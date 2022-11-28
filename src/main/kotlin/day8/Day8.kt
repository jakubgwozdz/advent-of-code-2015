package day8

import readAllText

fun main() {
    println(part1(readAllText("local/day8_input.txt")))
    println(part2(readAllText("local/day8_input.txt")))
}

fun part1(input: String): Int = input.lineSequence().filterNot(String::isBlank)
    .sumOf { line ->
        var result = 2
        var pos = 0
        while (pos < line.length) {
            if (line[pos] == '\\') {
                result++
                pos++
                if (line[pos] == 'x') result += 2
            }
            pos++
        }
        result
    }

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .sumOf { line -> 2 + line.count { it == '\\' || it == '\"' } }
