package model.test

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Test(
    val className: String,
    val functionName: String,
    val parametersToTypes: List<List<String>>? = null, // parameter value to type
    val expectedValue: List<String>? = null
)

@JsonClass(generateAdapter = true)
data class TestsList(
    val testsList: List<Test>
)