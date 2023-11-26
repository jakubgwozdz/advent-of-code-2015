package day20

import readAllText

fun main() {
    println(part1(readAllText("local/day20_input.txt")))
    println(part2(readAllText("local/day20_input.txt")))
}

fun part1(input: String): Int {
    val n = input.trim().toInt()
    return solution(n, 10, n)
}

fun part2(input: String): Int {
    val n = input.trim().toInt()
    return solution(n, 11, 50)
}

private fun solution(n: Int, presents: Int, maxHouses: Int): Int {
    val arr = LongArray(n + 1)
    var e = 1
    var upper = (n / presents)+1
    while (true) {
        var i = e
        var x = 0
        while (i <= upper && x < maxHouses) {
            arr[i] = arr[i] + e * presents
            if (arr[i] >= n) upper = i
            i += e
            x++
        }
        e++
        if (e > upper) {
            return arr.indexOfFirst { it >= n }
        }
    }
}
