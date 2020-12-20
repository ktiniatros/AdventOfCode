package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException
import kotlin.math.sqrt

class Day20 {
    private val fileName = "input20.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val tilesText = Utils.convertFileToString(input).split("\n\n")

    class Tile(val input: String) {
        val title: String
        val id: Long
        val content: MutableList<MutableList<Char>>
        val size: Int

        val firstLine: String

        val lastLine: String

        val firstColumn: String

        val lastColumn: String

        val firstLineReversed: String

        val lastLineReversed: String

        val firstColumnReversed: String

        val lastColumnReversed: String

        init {
            val contentParts = input.split("\n")
            title = contentParts.first()
            id = title.split(" ").get(1).substring(0..3).toLong()
            size = contentParts.first().length
            content = MutableList(size) {
                MutableList(size) {
                    '0'
                }
            }
            for (i in 1 until contentParts.size) {
                val current = contentParts[i]
                current.forEachIndexed { index, char ->
                    content[i-1][index] = char
                }
            }

            firstLine = content[0].joinToString("")

            lastLine =  content[size - 1].joinToString("")

            firstColumn = content.map { it.first() }.joinToString("")

            lastColumn = content.map {it.last()}.joinToString("")

            firstLineReversed = firstLine.reversed()
            lastLineReversed = lastLine.reversed()
            firstColumnReversed = firstColumn.reversed()
            lastColumnReversed = lastColumn.reversed()
        }

        override fun toString() = "$title ($id)\n" + content.joinToString("\n")

        fun flipVertically() {
            for (x in 0 until size) {
                for (y in 0 until size/2) {
                    val c1 = content[x][y]
                    val c2 = content[x][size - y - 1]

                    content[x][y] = c2
                    content[x][size - y - 1] = c1
                }
            }
        }

        fun flipHorizontally() {
            for (x in 0 until size / 2) {
                for (y in 0 until size) {
                    val c1 = content[x][y]
                    val c2 = content[size - x - 1][y]

                    content[x][y] = c2
                    content[size - x - 1][y] = c1
                }
            }
        }

        fun rotateLeftTwoTimes() {
            val temp = MutableList(size) {
                MutableList(size) {
                    '0'
                }
            }
            for (y in content.indices) {
                val row = content[y]
                for (x in row.indices) {
                    val newX = size - x - 1
                    val newY = size - y - 1
                    temp[newX][newY] = content[x][y]
                }
            }
            for (y in content.indices) {
                val row = content[y]
                for (x in row.indices) {
                    content[x][y] = temp[x][y]
                }
            }
        }

        fun rotateLeft() {
            val temp = MutableList(size) {
                MutableList(size) {
                    '0'
                }
            }
            for (y in content.indices) {
                val row = content[y]
                for (x in row.indices) {
                    var newX = 0
                    var newY = 0
                    if (x > 0 && y > 0) {
                        newX = y
                        newY = x
                    }
                    temp[newX][newY] = content[x][y]
                }
            }
            for (y in content.indices) {
                val row = content[y]
                for (x in row.indices) {
                    content[x][y] = temp[x][y]
                }
            }
        }

        fun getBorders() = listOf(firstLine, firstLineReversed, lastLine, lastLineReversed, firstColumn, firstColumnReversed, lastColumn, lastColumnReversed)

        fun matchOneDirection(tile: Tile): Boolean =
                getBorders().intersect(tile.getBorders()).isNotEmpty()

        fun contentWithoutBorders(): List<MutableList<Char>> {
            return content.filterIndexed { index, _ -> index !=0 && index != size - 1 }.map { it.subList(1, size - 1) }
        }
    }

    private val tiles = tilesText.map { Tile(it) }

    private fun defaultValueForNeighboursMap() = mutableListOf<Long>()

    private fun multiply(l1: Long, l2: Long) = l1 * l2

    private fun findNeighbourTiles(): MutableMap<Long, MutableList<Long>> {
        val neighbours = mutableMapOf<Long, MutableList<Long>>()
        for (i in tiles.indices) {
            for (j in i+1 until tiles.size) {
                if (tiles[i].matchOneDirection(tiles[j])) {
                    val n1 = tiles[i]
                    val n2 = tiles[j]
                    neighbours.getOrPut(n1.id, ::defaultValueForNeighboursMap).add(n2.id)
                    neighbours.getOrPut(n2.id, ::defaultValueForNeighboursMap).add(n1.id)
                }
            }
        }
        return neighbours
    }

    @Test
    fun ex1() {
        val neighbours = findNeighbourTiles()
        println(neighbours.filter { it.value.size == 2 }.map { it.key }.reduceRight(::multiply))
    }

    @Test
    fun ex2() {
        val imageSize = sqrt(tiles.size.toDouble()).toInt()
        println(imageSize)
//        tiles.last().content.forEach(::println)
//        println("----")
//        tiles.last().contentWithoutBorders().forEach(::println)

        val neighbours = findNeighbourTiles()

        val cornerNeighbours = neighbours.filter { it.value.size == 2 }
        val cornerIds = cornerNeighbours.map { it.key }

        val cornerTiles = tiles.filter { cornerIds.contains(it.id) }

        val finalImage = MutableList(imageSize) {
            MutableList(imageSize) {
                cornerTiles.first()
            }
        }

        finalImage[imageSize - 1][0] = cornerTiles[1]
        finalImage[imageSize - 1][imageSize - 1] = cornerTiles[2]
        finalImage[0][imageSize - 1] = cornerTiles[3]

        val tilesIdsAdded = (mutableListOf<Long>() + cornerIds).toMutableList()
        cornerTiles.forEachIndexed { index, tile ->
            val nextNeighbourIdsToAdd = neighbours[tile.id] ?: throw Exception("There are.")
            val neighbourTiles = tiles.filter { nextNeighbourIdsToAdd.contains(it.id) }
            when (index) {
                0 -> {
                    finalImage[0][1] = neighbourTiles.first()
                    finalImage[1][0] = neighbourTiles.last()
                }
                1 -> {
                    finalImage[imageSize - 2][0] = neighbourTiles.first()
                    finalImage[imageSize - 1][1] = neighbourTiles.last()
                }
                2 -> {
                    finalImage[imageSize - 1][imageSize - 2] = neighbourTiles.first()
                    finalImage[imageSize - 2][imageSize - 1] = neighbourTiles.last()
                }
                3 -> {
                    finalImage[0][imageSize - 2] = neighbourTiles.first()
                    finalImage[1][imageSize - 1] = neighbourTiles.last()
                }
            }
            tilesIdsAdded.addAll(neighbourTiles.map { it.id })
        }







        var hashCount = 0
        val monsterLines = mutableListOf(Triple("", "", ""))
        tilesText.filterNot { it.isEmpty() }.mapIndexed { rowIndex, it ->
            if (it.contains('O')) {
                val lastTriple = monsterLines.last()
                when {
                    lastTriple.third.isEmpty() -> monsterLines[monsterLines.size - 1] = Triple(lastTriple.first, lastTriple.second, it)
                    lastTriple.second.isEmpty() -> monsterLines[monsterLines.size - 1] = Triple(lastTriple.first, it, lastTriple.third)
                    lastTriple.first.isEmpty() -> monsterLines[monsterLines.size - 1] = Triple(it, lastTriple.second, lastTriple.third)
                    else -> monsterLines.add(Triple(it, "", ""))
                }
            } else {
                hashCount += it.length
            }
        }
    }
}
