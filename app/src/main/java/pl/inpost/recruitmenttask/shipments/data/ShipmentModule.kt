package pl.inpost.recruitmenttask.shipments.data

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.repository.ShipmentRepositoryImpl
import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.shipments.domain.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.GetShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.RefreshShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.archiveShipment
import pl.inpost.recruitmenttask.shipments.domain.usecase.getShipments
import pl.inpost.recruitmenttask.shipments.domain.usecase.refreshShipments
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ShipmentModule {

    @Provides
    fun provideGetShipmentsUseCase(
        shipmentRepository: ShipmentRepository,
    ): GetShipmentsUseCase = GetShipmentsUseCase {
        getShipments(shipmentRepository)
    }

    @Provides
    fun provideRefreshShipmentsUseCase(
        shipmentRepository: ShipmentRepository,
    ): RefreshShipmentsUseCase = RefreshShipmentsUseCase {
        refreshShipments(shipmentRepository)
    }

    @Provides
    fun provideArchiveShipmentUseCase(
        shipmentRepository: ShipmentRepository,
    ): ArchiveShipmentUseCase = ArchiveShipmentUseCase {
        archiveShipment(shipmentRepository, it)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindShipmentRepository(impl: ShipmentRepositoryImpl): ShipmentRepository
    }
}
