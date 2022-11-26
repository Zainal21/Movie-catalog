package com.muhamadzain.movie_catalog.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.TextView
import com.google.gson.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {
    fun convertDate(string: String?) : String? {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !string.isNullOrEmpty()) {
            LocalDate.parse(string, DateTimeFormatter.ISO_DATE)
        } else {
            return string
        }

        return "${date.dayOfWeek.name.toLowerCase(Locale.ROOT).capitalize(Locale.ROOT)}, ${date.dayOfMonth} ${
            date.month.name.toLowerCase(
                Locale.ROOT).capitalize(Locale.ROOT)
        } ${date.year}"
    }

    fun TextView.formatPrice(value: String) {
        this.text = indonesiaCurrency(java.lang.Double.parseDouble(value))
    }

    fun indonesiaCurrency(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "IDR " + format.format(price).replace(",".toRegex(), ".")
    }

    fun getDefaultGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                val formatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                TimeZone.getTimeZone("UTC").also { formatServer.timeZone = it }
                formatServer.parse(json.asString)
            })
            .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                format.timeZone = TimeZone.getTimeZone("UTC")
                if (src != null) {
                    JsonPrimitive(format.format(src))
                } else {
                    null
                }
            }).create()
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.convertLongToTime(dateFormmat: String): String {
        val date = Date(this)
        val format = SimpleDateFormat(dateFormmat)
        return format.format(date)
    }
}