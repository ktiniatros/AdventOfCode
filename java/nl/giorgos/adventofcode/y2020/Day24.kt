package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day24 {
    private val fileName = "input24.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val tileInstructions = Utils.convertFileToString(input).split("\n").filterNot { it.isEmpty() }

    enum class Direction {
        W,E,SE,SW,NE,NW;

        companion object {
            fun fromString(s: String): Direction {
                return when (s) {
                    "w" -> W
                    "e" -> E
                    "se" -> SE
                    "sw" -> SW
                    "ne" -> NE
                    "nw" -> NW
                    else -> throw Exception("Invalid direction input: $s")
                }
            }
        }
    }

    class Tile(var x: Int, var y: Int, var z: Int, var isBlack: Boolean = false) {

        override fun toString() = "Tile $x $y $z"

        fun changeColor() {
            isBlack = !isBlack
        }

        fun copy(): Tile = Tile(this.x, this.y, this.z, this.isBlack)

        fun changeColorFromNeighbours(tilesMap: Map<Triple<Int, Int, Int>, Tile>) {
            val defaultWhiteTile = Tile(0, 0, 0)
            val w = tilesMap[Triple(x-1, y+1, z)] ?: defaultWhiteTile
            val e = tilesMap[Triple(x+1, y-1, z)] ?: defaultWhiteTile
            val se = tilesMap[Triple(x, y-1, z+1)] ?: defaultWhiteTile
            val sw = tilesMap[Triple(x-1, y, z+1)] ?: defaultWhiteTile
            val ne = tilesMap[Triple(x+1, y, z-1)] ?: defaultWhiteTile
            val nw = tilesMap[Triple(x, y+1, z-1)] ?: defaultWhiteTile

            val blackNeighbours = listOf(w, e, se, sw, ne, nw).filter { it.isBlack }.size

            if (isBlack) {
                if (blackNeighbours == 0 || blackNeighbours > 2) {
                    isBlack = false
                }
            } else {
                if (blackNeighbours == 2) {
                    isBlack = true
                }
            }
        }

        fun directTo(direction: Direction) {
            when (direction) {
                Direction.W -> {
                    x -= 1
                    y += 1
                }
                Direction.E -> {
                    x += 1
                    y -= 1
                }
                Direction.SE -> {
                    y -= 1
                    z += 1
                }
                Direction.SW -> {
                    x -= 1
                    z += 1
                }
                Direction.NE -> {
                    x += 1
                    z -= 1
                }
                Direction.NW -> {
                    y += 1
                    z -= 1
                }
            }
        }
    }

    var tilesMap = mutableMapOf<Triple<Int, Int, Int>, Tile>()

    private fun installTiles() {
        tileInstructions.forEach {
            var index = 0
            val tile = Tile(0, 0, 0)
            while (index < it.length) {
                val nextChar = it[index]
                val nextDirection = if (nextChar == 's' || nextChar == 'n') {
                    index += 1
                    it.substring(index - 1, index + 1)
                } else {
                    "$nextChar"
                }
                index += 1
                val direction = Direction.fromString(nextDirection)
                tile.directTo(direction)

            }

            if (tilesMap.containsKey(Triple(tile.x, tile.y, tile.z))) {
                tilesMap[Triple(tile.x, tile.y, tile.z)]!!.changeColor()
            } else {
                tilesMap[Triple(tile.x, tile.y, tile.z)] = Tile(tile.x, tile.y, tile.z).apply { isBlack = true }
            }

        }
    }

    @Test
    fun ex1() {
        installTiles()
        println("There are ${tilesMap.filter { it.value.isBlack }.size} black tiles")
    }

    @Test
    fun ex2() {
        installTiles()

        val tempMap = tilesMap.filter { it.value.isBlack }.toMutableMap()
        tilesMap = tempMap.toMutableMap()
        tempMap.clear()

        for (i in 1..100) {
            tilesMap.forEach { entry ->
                val oldTile = entry.value

                val listOfNewTiles = mutableListOf<Tile>()
                val neighbourDistance = 1
                for (xx in -neighbourDistance..neighbourDistance) {
                    for (yy in -neighbourDistance..neighbourDistance) {
                        for (zz in -neighbourDistance..neighbourDistance) {
                            val tile = tilesMap[Triple(oldTile.x+xx, oldTile.y+yy, oldTile.z+zz)]?.copy() ?: Tile(oldTile.x+xx, oldTile.y+yy, oldTile.z+zz)
                            tile.changeColorFromNeighbours(tilesMap)
                            listOfNewTiles.add(tile)

                        }
                    }
                }
                listOfNewTiles.filter { it.isBlack }.forEach {
                    tempMap[Triple(it.x, it.y, it.z)] = it.copy()
                }
            }

            tilesMap = tempMap.toMutableMap()
            tempMap.clear()
        }
        println("Result: ${tilesMap.filter { it.value.isBlack }.size}")
    }
}
