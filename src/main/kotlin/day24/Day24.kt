package day24

import readAllText

fun main() {
    println(part1(readAllText("local/day24_input.txt")))
    println(part2(readAllText("local/day24_input.txt")))
}

fun part1(input: String) = input.lineSequence().filterNot(String::isBlank)
    .count()

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .count()
