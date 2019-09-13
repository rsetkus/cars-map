package lt.setkus.cars.ext

import android.content.Context

fun String.getJson(context: Context): String {
    val inputStream = context.resources.assets.open(this)
    return inputStream.bufferedReader().use { it.readText() }
}