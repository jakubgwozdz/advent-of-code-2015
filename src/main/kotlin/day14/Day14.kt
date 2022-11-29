package day14

import readAllText

fun main() {
    println(part1(readAllText("local/day14_input.txt")))
    println(part2(readAllText("local/day14_input.txt")))
}

data class Reinder(val name: String, val speed: Int, val time: Int, val rest: Int)

private val regex = "(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.".toRegex()

fun part1(input: String) = parse(input)
    .maxOf { r ->
        distance(r, 2503)
    }

fun part2(input: String): Int {
    val reinders = parse(input).toList()
    val score = reinders.associate { it.name to 0 }.toMutableMap()
    (1..2503).forEach { elapsed ->
        reinders.groupBy { distance(it, elapsed) }.maxBy { (k, v) -> k }.value
            .forEach { score[it.name] = score[it.name]!! + 1 }
    }

    return score.maxOf { it.value }
}

private fun distance(r: Reinder, elapsed: Int): Int {
    val cycles = elapsed / (r.time + r.rest)
    val remainder = elapsed % (r.time + r.rest)
    return cycles * r.speed * r.time + minOf(r.time, remainder) * r.speed
}

private fun parse(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map { line -> regex.matchEntire(line)?.destructured ?: error("WTF `$line`") }
    .map { (a, b, c, d) -> Reinder(a, b.toInt(), c.toInt(), d.toInt()) }
