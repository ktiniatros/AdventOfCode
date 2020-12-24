package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day22 {
    private val fileName = "input22.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val players = Utils.convertFileToString(input).split("\n\n")

    class Player(input: String) {
        val name: String
        val cards = mutableListOf<Long>()

        init {
            val inputParts = input.split("\n")
            name = inputParts.first()
            cards.addAll(inputParts.subList(1, inputParts.size).filterNot { it == "" }.map { it.toLong() })
        }

        fun lost() = cards.isEmpty()

        override fun toString() = "$name has: $cards"
    }

    private val p1 = Player(players[0])
    private val p2 = Player(players[1])

    @Test
    fun ex1() {
        var round = 1
        while (!p1.lost() && !p2.lost()) {
            val p1CardPlayed = p1.cards.removeAt(0)
            val p2CardPlayed = p2.cards.removeAt(0)
            val p1Won = p1CardPlayed > p2CardPlayed
            if (p1Won) {
                p1.cards.addAll(listOf(p1CardPlayed, p2CardPlayed))
            } else {
                p2.cards.addAll(listOf(p2CardPlayed, p1CardPlayed))
            }

            round += 1
        }

        println("Result: ${listOf(p1.cards, p2.cards).flatten().reversed().mapIndexed { index, l -> 
            (index + 1) * l
        }.sum()}")
    }


    @Test
    fun ex2() {

        val p1PreviousRounds = mutableMapOf<Int, MutableList<List<Long>>>()
        val p2PreviousRounds = mutableMapOf<Int, MutableList<List<Long>>>()

        fun addedToPreviousRounds(cards: List<Long>, previousRounds: MutableList<List<Long>>): Boolean {
            return if (previousRounds.contains(cards)) {
                //println("DEADEND $cards")
                false
            } else {
                //println("adding: $cards")
                previousRounds.add(cards.toList())
                true
            }
        }

        var game = 0
        fun recursiveCombat(p1: Player, p2: Player): Boolean {
            game += 1
            val thisGame = game
            //println("\n=== Game $thisGame ===\n")
            val player1Cards = p1.cards
            val player2Cards = p2.cards

            var round = 0
            while (player1Cards.isNotEmpty() && player2Cards.isNotEmpty()) {
                round += 1

                if (!addedToPreviousRounds(player1Cards, p1PreviousRounds.getOrPut(thisGame) {
                            mutableListOf()
                        })) return true
                if (!addedToPreviousRounds(player2Cards, p2PreviousRounds.getOrPut(thisGame) {
                            mutableListOf()
                        })) return true

                //println("-- Round $round (Game $thisGame) --")
                //println("Player 1's deck: $player1Cards")
                //println("Player 2's deck: $player2Cards")

                val p1CardPlayed = player1Cards.removeAt(0)
                val p2CardPlayed = player2Cards.removeAt(0)

                //println("Player 1 plays: $p1CardPlayed")
                //println("Player 2 plays: $p2CardPlayed")

                val p1Won = if (p1CardPlayed <= player1Cards.size && p2CardPlayed <= player2Cards.size) {
                    //println("Playing a sub-game to determine the winner...")
                    val newP1 = Player("Player 1 for round$thisGame:\n" + player1Cards.take(p1CardPlayed.toInt()).map { it.toString() }.joinToString("\n"))
                    val newP2 = Player("Player 2 for round$thisGame:\n" + player2Cards.take(p2CardPlayed.toInt()).map { it.toString() }.joinToString("\n"))
                    recursiveCombat(newP1, newP2)
                } else {
                    p1CardPlayed > p2CardPlayed
                }
                //println("Player ${if (p1Won) "1" else "2"} wins round $round of game $thisGame")

                if (p1Won) {
                    player1Cards.addAll(listOf(p1CardPlayed, p2CardPlayed))
                } else {
                    player2Cards.addAll(listOf(p2CardPlayed, p1CardPlayed))
                }
            }

            //println("The winner of game $thisGame, winner is player ${if (player1Cards.isNotEmpty()) "1" else "2"}!")
            return player1Cards.isNotEmpty()
        }


        val p1Won = recursiveCombat(p1, p2)

        //34285 too low
        //36577 too high
        //println(p1Won)
        //println(p1.cards)
        //println(p2.cards)
        print("\nPlayer ")
        val winningCards = if (p1Won) {
            print("1")
            p1.cards
        } else {
            print("2")
            p2.cards
        }
        print(" wins on recursive combat.\n")
        println("Result: ${winningCards.reversed().mapIndexed { index, l ->
            (index + 1) * l
        }.sum()}")

    }
}
