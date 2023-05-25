package pl.inpost.recruitmenttask.shipments.data.remote.model

import java.time.ZonedDateTime

data class ShipmentNetwork(
    val number: String,
    val shipmentType: ShipmentType,
    val status: ShipmentStatus,
    val eventLog: List<EventLogNetwork>,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    val receiver: CustomerNetwork?,
    val sender: CustomerNetwork?,
    val operations: OperationsNetwork
)
