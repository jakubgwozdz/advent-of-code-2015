package day3

private val startPoint = 0 to 0
private fun newPoint(c: Char, origin: Pair<Int, Int>) = when (c) {
    'v' -> origin.first + 1 to origin.second
    '^' -> origin.first - 1 to origin.second
    '>' -> origin.first to origin.second + 1
    '<' -> origin.first to origin.second - 1
    else -> error("wtf `$c`")
}

fun part1(input: String) = input.fold(startPoint to setOf(startPoint)) { acc, c ->
    val (santa, visited) = acc
    val newSanta = newPoint(c, santa)
    newSanta to (visited + newSanta)
}.second.size

fun part2(input: String) = input.foldIndexed((startPoint to startPoint) to setOf(startPoint)) { i, acc, c ->
    val (santas, visited) = acc
    val (santa, roboSanta) = santas
    if (i % 2 == 0) {
        val newSanta = newPoint(c, santa)
        (newSanta to roboSanta) to (visited + newSanta)
    } else {
        val newRoboSanta = newPoint(c, roboSanta)
        (santa to newRoboSanta) to (visited + newRoboSanta)
    }
}.second.size
