package pl.inpost.recruitmenttask.shipments.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.shipments.presentation.model.ShipmentDisplayableHeader


@Composable
fun ShipmentSectionHeader(header: ShipmentDisplayableHeader) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp)
            .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            Modifier
                .weight(1f)
                .height(1.dp)
                .align(alignment = Alignment.CenterVertically)
                .background(color = MaterialTheme.colorScheme.outline)
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            maxLines = 1,
            text = stringResource(id = header.text),
            style = MaterialTheme.typography.labelLarge
                .copy(color = MaterialTheme.colorScheme.tertiary)
        )
        Box(
            Modifier
                .weight(1f)
                .height(1.dp)
                .align(alignment = Alignment.CenterVertically)
                .background(color = MaterialTheme.colorScheme.outline)
        )
    }
}
