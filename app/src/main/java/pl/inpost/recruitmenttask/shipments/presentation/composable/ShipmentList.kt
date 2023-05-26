package pl.inpost.recruitmenttask.shipments.presentation.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DismissDirection.EndToStart
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableHeader
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableItem
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentsUiState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShipmentList(
    padding: PaddingValues,
    uiState: ShipmentsUiState,
    onRefresh: () -> Unit,
    onArchiveItem: (String) -> Unit
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

        if (uiState.shipments.isEmpty() && !uiState.isLoading) EmptyState()

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = padding.calculateTopPadding() + 8.dp)
        ) {

            items(items = uiState.shipments,
                key = {
                    when (it) {
                        is ShipmentDisplayableHeader -> it.text
                        is ShipmentDisplayableItem -> it.number
                    }
                },
                itemContent = { shipment ->
                    when (shipment) {
                        is ShipmentDisplayableHeader -> ShipmentSectionHeader(shipment)
                        is ShipmentDisplayableItem -> {
                            val item by rememberUpdatedState(newValue = shipment)
                            val dismissState = rememberDismissState(
                                confirmValueChange = {
                                    if (it == DismissValue.DismissedToStart) {
                                        onArchiveItem(item.number)
                                        true
                                    } else false
                                }
                            )

                            SwipeToDismiss(
                                modifier = Modifier.animateItemPlacement(),
                                state = dismissState,
                                directions = setOf(EndToStart),
                                background = {
                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .background(MaterialTheme.colorScheme.background),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            painter = rememberVectorPainter(image = Icons.Default.Delete),
                                            contentDescription = null,
                                            modifier = Modifier.padding(end = 45.dp)
                                        )
                                    }
                                },
                                dismissContent = { ShipmentItem(shipment) })

                        }
                    }
                })
        }
    }
}

@Composable
private fun BoxScope.EmptyState() {
    Text(
        modifier = Modifier.align(Alignment.Center),
        text = stringResource(R.string.no_shipments),
        style = MaterialTheme.typography.bodyMedium
    )
}
