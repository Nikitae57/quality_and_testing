package lab1

import java.util.*
import kotlin.math.abs
import kotlin.system.exitProcess

fun main() {
    val scanner = Scanner(System.`in`)
    val firstBorder = scanner.nextInt()
    val secondBorder = scanner.nextInt()

    if (firstBorder.toString().length != 2
        || secondBorder.toString().length != 2
    ) {
        println("Incorrect borders")
        exitProcess(-1)
    }

    val numbersToPrint = mutableListOf<Int>()
    for (i in firstBorder..secondBorder) {
        if (abs((i / 10) - (i % 10)) == 1) { numbersToPrint.add(i) }
    }

    println(numbersToPrint)
    println("Numbers been printed: ${numbersToPrint.size}")

    val oddNumber = numbersToPrint.filter { it % 2 != 0 }.size
    println("Odd number: $oddNumber")
    println("Even number ${numbersToPrint.size - oddNumber}")
}