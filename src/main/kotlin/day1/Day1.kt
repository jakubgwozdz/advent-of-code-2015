package day1

fun part1(input: String): Int = input.fold(0) { a, c ->
    when (c) {
        '(' -> a + 1
        ')' -> a - 1
        else -> error("'$c'? Really?")
    }
}

fun part2(input: String): Int = input.asSequence().runningFold(0) { a, c ->
    when (c) {
        '(' -> a + 1
        ')' -> a - 1
        else -> error("'$c'? Really?")
    }
}.takeWhile { it >= 0 }.count()

