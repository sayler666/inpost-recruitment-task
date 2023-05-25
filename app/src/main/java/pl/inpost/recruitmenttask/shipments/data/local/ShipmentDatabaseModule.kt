package pl.inpost.recruitmenttask.shipments.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.local.dao.ShipmentDao
import javax.inject.Singleton

private const val DATABASE_NAME = "shipment_database"

@Module
@InstallIn(SingletonComponent::class)
internal object ShipmentDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): ShipmentDatabase = Room.databaseBuilder(
        context,
        ShipmentDatabase::class.java,
        DATABASE_NAME,
    ).build()

    @Singleton
    @Provides
    fun provideShipmentDao(database: ShipmentDatabase): ShipmentDao {
        return database.shipmentDao()
    }
}
