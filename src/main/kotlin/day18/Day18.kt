package day18

import readAllText

fun main() {
    println(
        part2(
            """
                ##.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####.#
                """.trimIndent(), 5
        )
    )
    println(part1(readAllText("local/day18_input.txt")))
    println(part2(readAllText("local/day18_input.txt")))
}

private fun adj(row: Int, col: Int) = sequenceOf(
    row + 1 to col, row + 1 to col + 1, row to col + 1, row - 1 to col + 1,
    row - 1 to col, row - 1 to col - 1, row to col - 1, row + 1 to col - 1,
)

fun part1(input: String, steps: Int = 100) = input.lineSequence().filterNot(String::isBlank)
    .toList()
    .let { grid ->
        (1..steps).fold(grid) { acc, _ ->
            buildList {
                repeat(acc.size) { row ->
                    buildString {
                        repeat(acc[row].length) { col ->
                            val current = acc[row][col]
                            val adj = adj(row, col)
                                .filter { (r, c) -> r in 0..acc.lastIndex && c in 0..acc[r].lastIndex }
                                .sumOf { (r, c) -> if (acc[r][c] == '#') 1 as Int else 0 }
                            when {
                                current == '#' && adj in (2..3) -> '#'
                                current == '#' -> '.'
                                current == '.' && adj == 3 -> '#'
                                current == '.' -> '.'
                                else -> error("WTF `${acc[row]}")
                            }.also { append(it) }
                        }
                    }.also { add(it) }
                }
            }
        }
    }
    .sumOf { it.count { it == '#' } }

fun part2(input: String, steps: Int = 100) = input.lineSequence().filterNot(String::isBlank)
    .toList()
    .let { grid ->
        grid.mapIndexed { row, line ->
            line.mapIndexed { col, c ->
                when {
                    (row == 0 || row == grid.lastIndex) && (col == 0 || col == line.lastIndex) -> '#'
                    else -> c
                }
            }.joinToString("")
        }
    }
    .let { grid ->
        (1..steps).fold(grid) { acc, _ ->
            buildList {
                repeat(acc.size) { row ->
                    buildString {
                        repeat(acc[row].length) { col ->
                            val current = acc[row][col]
                            val adj = adj(row, col)
                                .filter { (r, c) -> r in 0..acc.lastIndex && c in 0..acc[r].lastIndex }
                                .sumOf { (r, c) -> if (acc[r][c] == '#') 1 as Int else 0 }
                            when {
                                (row == 0 || row == acc.lastIndex) && (col == 0 || col == acc[row].lastIndex) -> '#'
                                current == '#' && adj in (2..3) -> '#'
                                current == '#' -> '.'
                                current == '.' && adj == 3 -> '#'
                                current == '.' -> '.'
                                else -> error("WTF `${acc[row]}")
                            }.also { append(it) }
                        }
                    }.also { add(it) }
                }
            }
//                .also { println(it.joinToString("\n") + "\n") }
        }
    }
    .sumOf { it.count { it == '#' } }
