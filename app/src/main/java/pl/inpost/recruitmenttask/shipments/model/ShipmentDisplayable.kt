package pl.inpost.recruitmenttask.shipments.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class ShipmentDisplayable

data class ShipmentDisplayableItem(
    val number: String,
    @DrawableRes val icon: Int,
    @StringRes val status: Int,
    val sender: String,
    val date: String?,
    @StringRes val dateLabel: Int?
) : ShipmentDisplayable()

data class ShipmentDisplayableHeader(
    @StringRes val text: Int
) : ShipmentDisplayable()
