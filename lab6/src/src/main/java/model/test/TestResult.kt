package model.test

data class TestResult(
    val isPassed: Boolean,
    val err: Throwable? = null
)