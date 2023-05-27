package pl.inpost.recruitmenttask.shipments.data.remote.api

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.data.remote.api.adapter.ApiTypeAdapter
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentNetwork
import pl.inpost.recruitmenttask.shipments.data.remote.model.ShipmentsResponse

class MockShipmentApi(
    @ApplicationContext private val context: Context,
    apiTypeAdapter: ApiTypeAdapter
) : ShipmentApi {

    private val response by lazy {
        val json = context.resources.openRawResource(R.raw.mock_shipment_api_response)
            .bufferedReader()
            .use { it.readText() }

        val jsonAdapter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(apiTypeAdapter)
            .build()
            .adapter(ShipmentsResponse::class.java)

        jsonAdapter.fromJson(json) as ShipmentsResponse
    }

    override suspend fun getShipments(): List<ShipmentNetwork> {
        delay(1000)
        return response.shipments
    }
}
