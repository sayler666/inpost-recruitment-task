package pl.inpost.recruitmenttask.shipments.presentation.composable

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableItem
import pl.inpost.recruitmenttask.ui.TextLabel


@Composable
fun ShipmentItem(shipment: ShipmentDisplayableItem) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .clickable {
                    // TODO out of scope
                    Toast
                        .makeText(context, shipment.number, Toast.LENGTH_SHORT)
                        .show()
                }
                .padding(top = 16.dp)
                .padding(bottom = 28.dp)
                .padding(horizontal = 20.dp)

        ) {
            Row {
                Column {
                    TextLabel(text = stringResource(id = R.string.shipment_nr))
                    Text(shipment.number, style = MaterialTheme.typography.headlineMedium)
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    painter = painterResource(id = shipment.icon),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextLabel(
                        text = stringResource(id = R.string.status)
                    )
                    shipment.dateLabel?.let {
                        TextLabel(
                            textAlign = TextAlign.End,
                            text = stringResource(id = it)
                        )
                    }
                }
            }
            Column {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1f, fill = false),
                        text = stringResource(id = shipment.status),
                        maxLines = 3,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    shipment.date?.let {
                        Text(
                            text = it,
                            maxLines = 1,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Column {
                    TextLabel(
                        text = stringResource(id = R.string.sender)
                    )
                    Text(text = shipment.sender, style = MaterialTheme.typography.headlineLarge)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Text(
                        stringResource(id = R.string.more),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Image(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surfaceTint,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
    }

}
