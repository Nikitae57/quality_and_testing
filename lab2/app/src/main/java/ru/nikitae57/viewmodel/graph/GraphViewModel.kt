package ru.nikitae57.viewmodel.graph

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikitae57.model.Edge
import ru.nikitae57.model.GraphPoint
import ru.nikitae57.view.longToast
import java.lang.StringBuilder

class GraphViewModel(private val app: Application) : AndroidViewModel(app) {
    private val connectedPoints = HashMap<GraphPoint, MutableList<GraphPoint>>()

    val graphLiveData = MutableLiveData<MutableList<Edge>>()
    val elementAddedEvent = MutableLiveData<Int>()

    init { graphLiveData.postValue(mutableListOf()) }
    fun addEdge(edge: Edge) {

        // Already has this edge
        if (edge in graphLiveData.value!!
            || edge.reversed() in graphLiveData.value!!
        ) {
            app.longToast("Уже есть такое ребро")
            return
        }

        edge.run {
            if (connectedPoints[point1] == null) {
                connectedPoints[point1] = mutableListOf(point2)
            } else {
                connectedPoints[point1]!!.add(point2)
            }

            if (connectedPoints[point2] == null) {
                connectedPoints[point2] = mutableListOf(point1)
            } else {
                connectedPoints[point2]!!.add(point1)
            }
        }

        graphLiveData.value!!.add(edge)
        elementAddedEvent.postValue(graphLiveData.value!!.size - 1)
    }

    private fun getAlreadyExistingPointName(x: Int, y: Int): String? {
        var existingName: String? = null
        for (edge in graphLiveData.value!!) {
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
}