package ru.nikitae57.model

import java.lang.IllegalStateException
import kotlin.math.pow
import kotlin.math.sqrt

class AStar {
    companion object {
        private fun calculateDistance(point1: GraphPoint, point2: GraphPoint): Double {
            return sqrt(
                (point1.x - point2.x).toDouble().pow(2)
                + (point1.y - point2.y).toDouble().pow(2)
            )
        }

        fun findPath(pointFrom: GraphPoint, pointTo: GraphPoint): List<GraphPoint> {
            val path = mutableListOf<GraphPoint>()
            val pointToIsVisited = hashMapOf<GraphPoint, Boolean>()
            val heuristic = { point: GraphPoint ->
                calculateDistance(pointFrom, point) + calculateDistance(point, pointTo)
            }

            var currentPoint = pointFrom
            while(currentPoint != pointTo) {
                pointToIsVisited[currentPoint] = true
                path.add(currentPoint)

                currentPoint = currentPoint.neighbours.run {
                    // Best point calculated using heuristic
                    val nextPoint = filter {
                        it !in pointToIsVisited
                    }.minBy(heuristic) ?: throw IllegalStateException("Can't find a way. There's no not visited points")
                    forEach { pointToIsVisited[it] = true }

                    nextPoint
                }
            }
            path.add(pointTo)

            return path
        }
    }
}