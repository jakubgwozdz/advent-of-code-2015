package day7

import readAllText

fun main() {
    println(part1(readAllText("local/day7_input.txt")))
    println(part2(readAllText("local/day7_input.txt")))
}

sealed interface Op {
    fun value(circuit: Map<String, Op>): Int
    data class Signal(val v: Int) : Op {
        override fun value(circuit: Map<String, Op>): Int = v
    }

    data class Short(val w1: String) : Op {
        private var cached: Int? = null
        override fun value(circuit: Map<String, Op>): Int = cached
            ?: circuit[w1]!!.value(circuit)
                .also { cached = it }
    }

    data class Or(val w1: String, val w2: String) : Op {
        private var cached: Int? = null
        override fun value(circuit: Map<String, Op>): Int = cached
            ?: (circuit[w1]!!.value(circuit) or circuit[w2]!!.value(circuit))
                .also { cached = it }
    }

    data class Not(val w1: String) : Op {
        private var cached: Int? = null
        override fun value(circuit: Map<String, Op>): Int = cached
            ?: (circuit[w1]!!.value(circuit).inv() and 0xffff)
                .also { cached = it }
    }

    data class And(val w1: String, val w2: String) : Op {
        private var cached: Int? = null
        override fun value(circuit: Map<String, Op>): Int = cached
            ?: (circuit[w1]!!.value(circuit) and circuit[w2]!!.value(circuit))
                .also { cached = it }
    }

    data class LShift(val w1: String, val sh: Int) : Op {
        private var cached: Int? = null
        override fun value(circuit: Map<String, Op>): Int = cached
            ?: (circuit[w1]!!.value(circuit) shl sh and 0xffff)
                .also { cached = it }
    }

    data class RShift(val w1: String, val sh: Int) : Op {
        private var cached: Int? = null
        override fun value(circuit: Map<String, Op>): Int = cached
            ?: (circuit[w1]!!.value(circuit) ushr sh and 0xffff)
                .also { cached = it }
    }
}


private fun parse(line: String) =
    "(\\d+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
        ?.let { (v, wire) -> wire to Op.Signal(v.toInt()) }
        ?: "(\\w+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, wire) -> wire to Op.Short(w1) }
        ?: "(\\w+) OR (\\w+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, w2, wire) -> wire to Op.Or(w1, w2) }
        ?: "(\\w+) AND (\\w+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, w2, wire) -> wire to Op.And(w1, w2) }
        ?: "NOT (\\w+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, wire) -> wire to Op.Not(w1) }
        ?: "(\\w+) LSHIFT (\\d+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, sh, wire) -> wire to Op.LShift(w1, sh.toInt()) }
        ?: "(\\w+) RSHIFT (\\d+) -> (\\w+)".toRegex().matchEntire(line)?.destructured
            ?.let { (w1, sh, wire) -> wire to Op.RShift(w1, sh.toInt()) }
        ?: error("Wtf `$line`")

fun part1(input: String): Int {
    val circuit = input.lineSequence().filterNot(String::isBlank)
        .map { parse(it) }
        .toMap()
    return circuit["a"]!!.value(circuit)
}

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .count()
