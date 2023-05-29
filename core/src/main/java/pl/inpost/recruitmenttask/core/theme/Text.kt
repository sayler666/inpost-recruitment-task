package pl.inpost.recruitmenttask.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextLabel(
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    text: String
) {
    Text(
        modifier = modifier,
        text = text.uppercase(),
        textAlign = textAlign,
        style = MaterialTheme.typography.labelSmall
            .copy(color = MaterialTheme.colorScheme.tertiary)
    )
}
