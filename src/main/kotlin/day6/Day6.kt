package day6

import readAllText

fun main() {
    println(part1(readAllText("local/day6_input.txt")))
    println(part2(readAllText("local/day6_input.txt")))
}

private fun parse(line: String) =
    "(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex().matchEntire(line)
        ?.destructured
        ?.let { (c, x1, y1, x2, y2) ->
            Triple(c, x1.toInt() to y1.toInt(), x2.toInt() to y2.toInt())
        }
        ?: error("WTF `$line`")

fun part1(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map(::parse)
    .fold(Array(1000) { IntArray(1000) }) { acc, (command, from, to) ->
        println("$command: $from-$to")
        (from.first..to.first).forEach { x ->
            (from.second..to.second).forEach { y ->
                when (command) {
                    "turn on" -> acc[x][y] = 1
                    "turn off" -> acc[x][y] = 0
                    "toggle" -> acc[x][y] = 1 - acc[x][y]
                    else -> error("wtf `$command`")
                }
            }
        }
        acc
    }
    .sumOf { it.sum() }

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .count()
