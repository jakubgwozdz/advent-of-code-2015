package day13

import permutations
import readAllText

fun main() {
    println(part1(readAllText("local/day13_input.txt")))
    println(part2(readAllText("local/day13_input.txt")))
}

private val regex = "(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+)\\.".toRegex()

fun part1(input: String): Int {
    val graph = graph(input)
    val people = graph.keys.toList()
    return permutations(people.size)
        .map { it.map { people[it] } }
        .maxOf { order ->
            (0..people.size - 1).sumOf {
                val a = order[it]
                val b = order[(it + 1) % people.size]
                graph[a]!![b]!! + graph[b]!![a]!!
            }
        }
}

fun part2(input: String): Int {
    val graph = graph(input)
    val people = graph.keys.toList() + "me"
    return permutations(people.size)
        .map { it.map { people[it] } }
        .maxOf { order ->
            (0..people.size - 1).sumOf {
                val a = order[it]
                val b = order[(it + 1) % people.size]
                (graph[a]?.get(b) ?: 0) + (graph[b]?.get(a) ?: 0)
            }
        }
}

private fun graph(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map { line -> regex.matchEntire(line)?.destructured ?: error("WTF `$line`") }
    .map { (a, b, c, d) -> a to (d to if (b == "gain") c.toInt() else -c.toInt()) }
    .groupBy { it.first }
    .mapValues { (_, v) ->
        v.map { it.second }.groupBy { it.first }.mapValues { (_, v) -> v.map { it.second }.single() }
    }
