package pl.inpost.recruitmenttask.shipments.domain.usecase

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.inpost.recruitmenttask.shipments.domain.repository.ShipmentRepository
import pl.inpost.recruitmenttask.shipments.generateShipmentsCachedData
import kotlin.test.assertEquals

/* Sample usecase unit tests */
class GetShipmentsUseCaseTest {

    @MockK
    private lateinit var repository: ShipmentRepository

    private lateinit var objectUnderTest: GetShipmentsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpGetShipmentsUseCase()
    }

    @Test
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        val testShipmentsFromDomain = generateShipmentsCachedData()
        coEvery { repository.getShipments() } returns flowOf(testShipmentsFromDomain)

        // When-Then
        objectUnderTest.invoke().test {
            val result = awaitItem()

            assertEquals(
                expected = Result.success(testShipmentsFromDomain),
                actual = result,
            )
            awaitComplete()
        }
    }

    @Test
    fun `should wrap result with failure if repository throws Exception`() = runTest {
        // Given
        val testException = Exception("Test message")
        coEvery { repository.getShipments() } throws testException

        // When-Then
        assertThrows<Exception> {
            objectUnderTest.invoke().test {
                val result = awaitItem()

                assertEquals(
                    expected = Result.failure(testException),
                    actual = result,
                )
            }
        }
    }

    private fun setUpGetShipmentsUseCase() {
        objectUnderTest = GetShipmentsUseCase {
            getShipments(shipmentRepository = repository)
        }
    }
}
