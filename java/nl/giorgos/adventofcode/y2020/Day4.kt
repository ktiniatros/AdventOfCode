package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day4 {

//    byr (Birth Year)
//    iyr (Issue Year)
//    eyr (Expiration Year)
//    hgt (Height)
//    hcl (Hair Color)
//    ecl (Eye Color)
//    pid (Passport ID)
//    cid (Country ID)

    class Document(input: String) {
        val byr: String?
        val iyr: String?
        val eyr: String?
        val hgt: String?
        val hcl: String?
        val ecl: String?
        val pid: String?
        val cid: String?

        init {
            val fields = input.split(" ")
            val fieldsMap = mutableMapOf<String, String>()
            fields.forEach { 
                val field = it.split(":")
                fieldsMap[field.first()] = field.last()
            }
            
            byr = fieldsMap["byr"]
            iyr = fieldsMap["iyr"]
            eyr = fieldsMap["eyr"]
            hgt = fieldsMap["hgt"]
            hcl = fieldsMap["hcl"]
            ecl = fieldsMap["ecl"]
            pid = fieldsMap["pid"]
            cid = fieldsMap["cid"]
        }

        override fun toString() = "byr=$byr iyr=$iyr eyr=$eyr hgt=$hgt hcl=$hcl ecl=$ecl pid=$pid".replace("in", ".in")

        companion object {
            fun isValid(document: Document) = document.byr != null && document.iyr != null && document.eyr != null && document.hgt != null && document.hcl != null && document.ecl != null && document.pid != null

            // byr (Birth Year) - four digits; at least 1920 and at most 2002.
            // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
            // hgt (Height) - a number followed by either cm or in:
            // If cm, the number must be at least 150 and at most 193.
            // If in, the number must be at least 59 and at most 76.
            // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            // pid (Passport ID) - a nine-digit number, including leading zeroes.
            // cid (Country ID) - ignored, missing or not.
            fun isValid2(document: Document): Boolean {
                val byr = document.byr?.toInt() ?: 0
                val iyr = document.iyr?.toInt() ?: 0
                val eyr = document.eyr?.toInt() ?: 0
                var heightIsValid = false
                document.hgt?.let {
                    if (it.contains("cm")) {
                        val parts = it.split("cm")
                        if (parts.size == 2 && parts.first().toInt() in 150..193) {
                            heightIsValid = true
                        }
                    }
                    if (it.contains("in")) {
                        val parts = it.split("in")
                        if (parts.size == 2 && parts.first().toInt() in 59..76) {
                            heightIsValid = true
                        }
                    }
                }

                var hclIsValid = false
                document.hcl?.let {
                    for (i in 1 until it.length) {
                        val c = it[i]
                        hclIsValid = Character.digit(c, 16) != -1
                    }
                    hclIsValid = it.first() == '#'
                }

                val eclIsValid = document.ecl in arrayListOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

                var pidIsValid = false
                document.pid?.let { pid ->
                    pidIsValid = pid.filter { it.isDigit() }.count() == 9
                }

                return byr in 1920..2002 &&
                        iyr in 2010..2020 &&
                        eyr in 2020..2030 &&
                        heightIsValid && hclIsValid && eclIsValid && pidIsValid

            }
        }
    }

    private val input = this.javaClass.getResourceAsStream("input4.txt") ?: throw InvalidPathException( "input4", "Invalid input")
    private val lines = Utils.convertFileToStringArray(input)

    @Test
    fun ex1() {
        var currentDocument = ""
        val scannedDocuments = mutableListOf<Document>()
        lines.forEach {
            if (it.isEmpty()) {
                scannedDocuments.add(Document(currentDocument))
                currentDocument = ""
            } else {
                currentDocument += " $it"
            }
        }
        scannedDocuments.add(Document(currentDocument))

        println("There are ${scannedDocuments.filter(Document.Companion::isValid).count()} valid documents")
    }

    @Test
    fun ex2() {
        var currentDocument = ""
        val scannedDocuments = mutableListOf<Document>()
        lines.forEach {
            if (it.isEmpty()) {
                scannedDocuments.add(Document(currentDocument))
                currentDocument = ""
            } else {
                currentDocument += " $it"
            }
        }
        scannedDocuments.add(Document(currentDocument))

        println("There are ${scannedDocuments.filter(Document.Companion::isValid2).count()} valid documents")
    }
}
