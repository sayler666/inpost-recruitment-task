package pl.inpost.recruitmenttask.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pl.inpost.recruitmenttask.core.R

val MontserratMedium = FontFamily(Font(R.font.montserrat_medium))
val MontserratSemiBold = FontFamily(Font(R.font.montserrat_semibold))
val MontserratBold = FontFamily(Font(R.font.montserrat_bold))

val Typography = Typography(
    labelSmall = TextStyle(
        fontFamily = MontserratSemiBold,
        fontSize = 11.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MontserratBold,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratSemiBold,
        fontSize = 13.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 15.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = MontserratBold,
        fontSize = 15.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = MontserratSemiBold,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    )
)
