package day15

import readAllText

fun main() {
    println(part1(readAllText("local/day15_input.txt")))
    println(part2(readAllText("local/day15_input.txt")))
}

fun part1(input: String): Long {
    val ingredients = parse(input).toList()
    return possibilities()
        .map { counts ->
            counts.zip(ingredients) { c, i -> i * c }.reduce { a, i -> a + i }
        }
        .map { total->
            (total.a.coerceAtLeast(0) * total.b.coerceAtLeast(0) * total.c.coerceAtLeast(0) * total.d.coerceAtLeast(0))
        }
        .max()
}
fun part2(input: String): Long {
    val ingredients = parse(input).toList()
    return possibilities()
        .map { counts ->
            counts.zip(ingredients) { c, i -> i * c }.reduce { a, i -> a + i }
        }
        .filter { it.e == 500L }
        .map {
            it.a.coerceAtLeast(0) * it.b.coerceAtLeast(0) * it.c.coerceAtLeast(0) * it.d.coerceAtLeast(0)
        }
        .max()
}

private fun possibilities() = sequence {
    (0..100L).forEach { i ->
        (0..100 - i).forEach { j ->
            (0..100 - i - j).forEach { k ->
                yield(listOf(i, j, k, 100 - i - j - k))
            }
        }
    }
}

data class Ingredients(val a: Long, val b: Long, val c: Long, val d: Long, val e: Long) {
    operator fun times(i: Long) = Ingredients(a * i, b * i, c * i, d * i, e * i)
    operator fun plus(o: Ingredients) = Ingredients(a + o.a, b + o.b, c + o.c, d + o.d, e + o.e)
}

private val regex =
    "\\w+: capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)".toRegex()

private fun parse(input: String) = input.lineSequence().filterNot(String::isBlank)
    .map { line -> regex.matchEntire(line)?.destructured ?: error("WTF `$line`") }
    .map { (a, b, c, d, e) -> Ingredients(a.toLong(), b.toLong(), c.toLong(), d.toLong(), e.toLong()) }
