import java.util.*

fun main() {
    val separator = NumberSeparator()

    val scanner = Scanner(System.`in`)
    val q = scanner.nextInt()
    for (i in 0 until q) {
        val str = scanner.next()
        val checkResult = separator.checkIfBeautiful(str)
        if (checkResult.isBeautiful) {
            println("YES ${checkResult.smallestItem}")
        } else {
            println("NO")
        }
    }
}
