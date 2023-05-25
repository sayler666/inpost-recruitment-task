package pl.inpost.recruitmenttask.shipments.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached

interface ShipmentRepository {
    fun getShipments(): Flow<List<ShipmentWithEventLogsCached>>
    suspend fun refreshShipments()
}
