package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.file.InvalidPathException
import kotlin.math.abs

class Day17 {
    private val fileName = "input17.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    private fun isActive(char: Char) = char == '#'

    private class Cube(val x: Int, val y: Int, val z: Int,  var active: Boolean) {

        fun setActive(activeNeighbourCubesCount: Int) {

//                        If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
            if (active && !IntRange(2, 3).contains(activeNeighbourCubesCount)) {
                active = false
//                        If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.
            } else if (!active && activeNeighbourCubesCount == 3) {
                active = true
            }
        }

        fun isNeighbour(anotherCube: Cube) = abs(x - anotherCube.x) + abs(y - anotherCube.y) + abs(z - anotherCube.z) == 1

        override fun toString() = "[$x, $y, $z]"

        fun copy() = Cube(x, y, z, active)
    }

    private fun putCubeSafely(map: MutableMap<Int, MutableMap<Int, MutableMap<Int, Cube>>>, cube: Cube, debug: Boolean = false): Cube {
        if (!map.containsKey(cube.x)) {
            if (debug) println("x plane was missing")
            map[cube.x] = mutableMapOf()
        }
        if (!map[cube.x]!!.containsKey(cube.y)) {
            if (debug) println("y plane was missing")
            map[cube.x]!![cube.y] = mutableMapOf()
        }
        if (!map[cube.x]!![cube.y]!!.containsKey(cube.z)) {
            if (debug) println("z plane was missing")
            map[cube.x]!![cube.y]!![cube.z] = cube
        }
        map[cube.x]!![cube.y]!![cube.z] = cube
        return cube
    }

    private fun getCurrentCubeOrPutInactiveCube(map: MutableMap<Int, MutableMap<Int, MutableMap<Int, Cube>>>, x: Int, y: Int, z: Int): Cube {
        map[x]?.get(y)?.get(z)?.let {
            return it
        }
        return putCubeSafely(map, Cube(x, y, z, false))
    }

    private fun getNeighbours(map: MutableMap<Int, MutableMap<Int, MutableMap<Int, Cube>>>, cube: Cube): List<Cube> {
        val list = mutableListOf<Cube>()
        for (x in cube.x - 1..cube.x + 1) {
            for (y in cube.y -1..cube.y + 1) {
                for (z in cube.z -1..cube.z + 1) {
                    if (!(x == cube.x && y == cube.y && z == cube.z)) {
                        val getAlreadySavedCubeOrAnInactiveOne = map[x]?.get(y)?.get(z)?: Cube(x, y, z, false)
                        list.add(getAlreadySavedCubeOrAnInactiveOne)
                    }
                }
            }
        }
        return list
    }

    private fun getCopyOfMap(map: MutableMap<Int, MutableMap<Int, MutableMap<Int, Cube>>>):  MutableMap<Int, MutableMap<Int, MutableMap<Int, Cube>>> {
        val copy: MutableMap<Int, MutableMap<Int, MutableMap<Int, Cube>>> = mutableMapOf()

        map.forEach { _, u ->
            u.forEach { _, uu ->
                uu.forEach { _, uuu ->
                    putCubeSafely(copy, Cube(uuu.x, uuu.y, uuu.z, uuu.active))
                }
            }
        }

        return copy
    }

    private fun setActive(cube: Cube, activeNeighbourCubesCount: Int) {

    }
    @Test
    fun ex1() {
        var referenceCubes = mutableMapOf<Int, MutableMap<Int, MutableMap<Int, Cube>>>()
        lines.forEachIndexed { yIndex, line ->
            line.forEachIndexed { xIndex, currentState ->
                putCubeSafely(referenceCubes, Cube(xIndex, yIndex, 0, isActive(currentState)))
            }
        }

        for (i in 1..6) {
            val tempReference = getCopyOfMap(referenceCubes)
            println(i)
//            println(referenceCubes)
            referenceCubes.forEach { t, xCubes ->
//                println(" $t")
                xCubes.forEach { tt, yCubes ->
//                    println("  $tt (${yCubes.values.size})")
                    yCubes.forEach { ttt, cube ->
                        val tempCube = cube.copy()
//                        if (i == 1 && t==0&&tt==0 && ttt == 0) println(cube)
//                        println("   $ttt (${yCubes.size}")
                        val neighbourCubes = getNeighbours(referenceCubes, tempCube)
                        val activeNeighbourCubesCount = neighbourCubes.filter { it.active }.size
                        tempCube.setActive(activeNeighbourCubesCount)
                        putCubeSafely(tempReference, Cube(tempCube.x, tempCube.y, tempCube.z, tempCube.active))
                        neighbourCubes.forEach { neighbourCube ->
                            val tempNeighbourCube = neighbourCube.copy()
                            val itneighbourCubes = getNeighbours(referenceCubes, tempNeighbourCube)
                            val itactiveNeighbourCubesCount = itneighbourCubes.filter { it.active }.size
                            tempNeighbourCube.setActive(itactiveNeighbourCubesCount)
                            putCubeSafely(tempReference, Cube(tempNeighbourCube.x, tempNeighbourCube.y, tempNeighbourCube.z, tempNeighbourCube.active))
                        }
//                        println("edw")
                    }
                }
            }
            referenceCubes = getCopyOfMap(tempReference)

            var cubes = 0
            var activeCubes = 0
            referenceCubes.forEach { t, u ->
                u.forEach { tt, uu ->
                    uu.forEach { ttt, cube ->
                        cubes += 1
                        if (cube.active) activeCubes += 1


                        if (i == 1 && cube.active) {
                            println("Cube active at $cube")
                        }
                    }
                }
            }

            println("I saved $cubes of which $activeCubes are active")
        }



    }

    @Test
    fun ex2() {
        val map = mutableMapOf(1 to 2)
        val map2 = map.toMutableMap()
        map[1] = 3
        println(map2)

        val ran = IntRange(2,3)
        assertTrue(!ran.contains(1))
        assertTrue(!ran.contains(4))
        assertFalse(!ran.contains(2))
        assertFalse(!ran.contains(3))
    }
}
