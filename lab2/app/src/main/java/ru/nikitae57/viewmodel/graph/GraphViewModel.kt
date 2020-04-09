package ru.nikitae57.viewmodel.graph

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikitae57.model.AStar
import ru.nikitae57.model.Edge
import ru.nikitae57.model.GraphPoint
import ru.nikitae57.view.longToast
import java.lang.IllegalStateException
import java.lang.StringBuilder
import kotlin.math.pow
import kotlin.math.sqrt

class GraphViewModel(private val app: Application) : AndroidViewModel(app) {
    val elementAddedEvent = MutableLiveData<Int>()
    val shortestWayLiveData = MutableLiveData<List<GraphPoint>?>()

    val edges = mutableListOf<Edge>()
    private val graph = sortedSetOf<GraphPoint>()

    fun getPointsCount() = graph.size
    fun getPointsNames() = graph.map { it.name }
    fun getPoint(index: Int) = graph.elementAt(index)

    fun addEdge(x1: Int, y1: Int, x2: Int, y2: Int) {
        val point1Name = getPointName(x1, y1)
        val point2Name = getPointName(x2, y2)
        var point1 = GraphPoint(x1, y1, point1Name)
        var point2 = GraphPoint(x2, y2, point2Name)

        point1 = graph.find { it == point1 } ?: point1
        point2 = graph.find { it == point2 } ?: point2

        point1.neighbours.add(point2)
        point2.neighbours.add(point1)
        graph.addAll(listOf(point1, point2))

        val distance = sqrt(
            (x1 - x2).toDouble().pow(2)
                + (y1 - y2).toDouble().pow(2)
        )

        val edge = Edge(point1, point2, distance)

        // Already has this edge
        if (edge in edges || edge.reversed() in edges) {
            app.longToast("Уже есть такое ребро")
            return
        }
        edges.add(edge)

        graph.add(edge.point1)
        graph.add(edge.point2)
        elementAddedEvent.postValue(edges.size - 1)
    }

    private fun getAlreadyExistingPointName(x: Int, y: Int): String? {
        var existingName: String? = null
        for (edge in edges) {
            edge.run {
                if (point1.x == x && point1.y == y) {
                    existingName = point1.name
                } else if (point2.x == x && point2.y == y) {
                    existingName = point2.name
                }
            }

            if (existingName != null) { break }
        }

        return existingName
    }

    private val digitToLetter = mapOf(
        '0' to 'A',
        '1' to 'B',
        '2' to 'C',
        '3' to 'D',
        '4' to 'E',
        '5' to 'F',
        '6' to 'G',
        '7' to 'H',
        '8' to 'I',
        '9' to 'J'
    )

    private var nameCount = 0
    fun getPointName(x: Int, y: Int): String {
        // Check if point already exist
        val alreadyStoredName = getAlreadyExistingPointName(x, y)
        if (alreadyStoredName != null) {
            return alreadyStoredName
        }

        // Generate new name
        val stringBuilder = StringBuilder()
        for (char in nameCount.toString()) {
            stringBuilder.append(digitToLetter[char])
        }
        nameCount++

        return stringBuilder.toString()
    }

    fun findShortestWay(from: GraphPoint, to: GraphPoint) {
        val path = try {
            AStar.findPath(from, to)
        } catch (ex: IllegalStateException) {
            null
        }

        shortestWayLiveData.postValue(path)
    }
}