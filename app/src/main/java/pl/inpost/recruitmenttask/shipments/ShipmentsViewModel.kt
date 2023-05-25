package pl.inpost.recruitmenttask.shipments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.shipments.presentation.mapper.ShipmentsMapper
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentsUiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val shipmentRepository: ShipmentRepository,
    private val mapper: ShipmentsMapper
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _state.value = _state.value.copy(isLoading = false)
        viewModelScope.launch {
            // TODO more sophisticated error message
            _error.emit(R.string.error)
        }
    }

    private val _error = MutableSharedFlow<Int?>()
    val error = _error.asSharedFlow()

    private val _state = MutableStateFlow(ShipmentsUiState())
    val state: StateFlow<ShipmentsUiState> = _state.asStateFlow()

    init {
        observeShipmentData()
        refreshData()
    }

    private fun observeShipmentData() {
        viewModelScope.launch(SupervisorJob() + exceptionHandler) {
            shipmentRepository.getShipments()
                .collect {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        shipments = mapper.toDisplayable(it)
                    )
                }
        }
    }

    fun refreshData() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch(SupervisorJob() + exceptionHandler) {
            shipmentRepository.refreshShipments()
        }
    }
}
