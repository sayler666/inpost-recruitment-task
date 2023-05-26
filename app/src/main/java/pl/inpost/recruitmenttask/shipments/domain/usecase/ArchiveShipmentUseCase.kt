package pl.inpost.recruitmenttask.shipments.domain.usecase

import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository

fun interface ArchiveShipmentUseCase : suspend (String) -> Unit

suspend fun archiveShipment(
    shipmentRepository: ShipmentRepository,
    shipmentNumber: String,
) {
    shipmentRepository.archiveShipment(shipmentNumber)
}
