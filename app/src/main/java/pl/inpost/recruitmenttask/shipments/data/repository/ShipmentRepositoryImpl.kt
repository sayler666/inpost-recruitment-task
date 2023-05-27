package pl.inpost.recruitmenttask.shipments.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.inpost.recruitmenttask.shipments.data.local.dao.ShipmentDao
import pl.inpost.recruitmenttask.shipments.data.local.model.Archived
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
        shipmentDao.getShipments().collect {
            if (it.isEmpty()) {
                // emit empty list only if there are archived shipments
                if (shipmentDao.hasArchivedShipments()) emit(it)
                refreshShipments()
            } else {
                emit(it)
            }
        }
    }

    override suspend fun refreshShipments() {
        shipmentApi.getShipments().map { shipment ->
            shipment.toCachedModel()
        }.also { cachedShipments ->
            shipmentDao.saveShipmentsWithEventLogs(cachedShipments)
        }
    }

    override suspend fun archiveShipment(shipmentNumber: String) {
        shipmentDao.archiveShipment(Archived(shipmentNumber))
    }
}
