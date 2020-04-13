class NumberSeparator {
    data class CheckResult(val isBeautiful: Boolean, val smallestItem: Long)

    fun checkIfBeautiful(str: String): CheckResult {
        if (str[0] == '-') {
            return CheckResult(false, -1)
        }

        var currentCheckLength = 1
        while (currentCheckLength * 2 <= str.length) {
            val firstNumber = str.substring(0, currentCheckLength).toLong()

            val sb = StringBuilder()
            var number = firstNumber
            while (sb.length < str.length) {
                sb.append(number)
                number++
            }

            if (sb.toString() == str) {
                return CheckResult(true, firstNumber)
            }

            currentCheckLength++
        }

        return CheckResult(false, -1)
    }
}