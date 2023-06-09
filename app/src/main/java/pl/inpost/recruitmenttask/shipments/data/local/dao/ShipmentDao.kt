package pl.inpost.recruitmenttask.shipments.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.shipments.data.local.model.Archived
import pl.inpost.recruitmenttask.shipments.data.local.model.EventLogCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached

@Dao
abstract class ShipmentDao {

    @Upsert
    abstract suspend fun saveShipments(shipments: List<ShipmentCached>)

    @Upsert
    abstract suspend fun saveEventLogs(eventLogs: List<EventLogCached>)

    @Transaction
    open suspend fun saveShipmentsWithEventLogs(shipments: List<ShipmentWithEventLogsCached>) {
        saveShipments(shipments.map { it.shipment })
        saveEventLogs(shipments.flatMap { it.eventLogs })
    }

    @Transaction
    @Query(
        "SELECT * FROM ShipmentCached LEFT JOIN Archived " +
                "ON ShipmentCached.number = Archived.shipmentNumber WHERE shipmentNumber is NULL"
    )
    abstract fun getShipments(): Flow<List<ShipmentWithEventLogsCached>>

    @Query("SELECT EXISTS(SELECT * FROM Archived)")
    abstract suspend fun hasArchivedShipments(): Boolean

    @Upsert
    abstract suspend fun archiveShipment(archived: Archived)

}
