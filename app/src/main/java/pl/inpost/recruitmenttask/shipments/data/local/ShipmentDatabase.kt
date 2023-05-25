package pl.inpost.recruitmenttask.shipments.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.shipments.data.local.converter.ZonedDateTimeConverter
import pl.inpost.recruitmenttask.shipments.data.local.dao.ShipmentDao
import pl.inpost.recruitmenttask.shipments.data.local.model.EventLogCached
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentCached

private const val DATABASE_VERSION = 1

@Database(
    entities = [ShipmentCached::class, EventLogCached::class],
    version = DATABASE_VERSION,
)
@TypeConverters(ZonedDateTimeConverter::class)
abstract class ShipmentDatabase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentDao
}
