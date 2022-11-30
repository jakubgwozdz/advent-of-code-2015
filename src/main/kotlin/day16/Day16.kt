package day16

import readAllText

fun main() {
    println(part1(readAllText("local/day16_input.txt")))
    println(part2(readAllText("local/day16_input.txt")))
}

private val results = """
    children: 3
    cats: 7
    samoyeds: 2
    pomeranians: 3
    akitas: 0
    vizslas: 0
    goldfish: 5
    trees: 3
    cars: 2
    perfumes: 1
""".trimIndent().lineSequence()
    .filterNot(String::isBlank)
    .map { it.split(":").let { (a, b) -> a.trim() to b.trim().toInt() } }
    .toMap()

fun part1(input: String) = parse(input)
    .filter { (id, vals) -> vals.all { (k, v) -> results[k] == v } }
    .single()
    .first

fun part2(input: String) = parse(input)
    .filter { (id, vals) ->
        vals.all { (k, v) ->
            when (k) {
                "cats", "trees" -> results[k]!! < v
                "pomeranians", "goldfish" -> results[k]!! > v
                else -> results[k] == v
            }
        }
    }
    .single()
    .first

private val regex = "Sue (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)".toRegex()

private fun parse(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map { line -> regex.matchEntire(line)?.destructured ?: error("WTF `$line`") }
    .map { (id, k1, v1, k2, v2, k3, v3) -> id.toInt() to mapOf(k1 to v1.toInt(), k2 to v2.toInt(), k3 to v3.toInt()) }
