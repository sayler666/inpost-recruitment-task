package pl.inpost.recruitmenttask.shipments.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.shipments.model.ShipmentDisplayableItem
import pl.inpost.recruitmenttask.ui.TextLabel


@Composable
fun ShipmentItem(shipment: ShipmentDisplayableItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 12.dp)
        ) {
            Row(Modifier.padding(horizontal = 16.dp)) {
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
                        .padding(horizontal = 16.dp)
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
                        .padding(horizontal = 16.dp)
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

            Row(Modifier.padding(start = 16.dp)) {
                Column {
                    TextLabel(
                        text = stringResource(id = R.string.sender)
                    )
                    Text(text = shipment.sender, style = MaterialTheme.typography.headlineLarge)
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { /* TODO show parcel details (out of scope) */ }) {
                    Row {
                        Text(stringResource(id = R.string.more))
                        Image(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
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
