package day11

import readAllText

fun main() {
    println(part1(readAllText("local/day11_input.txt")))
    println(part2(readAllText("local/day11_input.txt")))
}

fun part1(input: String) = puzzle(input)
fun part2(input: String) = puzzle(puzzle(input))

private fun puzzle(input: String) = input.trim().decode()
    .let { n ->
        generateSequence(n + 1) { it + 1 }
            .map { it.encode() }
            .first { pass -> threeConsecutive(pass) && twoPairs(pass) }
    }

private fun twoPairs(pass: String) = pass.windowedSequence(2)
    .filter { it[0] == it[1] }
    .distinct()
    .take(2)
    .count() == 2

private fun threeConsecutive(pass: String) = pass.windowedSequence(3)
    .any { it[2] == it[1] + 1 && it[1] == it[0] + 1 }

private val alphabet = "abcdefghjkmnpqrstuvwxyz"
private val positions = alphabet
    .mapIndexed { i, c -> c to i }
    .toMap()

private fun String.decode() = fold(0L) { acc, c ->
    acc * alphabet.length + positions[c]!!
}

private fun Long.encode() = buildString {
    var r = this@encode
    do {
        val m = r.mod(alphabet.length)
        append(alphabet[m])
        r /= alphabet.length
    } while (r > 0)
}.reversed()
