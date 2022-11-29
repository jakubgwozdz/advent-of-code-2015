package day9

import permutations
import readAllText

fun main() {
    println(part1(readAllText("local/day9_input.txt")))
    println(part2(readAllText("local/day9_input.txt")))
}

fun part1(input: String) = results(input).min()
fun part2(input: String) = results(input).max()

private fun results(input: String): List<Int> {
    val graph = input.lineSequence().filterNot(String::isBlank)
        .map {
            "(\\w+) to (\\w+) = (\\d+)".toRegex().matchEntire(it)?.destructured
                ?.let { (a, b, d) -> Triple(a, b, d.toInt()) }
                ?: error("WTF `$it`")
        }
        .flatMap { (a, b, d) -> listOf(Triple(a, b, d), Triple(b, a, d)) }
        .groupBy { it.first }
        .mapValues { (k, v) ->
            v.groupBy { it.second }.mapValues { (kk, vv) -> vv.single().third }
        }
    val cities = graph.keys.toList()
    val possibilities = permutations(cities.size)
        .map { l -> l.map { cities[it] } }
        .map { l -> l.windowed(2).sumOf { (a, b) -> graph[a]!![b]!! } }
        .toList()
    return possibilities
}
