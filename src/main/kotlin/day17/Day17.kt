package day17

import readAllText

fun main() {
    println(part1(readAllText("local/day17_input.txt")))
    println(part2(readAllText("local/day17_input.txt")))
}

fun part1(input: String) = parse(input).checkTree(0, 150)
    .count()

fun part2(input: String) = parse(input).checkTree(0, 150)
    .groupBy { it }.minBy { it.key }.value
    .count()

private fun parse(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map { it.toInt() }
    .toList()

internal fun List<Int>.checkTree(from: Int, remaining: Int): List<Int> = (from..size - 1)
    .flatMap { next ->
        val remainingAfterThis = remaining - this[next]
        if (remainingAfterThis < 0) emptyList()
        else if (remainingAfterThis == 0) listOf(1)
        else checkTree(next + 1, remaining - this[next]).map { it + 1 }
    }
