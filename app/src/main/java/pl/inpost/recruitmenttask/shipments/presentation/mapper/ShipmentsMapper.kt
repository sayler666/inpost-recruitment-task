package pl.inpost.recruitmenttask.shipments.presentation.mapper

import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentStatus.DELIVERED
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentStatus.READY_TO_PICKUP
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentType.COURIER
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayable
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableHeader
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableItem
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ShipmentsMapper @Inject constructor() {

    companion object {
        private const val DATE_FORMAT = "EEE. | dd.MM.yyyy | HH:mm"
    }

    fun toDisplayable(cached: List<ShipmentWithEventLogsCached>): List<ShipmentDisplayable> {
        return with(mutableListOf<ShipmentDisplayable>()) {
            sort(cached).scan(false) { previousHighlighted, shipmentCached ->
                addGroupHeader(previousHighlighted, shipmentCached)
                addShipment(shipmentCached)

                shipmentCached.operations.highlight
            }
            this
        }
    }

    private fun ZonedDateTime.format(): String = format(DateTimeFormatter.ofPattern(DATE_FORMAT))

    private fun MutableList<ShipmentDisplayable>.addGroupHeader(
        previousHighlighted: Boolean,
        shipment: ShipmentCached
    ) {
        if (previousHighlighted != shipment.operations.highlight) {
            when (shipment.status) {
                READY_TO_PICKUP -> add(ShipmentDisplayableHeader(R.string.section_ready_to_pickup))
                else -> add(ShipmentDisplayableHeader(R.string.section_other))
            }
        }
    }

    private fun MutableList<ShipmentDisplayable>.addShipment(shipment: ShipmentCached) {
        val (dateLabel, date) = when (shipment.status) {
            READY_TO_PICKUP -> R.string.pick_up_to to shipment.expiryDate?.format()
            DELIVERED -> R.string.picked_up to shipment.pickUpDate?.format()
            else -> null to null
        }
        add(
            ShipmentDisplayableItem(
                number = shipment.number,
                icon = if (shipment.shipmentType == COURIER)
                    R.drawable.ic_courier else R.drawable.ic_locker,
                status = shipment.status.nameRes,
                sender = shipment.sender?.email ?: shipment.sender?.name
                ?: shipment.sender?.phoneNumber ?: "",
                date = date,
                dateLabel = dateLabel
            )
        )
    }

    private fun sort(cached: List<ShipmentWithEventLogsCached>) = cached
        .asSequence()
        .sortedBy { it.shipment.number }
        .sortedBy { it.shipment.storedDate }
        .sortedBy { it.shipment.expiryDate }
        .sortedBy { it.shipment.pickUpDate }
        .sortedBy { it.shipment.status.ordinal }
        .sortedByDescending { it.shipment.operations.highlight }
        .map { it.shipment }
        .toList()
}
