package com.example.londonweaher.utils


class TempFormat {
    companion object {
        fun formKelvinToCelsius(kelvinTemp: String): String {
            return (kelvinTemp.toFloat() - 273.15).toInt().toString()
        }
    }
}