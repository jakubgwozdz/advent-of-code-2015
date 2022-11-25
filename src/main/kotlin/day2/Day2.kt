package day2

private val pattern = "(\\d+)x(\\d+)x(\\d+)".toRegex()
private fun parse(line: String): Triple<Long, Long, Long> {
    val (l, w, h) = pattern.matchEntire(line)?.destructured ?: error("WTF `$line`")
    return Triple(l.toLong(), w.toLong(), h.toLong())
}

fun part1(input: String) = input.lineSequence().filterNot(String::isEmpty).map(::parse)
    .sumOf { (l, w, h) ->
        2 * l * w + 2 * w * h + 2 * h * l + minOf(l * w, w * h, h * l)
    }

fun part2(input: String) = input.lineSequence().filterNot(String::isEmpty).map(::parse)
    .sumOf { (l, w, h) ->
        2 * minOf(l + w, w + h, h + l) + l * w * h
    }
