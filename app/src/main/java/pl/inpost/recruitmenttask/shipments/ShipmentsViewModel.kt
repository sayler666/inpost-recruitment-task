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
import pl.inpost.recruitmenttask.shipments.domain.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.GetShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.RefreshShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.presentation.mapper.ShipmentsMapper
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentsUiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val getShipmentsUseCase: GetShipmentsUseCase,
    private val refreshShipmentsUseCase: RefreshShipmentsUseCase,
    private val archiveShipmentUseCase: ArchiveShipmentUseCase,
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
        viewModelScope.launch {
            getShipmentsUseCase()
                .collect {
                    it.onSuccess {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            shipments = mapper.toDisplayable(it)
                        )
                    }.onFailure {
                        _state.value = _state.value.copy(isLoading = false)
                        _error.emit(R.string.error)
                    }
                }
        }
    }

    fun refreshData() {
        viewModelScope.launch(SupervisorJob() + exceptionHandler) {
            _state.value = _state.value.copy(isLoading = true)
            refreshShipmentsUseCase()
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun archiveShipment(shipmentNumber: String) {
        viewModelScope.launch(SupervisorJob() + exceptionHandler) {
            archiveShipmentUseCase(shipmentNumber)
        }
    }
}
