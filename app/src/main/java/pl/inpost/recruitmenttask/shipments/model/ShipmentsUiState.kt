package pl.inpost.recruitmenttask.shipments.model

data class ShipmentsUiState(
    val isLoading: Boolean = true,
    val shipments: List<ShipmentDisplayable> = emptyList(),
    val error: String? = null
)
