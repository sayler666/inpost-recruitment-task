package pl.inpost.recruitmenttask.shipments.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentStatus
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentType
import java.time.ZonedDateTime

@Entity
class ShipmentCached(
    @PrimaryKey
    val number: String,
    val shipmentType: ShipmentType,
    val status: ShipmentStatus,
    val openCode: String?,
    val expiryDate: ZonedDateTime?,
    val storedDate: ZonedDateTime?,
    val pickUpDate: ZonedDateTime?,
    @Embedded(prefix = "receiver_") val receiver: CustomerCached?,
    @Embedded(prefix = "sender_") val sender: CustomerCached?,
    @Embedded val operations: OperationsCached
)

data class CustomerCached(
    val email: String?,
    val phoneNumber: String?,
    val name: String?
)

data class OperationsCached(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
)

@Entity(primaryKeys = ["shipmentNumber", "name", "date"])
data class EventLogCached(
    val shipmentNumber: String,
    val name: String,
    val date: ZonedDateTime
)

data class ShipmentWithEventLogsCached(
    @Embedded val shipment: ShipmentCached,
    @Relation(
        parentColumn = "number",
        entityColumn = "shipmentNumber"
    )
    val eventLogs: List<EventLogCached>
)
