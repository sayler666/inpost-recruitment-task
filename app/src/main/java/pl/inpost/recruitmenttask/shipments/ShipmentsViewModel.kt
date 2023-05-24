package pl.inpost.recruitmenttask.shipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.model.ShipmentDisplayableHeader
import pl.inpost.recruitmenttask.shipments.model.ShipmentDisplayableItem
import pl.inpost.recruitmenttask.shipments.model.ShipmentsUiState
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ShipmentsUiState())
    val state: StateFlow<ShipmentsUiState> = _state.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            delay(1000)
            _state.value = _state.value.copy(
                isLoading = false,
                shipments = mockedShipments()
            )
        }
    }

    // TODO: Remove this method and replace it with real data
    private fun mockedShipments() = listOf(
        ShipmentDisplayableHeader(R.string.section_ready_to_pickup),
        ShipmentDisplayableItem(
            number = "235678654323567889762231",
            icon = R.drawable.ic_courier,
            status = R.string.status_out_for_delivery,
            sender = "adresmailowy@mail.com",
            dateLabel = null,
            date = null,
        ),
        ShipmentDisplayableItem(
            number = "235678654323567889762231",
            icon = R.drawable.ic_locker,
            status = R.string.status_ready_to_pickup,
            sender = "adresmailowy@mail.com",
            dateLabel = R.string.pick_up_to,
            date = "pn. | 12.04.2021 | 12:00"
        ),
        ShipmentDisplayableHeader(R.string.section_other),
        ShipmentDisplayableItem(
            number = "235678654323567889762231",
            icon = R.drawable.ic_locker,
            dateLabel = R.string.picked_up,
            date = "pn. | 12.04.2021 | 11:00",
            status = R.string.status_delivered,
            sender = "Adam Bielak"
        )
    )
}
