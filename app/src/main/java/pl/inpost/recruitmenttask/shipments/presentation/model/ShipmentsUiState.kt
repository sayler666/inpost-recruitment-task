package pl.inpost.recruitmenttask.shipments.presentation.model

data class ShipmentsUiState(
    val isLoading: Boolean = true,
    val shipments: List<ShipmentDisplayable> = emptyList()
)
