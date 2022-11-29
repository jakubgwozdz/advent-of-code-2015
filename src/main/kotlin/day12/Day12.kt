package day12

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import readAllText

fun main() {
    println(part1(readAllText("local/day12_input.txt")))
    println(part2(readAllText("local/day12_input.txt")))
}

fun part1(input: String) = "(-?\\d+)".toRegex().findAll(input.trim())
    .sumOf { it.value.toLong() }

fun part2(input: String) = Json.decodeFromString<JsonObject>(input)
    .stripRed()
    .let { part1(it.toString()) }

private fun JsonObject.stripRed(): JsonObject = buildJsonObject {
    if (this@stripRed.none { (k, v) -> v is JsonPrimitive && v.content == "red" })
        this@stripRed.forEach { (k, v) ->
            when (v) {
                is JsonObject -> put(k, v.stripRed())
                is JsonArray -> put(k, v.stripRed())
                is JsonPrimitive -> put(k, v)
                JsonNull -> TODO()
            }
        }
}

private fun JsonArray.stripRed(): JsonArray = buildJsonArray {
    this@stripRed.forEach { v ->
        when (v) {
            is JsonObject -> add(v.stripRed())
            is JsonArray -> add(v.stripRed())
            is JsonPrimitive -> add(v)
            JsonNull -> TODO()
        }
    }
}
