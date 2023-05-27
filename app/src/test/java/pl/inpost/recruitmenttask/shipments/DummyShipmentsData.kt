package pl.inpost.recruitmenttask.shipments

import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.data.local.model.CustomerCached
import pl.inpost.recruitmenttask.shipments.data.local.model.OperationsCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached
import pl.inpost.recruitmenttask.shipments.data.remote.model.CustomerNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.OperationsNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentStatus
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentType
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableItem
import java.time.ZoneId
import java.time.ZonedDateTime

fun generateShipmentsCachedData() = listOf(
    ShipmentWithEventLogsCached(
        ShipmentCached(
            number = "1234",
            shipmentType = ShipmentType.PARCEL_LOCKER,
            status = ShipmentStatus.CREATED,
            openCode = "1233",
            expiryDate = ZonedDateTime.of(2021, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault()),
            storedDate = null,
            pickUpDate = ZonedDateTime.of(2021, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault()),
            CustomerCached("email@mail.com", "555000555", "John"),
            CustomerCached("email@mail.com", "555000555", "John"),
            OperationsCached(
                manualArchive = false,
                delete = false,
                collect = false,
                highlight = false,
                expandAvizo = false,
                endOfWeekCollection = false
            )
        ), emptyList()
    )
)

fun generateShipmentsDisplayable() = listOf(
    ShipmentDisplayableItem(
        number = "1234",
        icon = R.drawable.ic_locker,
        status = ShipmentStatus.CREATED.nameRes,
        sender = "email@mail.com",
        date = null,
        dateLabel = null
    )
)

fun generateShipmentsApiData() = listOf(
    ShipmentNetwork(
        number = "1234",
        shipmentType = ShipmentType.PARCEL_LOCKER,
        status = ShipmentStatus.CREATED,
        openCode = "1233",
        expiryDate = ZonedDateTime.of(2021, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault()),
        storedDate = null,
        pickUpDate = ZonedDateTime.of(2021, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault()),
        eventLog = emptyList(),
        sender = CustomerNetwork("email@mail.com", "555000555", "John"),
        receiver = CustomerNetwork("email@mail.com", "555000555", "John"),
        operations = OperationsNetwork(
            manualArchive = false,
            delete = false,
            collect = false,
            highlight = false,
            expandAvizo = false,
            endOfWeekCollection = false
        )
    )
)
