package day19

import readAllText

fun main() {

//    println(listOf(1,2,3,2,3,4,5).allReplacements(0, listOf(1,2,3,2,3,4,5)).toList())

    val test = """
        e => H
        e => O
        H => HO
        H => OH
        O => HH
        
        HOHOHO
    """.trimIndent()
    println(part1(test))
    println(part2(test))
    val data = readAllText("local/day19_input.txt")
    println(part1(data))
    println(part2(data))
}

fun part1(input: String) = input.lineSequence().filterNot(String::isBlank)
    .let {
        val rules = it.takeWhile { it.contains(" => ") }
        val molecule = it.last()
        rules to molecule
    }
    .let { (rules, molecule) ->
        rules.flatMap { rule ->
            val from = rule.substringBefore(" => ")
            val to = rule.substringAfter(" => ")
            molecule.indices
                .filter { i -> molecule.substring(i).startsWith(from) }
                .map { i ->
                    molecule.substring(0, i) + to + molecule.substring(i + from.length)
                }
        }
            .toSet()
    }
    .count()

val regex = "[A-Z][a-z]*".toRegex()

@JvmInline
value class Atom(private val s: String) {
    override fun toString(): String = s
}
typealias Molecule = List<Atom>

fun String.atom() = Atom(this)
fun String.atoms() = regex.findAll(this).map(MatchResult::value).map(String::atom).toList()
val start = listOf("e".atom())

fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
    .let {
        val rules = it.takeWhile { it.contains(" => ") }
        val molecule = it.last()
        rules to molecule
    }
    .let { (rulesStr, moleculeStr) ->
        val molecule = moleculeStr.atoms()
        val rules = rulesStr.map { it.substringBefore(" => ").atom() to it.substringAfter(" => ").atoms() }.toSet()
        findShortest(molecule, rules) ?: error("no result")
    }

fun findShortest(end: Molecule, rules: Set<Pair<Atom, Molecule>>): Int? =
    DeepRecursiveFunction<Molecule, Int?> { molecule ->
        if (molecule == start) 0
        else molecule.backtracks(rules).firstNotNullOfOrNull { callRecursive(it) }?.let { it + 1 }
    }(end)

private fun Molecule.backtracks(rules: Set<Pair<Atom, Molecule>>) = rules.asSequence()
    .flatMap { (k, v) ->
        (0..(size - v.size)).asSequence()
            .filter { subList(it, it + v.size) == v }
            .map { take(it) + k + drop(it + v.size) }
    }
    .distinct()

