package ru.nikitae57.model


data class Edge(
    val point1: GraphPoint,
    val point2: GraphPoint,
    val distance: Double
) {
    fun reversed(): Edge {
        return Edge(point2, point1, distance)
    }
}