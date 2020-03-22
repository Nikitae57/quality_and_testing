package ru.nikitae57.model

data class GraphPoint(
    val x: Int,
    val y: Int,
    val name: String,
    val neighbours: MutableList<GraphPoint> = mutableListOf()
) : Comparable<GraphPoint> {
    override fun compareTo(other: GraphPoint): Int {
        return -other.name.compareTo(name)
    }

    override fun toString(): String {
        val neighboursStr = neighbours.joinToString { it.name }
        return "$name($x; $y), [$neighboursStr]"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is GraphPoint) {
            name == other.name
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}