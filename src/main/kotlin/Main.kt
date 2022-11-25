import java.nio.file.Files
import java.nio.file.Path
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {
    measureTime {
        println(day1.part1(readAllText("local/day1_input.txt")))
        println(day1.part2(readAllText("local/day1_input.txt")))
        println(day2.part1(readAllText("local/day2_input.txt")))
        println(day2.part2(readAllText("local/day2_input.txt")))
        println(day3.part1(readAllText("local/day3_input.txt")))
        println(day3.part2(readAllText("local/day3_input.txt")))
        println(day4.part1(readAllText("local/day4_input.txt")))
        println(day4.part2(readAllText("local/day4_input.txt")))
    }.also { println(it) }
}

fun readAllText(filePath: String): String = Files.readString(Path.of(filePath))

