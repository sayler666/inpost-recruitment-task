package pl.inpost.recruitmenttask.shipments.data.local.converter

import androidx.room.TypeConverter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun toZonedDateTime(value: String): ZonedDateTime = formatter.parse(value, ZonedDateTime::from)

    @TypeConverter
    fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)
}
