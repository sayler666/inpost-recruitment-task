package pl.inpost.recruitmenttask.shipments

import app.cash.turbine.test
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.core.MainDispatcherExtension
import pl.inpost.recruitmenttask.shipments.data.local.model.ShipmentWithEventLogsCached
import pl.inpost.recruitmenttask.shipments.domain.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.GetShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.usecase.RefreshShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.presentation.mapper.ShipmentsMapper

/* Sample view model unit tests */
class ShipmentsViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @RelaxedMockK
    private lateinit var getShipmentsUseCase: GetShipmentsUseCase

    private val shipmentsMapper = ShipmentsMapper()

    @RelaxedMockK
    private lateinit var archiveShipmentUseCase: ArchiveShipmentUseCase

    @RelaxedMockK
    private lateinit var refreshShipmentsUseCase: RefreshShipmentsUseCase

    private lateinit var objectUnderTest: ShipmentsViewModel

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should show loading state first during init Shipments retrieval`() =
        runTest {
            // Given
            val fakeFlow = MutableStateFlow<Result<List<ShipmentWithEventLogsCached>>>(
                Result.success(emptyList())
            )
            every { getShipmentsUseCase() } returns fakeFlow
            setUpShipmentsViewModel()

            // When-Then
            objectUnderTest.state.test {
                val actualItem = awaitItem()
                assertTrue(actualItem.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should hide loading state after Shipments retrieval`() =
        runTest {
            // Given
            val fakeFlow = MutableStateFlow<Result<List<ShipmentWithEventLogsCached>>>(
                Result.success(emptyList())
            )
            every { getShipmentsUseCase() } returns fakeFlow
            setUpShipmentsViewModel()

            // When-Then
            objectUnderTest.state.test {
                assertTrue(awaitItem().isLoading)

                fakeFlow.emit(Result.success(generateShipmentsCachedData()))
                val item = awaitItem()
                assertTrue(item.isLoading.not())
                assertEquals(generateShipmentsDisplayable(), item.shipments)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should show error after failure retrieval`() =
        runTest {
            // Given
            val fakeFlow = MutableStateFlow<Result<List<ShipmentWithEventLogsCached>>>(
                Result.failure(Throwable("Error"))
            )
            every { getShipmentsUseCase() } returns fakeFlow
            setUpShipmentsViewModel()

            // When-Then
            objectUnderTest.error.test {
                val actualItem = awaitItem()
                assertEquals(R.string.error, actualItem)
            }
        }

    private fun setUpShipmentsViewModel() {
        objectUnderTest = ShipmentsViewModel(
            getShipmentsUseCase,
            refreshShipmentsUseCase,
            archiveShipmentUseCase,
            shipmentsMapper
        )
    }
}
