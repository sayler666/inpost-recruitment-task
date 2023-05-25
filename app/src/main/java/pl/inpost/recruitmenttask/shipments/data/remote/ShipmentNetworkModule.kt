package pl.inpost.recruitmenttask.shipments.data.remote

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.shipments.data.remote.api.adapter.ApiTypeAdapter
import pl.inpost.recruitmenttask.shipments.data.remote.api.MockShipmentApi
import pl.inpost.recruitmenttask.shipments.data.remote.api.ShipmentApi

@InstallIn(SingletonComponent::class)
@Module
class ShipmentNetworkModule {

    @Provides
    fun shipmentApi(@ApplicationContext context: Context, apiTypeAdapter: ApiTypeAdapter): ShipmentApi = MockShipmentApi(context, apiTypeAdapter)
}
