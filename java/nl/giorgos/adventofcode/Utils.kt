package nl.giorgos.adventofcode

import java.io.BufferedReader
import java.io.InputStream

object Utils {
    fun convertFileToStringArray(inputStream: InputStream): List<String> {
        val inputAsList = mutableListOf<String>()
        val reader = BufferedReader(inputStream.reader())
        try {
            var line = reader.readLine()
            while (line != null) {
                inputAsList.add(line)
                line = reader.readLine()
            }
        } finally {
            reader.close()
        }

        return inputAsList
    }

    fun convertFileToString(inputStream: InputStream): String {
        val reader = BufferedReader(inputStream.reader())
        return reader.readText().also {
            reader.close()
        }
    }
}
