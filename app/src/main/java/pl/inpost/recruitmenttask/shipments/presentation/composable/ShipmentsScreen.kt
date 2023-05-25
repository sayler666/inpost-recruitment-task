package pl.inpost.recruitmenttask.shipments.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.collectLatest
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.ShipmentsViewModel
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableHeader
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableItem
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentsUiState

@RootNavGraph(start = true)
@Destination
@Composable
fun ShipmentsScreen(
    shipmentsViewModel: ShipmentsViewModel = hiltViewModel()
) {
    val uiState: ShipmentsUiState by shipmentsViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { Title() },
        content = { padding ->
            ShipmentList(
                padding,
                uiState,
                onRefresh = {
                    shipmentsViewModel.refreshData()
                }
            )
        }
    )

    LaunchedEffect(Unit) {
        shipmentsViewModel.error.collectLatest {
            it?.let {
                snackbarHostState.showSnackbar(
                    message = context.getString(it),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
private fun Title() = Text(
    modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .fillMaxWidth(1f)
        .padding(16.dp),
    text = stringResource(R.string.app_name),
    style = MaterialTheme.typography.headlineLarge
)

@Composable
private fun ShipmentList(
    padding: PaddingValues,
    uiState: ShipmentsUiState,
    onRefresh: () -> Unit
) {
    val pullToRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { onRefresh() }
    )

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullToRefreshState)
    ) {
        PullRefreshIndicator(
            uiState.isLoading,
            pullToRefreshState,
            Modifier
                .align(Alignment.TopCenter)
                .zIndex(10f)
                .padding(top = padding.calculateTopPadding())
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp)
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
