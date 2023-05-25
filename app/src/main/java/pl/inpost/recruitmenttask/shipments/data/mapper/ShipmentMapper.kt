package pl.inpost.recruitmenttask.shipments.data.mapper

import pl.inpost.recruitmenttask.shipments.data.local.model.CustomerCached
import pl.inpost.recruitmenttask.shipments.data.local.model.EventLogCached
import pl.inpost.recruitmenttask.shipments.data.local.model.OperationsCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached
import pl.inpost.recruitmenttask.shipments.data.remote.model.CustomerNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.EventLogNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.OperationsNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentNetwork

fun ShipmentNetwork.toCachedModel() = ShipmentWithEventLogsCached(
    ShipmentCached(
        number = number,
        status = status,
        shipmentType = shipmentType,
        openCode = openCode,
        expiryDate = expiryDate,
        storedDate = storedDate,
        pickUpDate = pickUpDate,
        receiver = receiver?.toCachedModel(),
        sender = sender?.toCachedModel(),
        operations = operations.toCachedModel()
    ),
    eventLog.map { eventLog -> eventLog.toCachedModel(number) }
)

fun EventLogNetwork.toCachedModel(shipmentNumber: String): EventLogCached = EventLogCached(
    shipmentNumber = shipmentNumber,
    name = name,
    date = date
)

fun CustomerNetwork.toCachedModel(): CustomerCached = CustomerCached(
    email = email,
    phoneNumber = phoneNumber,
    name = name
)

fun OperationsNetwork.toCachedModel(): OperationsCached = OperationsCached(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)
