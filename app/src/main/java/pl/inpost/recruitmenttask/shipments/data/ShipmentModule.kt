package pl.inpost.recruitmenttask.shipments.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.remote.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ShipmentModule {

//    @Provides
//    fun provideGetShipmentsUseCase(
//        shipmentRepository: ShipmentRepository,
//    ): GetShipmentsUseCase {
//        return GetShipmentsUseCase {
//            getShipments(shipmentRepository)
//        }
//    }
//
//    @Provides
//    fun provideRefreshShipmentsUseCase(
//        shipmentRepository: ShipmentRepository,
//    ): RefreshShipmentsUseCase {
//        return RefreshShipmentsUseCase {
//            refreshShipments(shipmentRepository)
//        }
//    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindShipmentRepository(impl: ShipmentRepositoryImpl): ShipmentRepository
    }
}
