package pl.inpost.recruitmenttask.shipments.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.ShipmentsViewModel
import pl.inpost.recruitmenttask.shipments.model.ShipmentDisplayableHeader
import pl.inpost.recruitmenttask.shipments.model.ShipmentDisplayableItem
import pl.inpost.recruitmenttask.shipments.model.ShipmentsUiState

@OptIn(ExperimentalMaterialApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun ShipmentsScreen(
    shipmentsViewModel: ShipmentsViewModel = hiltViewModel()
) {
    val uiState: ShipmentsUiState by shipmentsViewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { shipmentsViewModel.refreshData() }
    )
    Text(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth(1f)
            .padding(16.dp),
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.headlineLarge
    )
    Column(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullToRefreshState)
    ) {
        PullRefreshIndicator(
            uiState.isLoading,
            pullToRefreshState,
            Modifier
                .align(Alignment.CenterHorizontally)
                .zIndex(10f)
        )

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 20.dp)
        ) {
            items(uiState.shipments) { shipment ->
                when (shipment) {
                    is ShipmentDisplayableHeader -> ShipmentSectionHeader(shipment)
                    is ShipmentDisplayableItem -> ShipmentItem(shipment)
                }
            }
        }

    }
}
