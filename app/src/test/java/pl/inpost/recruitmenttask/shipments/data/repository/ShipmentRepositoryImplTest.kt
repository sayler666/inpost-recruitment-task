package pl.inpost.recruitmenttask.shipments.data.repository

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import pl.inpost.recruitmenttask.MainDispatcherExtension
import pl.inpost.recruitmenttask.shipments.data.local.dao.ShipmentDao
import pl.inpost.recruitmenttask.shipments.data.remote.api.ShipmentApi
import pl.inpost.recruitmenttask.shipments.generateShipmentsApiData
import pl.inpost.recruitmenttask.shipments.generateShipmentsCachedData
import kotlin.test.assertEquals

/* Sample repository unit tests */
class ShipmentRepositoryImplTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @RelaxedMockK
    private lateinit var shipmentApi: ShipmentApi

    @RelaxedMockK
    private lateinit var shipmentDao: ShipmentDao

    private lateinit var objectUnderTest: ShipmentRepositoryImpl

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should emit list of cached items`() = runTest {
        // Given
        val fakeShipments = generateShipmentsCachedData()
        every { shipmentDao.getShipments() } returns flowOf(fakeShipments)
        setUpShipmentsRepository()

        // When-Then
        objectUnderTest.getShipments().test {
            val actualItem = awaitItem()
            assertEquals(fakeShipments, actualItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit empty list and fetch data from api`() = runTest {
        // Given
        every { shipmentDao.getShipments() } returns flowOf(emptyList())
        coEvery { shipmentDao.hasArchivedShipments() } returns true
        coEvery { shipmentApi.getShipments() } returns emptyList()
        setUpShipmentsRepository()

        // When-Then
        objectUnderTest.getShipments().test {
            val actualItem = awaitItem()
            assertEquals(emptyList(), actualItem)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { shipmentApi.getShipments() }
    }

    @Test
    fun `should store data from api in db`() = runTest {
        // Given
        every { shipmentDao.getShipments() } returns flowOf(emptyList())
        coEvery { shipmentDao.hasArchivedShipments() } returns true
        coEvery { shipmentApi.getShipments() } returns generateShipmentsApiData()
        setUpShipmentsRepository()

        // When-Then
        objectUnderTest.getShipments().test {
            val actualItem = awaitItem()
            assertEquals(emptyList(), actualItem)
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { shipmentApi.getShipments() }
        coVerify { shipmentDao.saveShipmentsWithEventLogs(any()) }
    }

    private fun setUpShipmentsRepository() {
        objectUnderTest = ShipmentRepositoryImpl(
            shipmentApi,
            shipmentDao,
        )
    }
}
