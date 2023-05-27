package pl.inpost.recruitmenttask.shipments.data.remote.api

import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentNetwork

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetwork>
}
