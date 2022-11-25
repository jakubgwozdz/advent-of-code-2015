package day4

import java.security.MessageDigest

fun part1(input: String): Long {
    var l = 1L
    while (!md5("${input.trim()}$l").let { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() in (0..15) }) l++
    return l
}

fun md5(str: String) = MessageDigest.getInstance("MD5")
    .digest(str.toByteArray())

fun part2(input: String): Long {
    var l = 1L
    while (!md5("${input.trim()}$l").let { it[0].toInt() == 0 && it[1].toInt() == 0 && it[2].toInt() == 0 }) l++
    return l
}

fun main() {
    println(part1("abcdef"))
}
