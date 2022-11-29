package day10

import readAllText

fun main() {
    println(part1(readAllText("local/day10_input.txt")))
    println(part2(readAllText("local/day10_input.txt")))
}

fun part1(input: String) = puzzle(input, 40)
fun part2(input: String) = puzzle(input, 50)

private fun puzzle(input: String, times: Int) = input.lineSequence().filterNot(String::isBlank)
    .single()
    .let { (1..times).fold(it) { acc, _ -> generate(acc) } }
    .length

private fun generate(input: String): String = buildString {
    var c = input[0]
    var i = 1
    input.asSequence().drop(1).forEach { cc ->
        if (cc == c) i++ else {
            append("$i$c")
            c = cc
            i = 1
        }
    }
    append("$i$c")
}
