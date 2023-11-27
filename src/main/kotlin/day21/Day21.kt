package day21

import readAllText

fun main() {
    println(part1(readAllText("local/day21_input.txt")))
    println(part2(readAllText("local/day21_input.txt")))
}

fun part1(input: String) = parse(input).let { boss ->
    sortedCombinations()
        .first { game(it.second, boss) }
        .first
}

fun part2(input: String) = parse(input).let { boss ->
    sortedCombinations()
        .last { !game(it.second, boss) }
        .first
}

fun sortedCombinations(): List<Pair<Int, Stats>> {
    val basePlayerHp = 100
    val combinations = buildList {
        weapons.forEach { w ->
            (armor + empty).forEach { a ->
                (rings + empty).forEach { r1 ->
                    (rings + empty).forEach { r2 ->
                        if (r1 != r2 || r1 == empty) add(listOf(w, a, r1, r2))
                    }
                }
            }
        }
    }
    val sortedCombinations = combinations
        .map { (w, a, r1, r2) ->
            val cost = w.cost + a.cost + r1.cost + r2.cost
            val player = Stats(
                basePlayerHp,
                w.damage + a.damage + r1.damage + r2.damage,
                w.armor + a.armor + r1.armor + r2.armor
            )
            cost to player
        }
        .sortedBy { it.first }
    return sortedCombinations
}

val regex = "\\s*(.+?)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*".toRegex()
val weapons = """
    Weapons:    Cost  Damage  Armor
    Dagger        8     4       0
    Shortsword   10     5       0
    Warhammer    25     6       0
    Longsword    40     7       0
    Greataxe     74     8       0
    """.trimIndent().parseItems()
val armor = """
    Armor:      Cost  Damage  Armor
    Leather      13     0       1
    Chainmail    31     0       2
    Splintmail   53     0       3
    Bandedmail   75     0       4
    Platemail   102     0       5
    """.trimIndent().parseItems()
val rings = """
    Rings:      Cost  Damage  Armor
    Damage +1    25     1       0
    Damage +2    50     2       0
    Damage +3   100     3       0
    Defense +1   20     0       1
    Defense +2   40     0       2
    Defense +3   80     0       3
    """.trimIndent().parseItems()
val empty = Item("none", 0, 0, 0)

fun String.parseItems() = lines()
    .mapNotNull { regex.matchEntire(it)?.destructured }
    .map { (id, c, d, a) -> Item(id, c.toInt(), d.toInt(), a.toInt()) }

data class Item(val id: String, val cost: Int, val damage: Int, val armor: Int)
data class Stats(val hitPoints: Int, val damage: Int, val armor: Int)

fun parse(input: String) = input.lines().let { (a, b, c) ->
    Stats(
        a.substringAfter("Hit Points: ").trim().toInt(),
        b.substringAfter("Damage: ").trim().toInt(),
        c.substringAfter("Armor: ").trim().toInt(),
    )
}

fun game(player: Stats, boss: Stats): Boolean =
    endurance(player, boss) >= endurance(boss, player)

fun endurance(character: Stats, attacker: Stats) =
    (character.hitPoints - 1) / (attacker.damage - character.armor).coerceAtLeast(1)
