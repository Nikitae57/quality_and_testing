import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class NumberSeparatorTest {
    private fun performTest(input: String, expectedOutput: String) {
        val out = ByteArrayOutputStream()
        System.setOut(PrintStream(out))

        val testLines = input.split("\n")
        val q = testLines[0].toInt()
        val separator = NumberSeparator()
        for (i in 0 until q) {
            val checkResult = separator.checkIfBeautiful(testLines[i + 1])
            if (checkResult.isBeautiful) {
                println("YES ${checkResult.smallestItem}")
            } else {
                println("NO")
            }
        }

        val outStr = String(out.toByteArray())
        assertEquals(expectedOutput, outStr)
    }

    @Test
    fun test0() {
        val input = """
            6
            1
            2
            33
            4445
            8889
            8910
        """.trimIndent()

        val expectedOutput = """
            NO
            NO
            NO
            YES 44
            YES 88
            YES 8
            
        """.trimIndent()

        performTest(input, expectedOutput)
    }

    @Test
    fun test1() {
        val input = """
            7
            1234
            91011
            99100
            101103
            010203
            13
            1
        """.trimIndent()

        val expectedOutput = """
            YES 1
            YES 9
            YES 99
            NO
            NO
            NO
            NO
            
        """.trimIndent()

        performTest(input, expectedOutput)
    }

    @Test
    fun test2() {
        val input = """
            10
            73747576777879808182838485868788
            73747576777879808182838485868778
            29303132333435363738394041424344
            29303132333435363738394041424244
            16171819202122232425262728293031
            16171819202122232425262728292031
            60616263646566676869707172737475
            60616263646566676869707172727475
            90919293949596979899100101102103
            90919293949596979899100101002103
        """.trimIndent()

        val expectedOutput = """
            YES 73
            NO
            YES 29
            NO
            YES 16
            NO
            YES 60
            NO
            YES 90
            NO
            
        """.trimIndent()

        performTest(input, expectedOutput)
    }

    @Test
    fun test3() {
        val input = """
            10
            605704597605704598605704599
            605704597605704598605704598
            314865217314865218314865219
            314865217314865218314865209
            719059156719059157719059158
            719059156719059157719059058
            985134995985134996985134997
            985134995985134996985133997
            160099024160099025160099026
            160099024160099025160089026
        """.trimIndent()

        val expectedOutput = """
            YES 605704597
            NO
            YES 314865217
            NO
            YES 719059156
            NO
            YES 985134995
            NO
            YES 160099024
            NO
            
        """.trimIndent()

        performTest(input, expectedOutput)
    }
}