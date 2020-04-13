package model.tmp_test.src

import java.lang.Exception

fun doStuff(): String {
    return "asdas"
}

fun doStuff2() {
}

fun doStuff3() {
    throw Exception("Exception in tested function")
}

fun doStuff4(): Int {
    throw Exception("Exception in tested function")
}

fun doStuff5(): Int {
    return 2
}

fun doStuff6(): Int {
    return 3
}

fun doStuff7(i: Int): Int {
    return i
}