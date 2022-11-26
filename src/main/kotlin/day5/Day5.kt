package day5

import readAllText

fun main() {
    println(part1(readAllText("local/day5_input.txt")))
    println(part2(readAllText("local/day5_input.txt")))
}

fun part1(input: String) = input.lineSequence().filterNot(String::isBlank)
    .filter { it.count { c -> c in "aeiou" } >= 3 }
    .filter { it.windowedSequence(2).any { sub -> sub[0] == sub[1] } }
    .filterNot { it.contains("ab") || it.contains("cd") || it.contains("pq") || it.contains("xy") }
    .count()

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .filter {
        (0..it.length - 4).any { i ->
            (i + 2..it.length - 2).any { j ->
                it[i] == it[j] && it[i + 1] == it[j + 1]
            }
        }
    }
    .filter { it.windowedSequence(3).any { sub -> sub[0] == sub[2] } }
    .count()

