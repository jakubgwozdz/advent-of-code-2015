import org.intellij.lang.annotations.Language
import java.nio.file.Files
import java.nio.file.Path
import kotlin.time.measureTime

fun main() {

    @Language("kotlin") val content = """
        package day0

        import readAllText

        fun main() {
            println(part1(readAllText("local/day0_input.txt")))
            println(part2(readAllText("local/day0_input.txt")))
        }

        fun part1(input: String) = input.lineSequence().filterNot(String::isBlank)
            .count()

        fun part2(input: String) = input.lineSequence().filterNot(String::isBlank)
            .count()

    """.trimIndent()


    (1..25).forEach { day ->
        val p = Path.of("src/main/kotlin/day${day}/Day${day}.kt")
        if (!Files.exists(p)) {
            Files.createDirectories(p.parent)
            Files.writeString(p, content.replace("day0", "day$day"))
        }
    }

    measureTime {
        println(day1.part1(readAllText("local/day1_input.txt")))
        println(day1.part2(readAllText("local/day1_input.txt")))
        println(day2.part1(readAllText("local/day2_input.txt")))
        println(day2.part2(readAllText("local/day2_input.txt")))
        println(day3.part1(readAllText("local/day3_input.txt")))
        println(day3.part2(readAllText("local/day3_input.txt")))
        println(day4.part1(readAllText("local/day4_input.txt")))
        println(day4.part2(readAllText("local/day4_input.txt")))
        println(day5.part1(readAllText("local/day5_input.txt")))
        println(day5.part2(readAllText("local/day5_input.txt")))
    }.also { println(it) }
}

fun readAllText(filePath: String): String = Files.readString(Path.of(filePath))

