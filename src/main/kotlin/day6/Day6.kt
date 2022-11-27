package day6

import readAllText
import kotlin.time.measureTime

fun main() = measureTime {
    println(part1(readAllText("local/day6_input.txt")))
    println(part2(readAllText("local/day6_input.txt")))
}.let { println(it) }

private data class Command(val op: Op, val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    enum class Op(val desc: String) {
        On("turn on"),
        Off("turn off"),
        Toggle("toggle"),
    }
}

private fun parse(line: String) =
    "(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex().matchEntire(line)
        ?.destructured
        ?.let { (c, x1, y1, x2, y2) ->
            Command(Command.Op.values().single { it.desc == c }, x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }
        ?: error("WTF `$line`")

fun part1(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map(::parse)
    .fold(Array(1000) { IntArray(1000) }) { acc, (command, x1, y1, x2, y2) ->
        (x1..x2).forEach { x ->
            (y1..y2).forEach { y ->
                when (command) {
                    Command.Op.On -> acc[x][y] = 1
                    Command.Op.Off -> acc[x][y] = 0
                    Command.Op.Toggle -> acc[x][y] = 1 - acc[x][y]
                }
            }
        }
        acc
    }
//    .also(::writeImg)
    .sumOf { it.sum() }

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .count()
