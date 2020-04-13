package view.log

import model.log.ConsoleColors

class Logger {
    companion object {
        fun log(msg: String) {
            println("${ConsoleColors.RESET}$msg")
        }

        fun logGreen(msg: String) {
            println("${ConsoleColors.GREEN}$msg${ConsoleColors.RESET}")
        }

        fun logRed(msg: String) {
            println("${ConsoleColors.RED}$msg${ConsoleColors.RESET}")
        }
    }
}