package ru.nikitae57

import org.junit.Test

import org.junit.Assert.*
import ru.nikitae57.model.AStar
import ru.nikitae57.model.GraphPoint
import java.lang.Exception
import java.lang.IllegalStateException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AStarTests {
    @Test
    fun equalPointsTest() {
        val p1 = GraphPoint(0, 0, "A")
        val p2 = GraphPoint(0, 0, "A")

        val path = AStar.findPath(p1, p2)

        assertEquals(path.size, 1)
        assertEquals(p1, path[0])
        assertEquals(p2, path[0])
    }

    @Test
    fun thereIsNoPathTest() {
        val p1 = GraphPoint(0, 0, "A")
        val p2 = GraphPoint(0, 1, "B")
        var isThereNoPath = false

        try {
            AStar.findPath(p1, p2)
        } catch (ex: IllegalStateException) {
            isThereNoPath = true
        }

        assert(isThereNoPath)
    }

    @Test
    fun thereIsAPathTest() {
        val p1 = GraphPoint(0, 0, "A", mutableListOf(GraphPoint(0, 1, "B")))
        val p2 = p1.neighbours[0]

        val path = AStar.findPath(p1, p2)

        assertEquals(path.size, 2)
        assertEquals(path.first(), p1)
        assertEquals(path.last(), p2)
    }
}
