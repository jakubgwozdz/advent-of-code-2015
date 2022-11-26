package day4

import java.security.MessageDigest

const val b0 = 0.toByte()
const val bF = 15.toByte()

private val fiveZeroes: ByteArray.() -> Boolean = { this[0] == b0 && this[1] == b0 && this[2] in b0..bF }
private val sixZeroes: ByteArray.() -> Boolean = { this[0] == b0 && this[1] == b0 && this[2] == b0 }

private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray())

fun part1(input: String) = generateSequence(1L) { it + 1 }
    .takeWhile { !md5("${input.trim()}$it").fiveZeroes() }
    .last() + 1

fun part2(input: String) = generateSequence(1L) { it + 1 }
    .takeWhile { !md5("${input.trim()}$it").sixZeroes() }
    .last() + 1

fun main() {
    println(part1("abcdef"))
}
