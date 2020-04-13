package run

import controller.test.TestDriver
import view.log.Logger

fun main(args: Array<String>) {
    if (args[0].toLowerCase().contains("help")) {
        Logger.log("""
            Usage:
            lab6 <kotlinc_path> <test_config_path> <module_to_test_path>
        """.trimIndent())
    }

    val kotlincPath = args[0]
    val testConfigPath = args[1]
    val modulePath = args[2]
    val driver = TestDriver(kotlincPath)

    driver.runTests(modulePath, testConfigPath)
}