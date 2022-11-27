package day7

import readAllText

fun main() {
    println(part1(readAllText("local/day7_input.txt")))
    println(part2(readAllText("local/day7_input.txt")))
}

private fun Map<String, Op>.calc(wire: String) = wire.toIntOrNull() ?: this[wire]!!.value(this)

sealed class Op(val op: Map<String, Op>.() -> Int) {
    var cached: Int? = null
    fun value(circuit: Map<String, Op>): Int = cached ?: op(circuit).also { cached = it }

    data class Short(val w1: String) : Op({ calc(w1) })
    data class Or(val w1: String, val w2: String) : Op({ calc(w1) or calc(w2) })
    data class And(val w1: String, val w2: String) : Op({ calc(w1) and calc(w2) })
    data class Not(val w1: String) : Op({ calc(w1).inv() and 0xffff })
    data class LShift(val w1: String, val sh: Int) : Op({ calc(w1) shl sh and 0xffff })
    data class RShift(val w1: String, val sh: Int) : Op({ calc(w1) ushr sh and 0xffff })
}


private fun parse(line: String) =
    "(\\w+) -> ([a-z]+)".toRegex().matchEntire(line)?.destructured
        ?.let { (w1, wire) -> wire to Op.Short(w1) }
        ?: "(\\w+) OR (\\w+) -> ([a-z]+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, w2, wire) -> wire to Op.Or(w1, w2) }
        ?: "(\\w+) AND (\\w+) -> ([a-z]+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, w2, wire) -> wire to Op.And(w1, w2) }
        ?: "NOT (\\w+) -> ([a-z]+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, wire) -> wire to Op.Not(w1) }
        ?: "(\\w+) LSHIFT (\\d+) -> ([a-z]+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, sh, wire) -> wire to Op.LShift(w1, sh.toInt()) }
        ?: "(\\w+) RSHIFT (\\d+) -> ([a-z]+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, sh, wire) -> wire to Op.RShift(w1, sh.toInt()) }
        ?: error("Wtf `$line`")

fun part1(input: String): Int {
    val circuit = input.lineSequence().filterNot(String::isBlank)
        .map { parse(it) }
        .toMap()
    return circuit["a"]!!.value(circuit)
}

fun part2(input: String): Int {
    val circuit = input.lineSequence().filterNot(String::isBlank)
        .map { parse(it) }
        .toMap().toMutableMap()
    val a = circuit["a"]!!.value(circuit)
    circuit["b"] = Op.Short(a.toString())
    circuit.forEach { (k, v) -> v.cached = null }
    return circuit["a"]!!.value(circuit)
}
