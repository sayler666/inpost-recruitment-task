package pl.inpost.recruitmenttask.shipments.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import pl.inpost.recruitmenttask.shipments.data.local.dao.ShipmentDao
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached
import pl.inpost.recruitmenttask.shipments.data.mapper.toCachedModel
import pl.inpost.recruitmenttask.shipments.data.remote.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository
import javax.inject.Inject

class ShipmentRepositoryImpl @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDao: ShipmentDao
) : ShipmentRepository {
    override fun getShipments(): Flow<List<ShipmentWithEventLogsCached>> = flow {
        emitAll(shipmentDao.getShipments())
    }

    override suspend fun refreshShipments() {
        shipmentApi.getShipments()
            .onSuccess { shipmentsNetwork ->
                val cachedShipments = shipmentsNetwork.map { shipment ->
                    shipment.toCachedModel()
                }
                shipmentDao.saveShipmentsWithEventLogs(cachedShipments)
            }
            .onFailure { error -> throw error }
    }
}
