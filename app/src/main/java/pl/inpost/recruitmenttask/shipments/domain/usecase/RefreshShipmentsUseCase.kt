package pl.inpost.recruitmenttask.shipments.domain.usecase

import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository

fun interface RefreshShipmentsUseCase : suspend () -> Unit

suspend fun refreshShipments(
    shipmentRepository: ShipmentRepository,
) {
    shipmentRepository.refreshShipments()
}
