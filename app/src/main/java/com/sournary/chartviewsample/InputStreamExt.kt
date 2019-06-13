package com.sournary.chartviewsample

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created in 6/7/19 by Sang
 * Description:
 */
fun InputStream.readStockData(): MutableList<StockData> {
    val reader = BufferedReader(InputStreamReader(this))
    val result = mutableListOf<StockData>()
    try {
        var line = reader.readLine()
        while (line != null) {
            val data = line.split(",")
            val stockData = StockData(
                data[0], // Date
                data[1].toFloat(), // open
                data[2].toFloat(), // high
                data[3].toFloat(), // low
                data[4].toFloat(), // close
                data[5].toFloat() // volume
            )
            result.add(stockData)
            line = reader.readLine()
        }
    } catch (e: Exception) {
        Log.d("App_Tag", "Error while read data: ${e.message}")
    }
    return result
}