package day22

import readAllText

fun main() {
    println(part1(readAllText("local/day22_input.txt")))
    println(part2(readAllText("local/day22_input.txt")))
}

fun part1(input: String): Int {
    val hitPoints = input.substringAfter("Hit Points: ").lineSequence().first().toInt()
    val damage = input.substringAfter("Damage: ").lineSequence().first().toInt()
    return game(GameState(50, 500, hitPoints, damage))?.let { it as Win }?.spentMana ?: error("no win")
}

fun part2(input: String): Int {
    val hitPoints = input.substringAfter("Hit Points: ").lineSequence().first().toInt()
    val damage = input.substringAfter("Damage: ").lineSequence().first().toInt()
    return game(GameState(50, 500, hitPoints, damage), true)?.let { it as Win }?.spentMana ?: error("no win")
}

sealed interface Result {
    fun ifNotEnded(op: GameState.() -> GameState): Result
}

data class Win(
    val spentMana: Int,
    val spells: List<Spell>,
) : Result {
    override fun ifNotEnded(op: GameState.() -> GameState) = this
}

data class Lose(
    val spells: List<Spell>,
) : Result {
    override fun ifNotEnded(op: GameState.() -> GameState) = this
}

data class GameState(
    val playerHP: Int,
    val playerMana: Int,
    val bossHP: Int,
    val bossDamage: Int,
    val armor: Int = 0,
    val spentMana: Int = 0,
    val shieldLeft: Int = 0,
    val poisonLeft: Int = 0,
    val rechargeLeft: Int = 0,
    val spells: List<Spell> = emptyList(),
) : Result {
    override fun ifNotEnded(op: GameState.() -> GameState) = op.invoke(this).checkEnd()

    fun applyEffects() = copy(
        playerMana = if (rechargeLeft > 0) playerMana + 101 else playerMana,
        bossHP = if (poisonLeft > 0) bossHP - 3 else bossHP,
        armor = if (shieldLeft > 0) 7 else 0,
        shieldLeft = if (shieldLeft > 0) shieldLeft - 1 else 0,
        poisonLeft = if (poisonLeft > 0) poisonLeft - 1 else 0,
        rechargeLeft = if (rechargeLeft > 0) rechargeLeft - 1 else 0,
    )

    fun bossAttack() = copy(
        playerHP = playerHP - (bossDamage - armor).coerceAtLeast(1)
    )

    fun prepareSpell(spell: Spell) = copy(
        playerMana = playerMana - spell.cost,
        spentMana = spentMana + spell.cost,
        spells = spells + spell
    )

    fun performSpell(spell: Spell) = spell.op(this)

    fun checkEnd() =
        if (playerHP <= 0 || playerMana < 0) Lose(spells)
        else if (bossHP <= 0) Win(spentMana, spells)
        else this

    fun easyOrHard(hard: Boolean) = if (hard) copy(playerHP = playerHP - 1) else this

    fun round(spell: Spell, hard: Boolean) = this
        .ifNotEnded { easyOrHard(hard) }
        .ifNotEnded { applyEffects() }
        .ifNotEnded { prepareSpell(spell) }
        .ifNotEnded { performSpell(spell) }
        .ifNotEnded { applyEffects() }
        .ifNotEnded { bossAttack() }
}

enum class Spell(val cost: Int, val op: (GameState) -> GameState) {
    Missile(53, { it.copy(bossHP = it.bossHP - 4) }),
    Drain(73, { it.copy(playerHP = it.playerHP + 2, bossHP = it.bossHP - 2) }),
    Shield(113, { it.copy(shieldLeft = 6) }),
    Poison(173, { it.copy(poisonLeft = 6) }),
    Recharge(229, { it.copy(rechargeLeft = 5) }),
}

class Game {
    var best: Int? = null
    fun perform(state: GameState, hard: Boolean = false): Result? = DeepRecursiveFunction<GameState, Result?> { s ->
        Spell.entries
            .map { s.round(it, hard) }
            .mapNotNull { result ->
                when (result) {
                    is Win -> result.also { best = best?.coerceAtMost(it.spentMana) ?: it.spentMana }
                    is Lose -> null
                    is GameState -> if (best != null && best!! <= result.spentMana) null
                    else callRecursive(result)
                }
            }
            .minByOrNull { (it as Win).spentMana }
    }(state)
}

fun game(state: GameState, hard: Boolean = false): Result? = Game().perform(state, hard)
