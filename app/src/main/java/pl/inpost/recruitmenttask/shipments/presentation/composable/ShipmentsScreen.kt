package pl.inpost.recruitmenttask.shipments.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.collectLatest
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.ShipmentsViewModel
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentsUiState

@RootNavGraph(start = true)
@Destination
@Composable
fun ShipmentsScreen(
    shipmentsViewModel: ShipmentsViewModel = hiltViewModel()
) {
    val uiState: ShipmentsUiState by shipmentsViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { Title() },
        content = { padding ->
            ShipmentList(
                padding,
                uiState,
                onRefresh = {
                    shipmentsViewModel.refreshData()
                },
                onArchiveItem = {
                    shipmentsViewModel.archiveShipment(it)
                }
            )
        }
    )

    LaunchedEffect(Unit) {
        shipmentsViewModel.error.collectLatest {
            it?.let {
                snackbarHostState.showSnackbar(
                    message = context.getString(it),
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}

@Composable
private fun Title() = Text(
    modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .fillMaxWidth(1f)
        .padding(16.dp),
    text = stringResource(R.string.app_name),
    style = MaterialTheme.typography.headlineLarge
)
