package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day21 {
    private val fileName = "input21.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val inputAsList = Utils.convertFileToString(input).split("\n")

    private fun parseFoodLine(input: String): Pair<List<String>, List<String>> {
        val parts = input.split(" (contains ")
        val ingredients = parts[0].split(" ")
        val allergens = parts[1].split(", ").map { it.replace(")", "") }
        return Pair(ingredients, allergens)
    }

    data class Food(val ingredients: MutableList<String>, val allergens: List<String>)

    private fun intersect(list: List<List<String>>) = list.reduceRight { list1, list2 ->
        return@reduceRight list1.intersect(list2).toList()
    }

    private fun getExerciseResults(): Pair<Int, String> {
        val listOfIngredients = mutableListOf<String>()
        val allergensMap = mutableMapOf<String, MutableList<Food>>()
        inputAsList.forEach {
            if (it.isNotBlank()) {
                val parsedFoodLine = parseFoodLine(it)
                val food = Food(parsedFoodLine.first.toMutableList(), parsedFoodLine.second)
                listOfIngredients.addAll(parsedFoodLine.first)
                food.allergens.forEach { allergen ->
                    allergensMap.getOrPut(allergen) {
                        return@getOrPut mutableListOf<Food>()
                    }.add(food)
                }

            }
        }

        val dangerousIngredients = mutableMapOf<String, String>()
        while (allergensMap.isNotEmpty()) {
            val allergensSoFar = allergensMap.keys.toSet()
            allergensSoFar.forEach { allergen ->
                allergensMap[allergen]?.let { allergenFoods ->
                    val ingredientsFound =  intersect(allergenFoods.map { it.ingredients })
                    if (ingredientsFound.size == 1) {
                        val ingredientFound = ingredientsFound.first()
                        dangerousIngredients[allergen] = ingredientFound
                        allergensMap.forEach { (t, u) ->
                            u.forEach {
                                it.ingredients.removeIf { it == ingredientFound }
                            }
                        }
                        listOfIngredients.removeIf { it == ingredientFound }
                        allergensMap.remove(allergen)
                    }
                }
            }
        }

        return Pair(listOfIngredients.size, dangerousIngredients.toSortedMap().map { it.value }.joinToString(","))
    }

    @Test
    fun ex1() {
        //2300 too high
        println("Result: ${getExerciseResults().first}")
    }

    @Test
    fun ex2() {
        println(getExerciseResults().second)
    }
}
