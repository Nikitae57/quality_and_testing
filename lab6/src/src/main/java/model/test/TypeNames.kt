package model.test

class TypeNames private constructor(){
    companion object {
        const val STRING = "string"
        const val BYTE = "byte"
        const val SHORT = "short"
        const val INT = "int"
        const val LONG = "long"
        const val FLOAT = "float"
        const val DOUBLE = "double"
        const val BOOLEAN = "boolean"
        const val CHAR = "char"

        val list = listOf(
            STRING,
            BYTE,
            SHORT,
            INT,
            LONG,
            FLOAT,
            DOUBLE,
            BOOLEAN,
            CHAR
        )
    }
}