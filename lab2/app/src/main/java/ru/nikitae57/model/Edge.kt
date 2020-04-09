package ru.nikitae57.model

import kotlin.math.pow
import kotlin.math.sqrt


data class Edge(
    val point1: GraphPoint,
    val point2: GraphPoint
) {
    val distance = sqrt(
        (point1.x - point2.x).toDouble().pow(2)
                + (point1.y - point2.y).toDouble().pow(2)
    )

    fun reversed(): Edge {
        return Edge(point2, point1)
    }
}