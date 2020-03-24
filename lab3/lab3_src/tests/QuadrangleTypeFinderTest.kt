import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class QuadrangleTypeFinderTest {
    @Test
    fun isPerpendicularTest() {
        val line1 = Line(Point(1, 6), Point(4, 3))
        val line2 = Line(Point(4, 3), Point(8, 7))
        assert(line1.isPerpendicularTo(line2))
    }

    @Test
    fun straightAnglesCountTest() {
        val quad = Quadrangle(
            Point(5, 1), Point(5, 3), Point(7, 3), Point(8, 1)
        )
        assertEquals(2, quad.straightAnglesCount())
    }

    @Test
    fun squareTest() {
        val square = Quadrangle(
            Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(square)

        assertEquals(QuadrangleTypes.SQUARE, determinedType)
    }

    @Test
    fun rectangleTest() {
        val rectangle = Quadrangle(
            Point(0, 0), Point(0, 1), Point(2, 1), Point(2, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rectangle)

        assertEquals(QuadrangleTypes.RECTANGLE, determinedType)
    }

    @Test
    fun parallelogramTest() {
        val parallelogram = Quadrangle(
            Point(0, 0), Point(1, 1), Point(3, 1), Point(2, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(parallelogram)

        assertEquals(QuadrangleTypes.PARALLELOGRAM, determinedType)
    }

    @Test
    fun rhombusTest() {
        val rhombus = Quadrangle(
            Point(1, 1), Point(3, 5), Point(7, 7), Point(5, 3)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rhombus)

        assertEquals(QuadrangleTypes.RHOMBUS, determinedType)
    }
    
    @Test
    fun isoscelesTrapezoidTest() {
        val isoscelesTrapezoid = Quadrangle(
            Point(0, 0), Point(1, 1), Point(2, 1), Point(3, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(isoscelesTrapezoid)

        assertEquals(QuadrangleTypes.ISOSCELES_TRAPEZOID, determinedType)
    }

    @Test
    fun rectangularTrapezoidTest() {
        val rectangularTrapezoid = Quadrangle(
            Point(0, 0), Point(0, 1), Point(1, 1), Point(2, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rectangularTrapezoid)

        assertEquals(QuadrangleTypes.RECTANGULAR_TRAPEZOID, determinedType)
    }

    @Test
    fun generalTrapezoidTest() {
        val generalTrapezoid = Quadrangle(
            Point(0, 0), Point(1, 2), Point(2, 2), Point(6, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(generalTrapezoid)

        assertEquals(QuadrangleTypes.GENERAL_TRAPEZOID, determinedType)
    }

    @Test
    fun generalQuadrangleTest() {
        val generalQuadrangle = Quadrangle(
            Point(0, 0), Point(5, 2), Point(9, 3), Point(1, -2)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(generalQuadrangle)

        assertEquals(QuadrangleTypes.GENERAL_QUADRANGLE, determinedType)
    }
}