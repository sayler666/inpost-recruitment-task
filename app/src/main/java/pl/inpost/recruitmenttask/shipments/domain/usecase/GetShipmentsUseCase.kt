package pl.inpost.recruitmenttask.shipments.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached
import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository

fun interface GetShipmentsUseCase : () -> Flow<Result<List<ShipmentWithEventLogsCached>>>

fun getShipments(
    shipmentRepository: ShipmentRepository,
): Flow<Result<List<ShipmentWithEventLogsCached>>> {
    return shipmentRepository.getShipments().map {
        Result.success(it)
    }.catch { cause ->
        emit(Result.failure(cause))
    }
}

