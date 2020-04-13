package controller.test

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.test.Test
import model.test.TestResult
import model.test.TypeNames
import view.log.Logger
import java.io.*
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.URLClassLoader
import javax.management.ReflectionException
import kotlin.system.exitProcess

class TestDriver(private val kotlincPath: String) {
    private val runtime = Runtime.getRuntime()

    private val moshiAdapter: JsonAdapter<List<Test>> = kotlin.run {
        val type = Types.newParameterizedType(List::class.java, Test::class.java)
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(type)
    }

    private val buildDir = File("build/")

    fun runTests(modulePath: String, testsPath: String): List<TestResult> {
        Logger.log("Opening tests config...")

        val testsJson = try {
            File(testsPath)
                .inputStream()
                .bufferedReader()
                .readLines()
                .joinToString(separator = "")
        } catch (fnofEx: FileNotFoundException) {
            Logger.logRed("Tests config not found")
            exitProcess(-1)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Logger.logRed("Can't open tests JSON file. Exception above")
            exitProcess(-1)
        }

        Logger.log("Parsing tests config...")
        val tests = try {
            moshiAdapter.fromJson(testsJson)!!
        } catch (ex: Exception) {
            Logger.logRed(ex.message!!)
            Logger.logRed("Can't parse tests JSON. Exception above")
            exitProcess(-1)
        }
        Logger.log("Parsed tests config")

        buildModule(modulePath)

        val reports = mutableListOf<TestResult>()
        tests.forEachIndexed { i: Int, test: Test ->
            val report = try {
                checkTestSanity(test)

                Logger.log("Running test #$i")
                runTest(test)
            } catch (ex: Exception) {
                TestResult(false, ex)
            }

            if (report.isPassed) {
                Logger.logGreen("$i) PASSED\n")
            } else {
                Logger.logRed("$i) FAILED\n${report.err.toString()}\n")
            }

            reports.add(report)
        }

        if (reports.all { it.isPassed }) {
            Logger.log("Everything OK")
        } else {
            Logger.log("There is failed tests")
        }

        return reports
    }

    private fun checkTestSanity(test: Test) {
        Logger.log("\nChecking tests sanity...")

        try {
            test.run {
                if (expectedValue != null) {
                    require(expectedValue.size == 2) {
                        "Expected value have to consist of list of value and it's type"
                    }

                    require(expectedValue[1] in TypeNames.list) {
                        "Expected value type not supported. Only primitives allowed"
                    }
                }

                parametersToTypes?.forEach {
                    require(it.size == 2) {
                        "Parameter have to consist of list of value and it's type"
                    }
                }
            }
        } catch (illegalArgEx: IllegalArgumentException) {

        }

        Logger.log("Tests sanity is OK")
    }

    private fun buildModule(modulePath: String) {
        Logger.log("Creating module build directory...")
        val buildDir = buildDir.apply {
            if (exists()) {
                deleteRecursively()
            }
            mkdirs()
        }
        Logger.log("Created module build directory at ${buildDir.absolutePath}")

        val command = """$kotlincPath $(find $modulePath -name "*.kt") -d ${buildDir.absolutePath} -include-runtime""".trimIndent()

        Logger.log("Building module...")
        val process = runtime.exec(arrayOf("bash", "-c", command))
        val reader = BufferedReader(InputStreamReader(process.errorStream))
        while (process.isAlive) {
            val errorMsg = reader.readLine()
            if (errorMsg != null) {
                println(errorMsg)
            }
        }
        process.waitFor()
        Logger.log("Built module")
    }

    private fun runTest(test: Test): TestResult {
        return try {
            test.run {
                val loader = URLClassLoader(arrayOf(buildDir.toURI().toURL()))
                val clazz = loader.loadClass(test.className)
                val classMethods = clazz.methods

                if (!classMethods.any { it.name == test.functionName }) {
                    val ex = NoSuchMethodException("Class ${clazz.name} doesn't have method with name ${test.functionName}")
                    return TestResult(false, ex)
                }

                val determineArgumentType = { value: String, type: String ->
                    when (type.toLowerCase()) {
                        TypeNames.BOOLEAN -> value.toBoolean()
                        TypeNames.BYTE -> value.toByte()
                        TypeNames.SHORT -> value.toShort()
                        TypeNames.INT -> value.toInt()
                        TypeNames.LONG -> value.toLong()
                        TypeNames.FLOAT -> value.toFloat()
                        TypeNames.DOUBLE -> value.toDouble()
                        TypeNames.STRING -> value
                        TypeNames.CHAR -> value.toCharArray().run {
                            require(size == 1) { "Passed value of type char but it consisted of more than one symbol" }
                            this[0]
                        }

                        else -> throw IllegalArgumentException("Provided value '$value' with unsupported type '$type'")
                    }
                }

                val argumentsList = mutableListOf<Any>()
                parametersToTypes?.forEach { valueToType: List<String> ->
                    val value = valueToType[0]
                    val type = valueToType[1]

                    val arg = determineArgumentType(value, type)
                    argumentsList.add(arg)
                }

                val expectedTypedValue = if (expectedValue != null) {
                    determineArgumentType(expectedValue[0], expectedValue[1])
                } else {
                    null
                }

                val method = clazz.methods
                    .filter { it.name == functionName }
                    .find {
                        if (expectedValue != null) {
                             it.returnType.toString().toLowerCase() == expectedValue[1]
                        } else {
                            it.returnType.name == "void"
                        }
                    } ?: throw ReflectiveOperationException(
                        "There is no methods with name $functionName and needed return type"
                    )

                val argArray = argumentsList.toTypedArray()
                val invokeReturnVal = method.invoke(null, *argArray)

                if (expectedValue != null) {
                    if (invokeReturnVal == null) {
                        throw ReflectiveOperationException(
                            "Function haven't returned anything, but, according to test config, it should"
                        )
                    }

                    if (expectedTypedValue != invokeReturnVal) {
                        throw ReflectiveOperationException(
                            "Function return result isn't equal to expected value"
                        )
                    }
                } else if (invokeReturnVal != null) {
                    throw ReflectiveOperationException(
                        "Function returned something but according to test config it shouldn't"
                    )
                }

                TestResult(true)
            }
        } catch (ex: Exception) {
            TestResult(false, ex)
        }
    }
}





















