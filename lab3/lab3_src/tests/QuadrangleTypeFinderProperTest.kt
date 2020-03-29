import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class QuadrangleTypeFinderProperTest {
    @Test
    fun `Both pairs of are not parallel`() {
        val generalQuadrangle = Quadrangle(
            Point(0, 0), Point(5, 2), Point(9, 3), Point(1, -2)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(generalQuadrangle)

        assertEquals(QuadrangleTypes.GENERAL_QUADRANGLE, determinedType)
    }

    @Test
    fun `Second pair is parallel, first pair lengths are equal`() {
        val isoscelesTrapezoid = Quadrangle(
            Point(0, 0), Point(1, 1), Point(2, 1), Point(3, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(isoscelesTrapezoid)

        assertEquals(QuadrangleTypes.ISOSCELES_TRAPEZOID, determinedType)
    }

    @Test
    fun `First pair is parallel, second pair lengths are equal`() {
        val isoscelesTrapezoid = Quadrangle(
            Point(0, 0), Point(0, 3), Point(1, 2), Point(1, 1)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(isoscelesTrapezoid)

        assertEquals(QuadrangleTypes.ISOSCELES_TRAPEZOID, determinedType)
    }

    @Test
    fun `Second pair is parallel, straight angles count = 2`() {
        val rectangularTrapezoid = Quadrangle(
            Point(0, 0), Point(0, 1), Point(1, 1), Point(2, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rectangularTrapezoid)

        assertEquals(QuadrangleTypes.RECTANGULAR_TRAPEZOID, determinedType)
    }

    @Test
    fun `First pair is parallel, straight angles count = 2`() {
        val rectangularTrapezoid = Quadrangle(
            Point(0, 0), Point(0, 1), Point(1, 2), Point(1, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rectangularTrapezoid)

        assertEquals(QuadrangleTypes.RECTANGULAR_TRAPEZOID, determinedType)
    }

    @Test
    fun `Second pair is parallel, straight angles count != 2`() {
        val generalTrapezoid = Quadrangle(
            Point(0, 0), Point(1, 2), Point(2, 2), Point(6, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(generalTrapezoid)

        assertEquals(QuadrangleTypes.GENERAL_TRAPEZOID, determinedType)
    }

    @Test
    fun `First pair is parallel, straight angles count != 2`() {
        val generalTrapezoid = Quadrangle(
            Point(0, 0), Point(0, 6), Point(2, 2), Point(2, 1)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(generalTrapezoid)

        assertEquals(QuadrangleTypes.GENERAL_TRAPEZOID, determinedType)
    }

    @Test
    fun `Straight angles count = 4, both pairs lengths are equal`() {
        val square = Quadrangle(
            Point(0, 0), Point(0, 1), Point(1, 1), Point(1, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(square)

        assertEquals(QuadrangleTypes.SQUARE, determinedType)
    }

    @Test
    fun `Straight angles count != 4, both pairs lengths are equal`() {
        val rhombus = Quadrangle(
            Point(1, 1), Point(3, 5), Point(7, 7), Point(5, 3)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rhombus)

        assertEquals(QuadrangleTypes.RHOMBUS, determinedType)
    }

    @Test
    fun `Straight angles count = 4, first pairs lengths are equal`() {
        val rectangle = Quadrangle(
            Point(0, 0), Point(0, 1), Point(2, 1), Point(2, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rectangle)

        assertEquals(QuadrangleTypes.RECTANGLE, determinedType)
    }

    @Test
    fun `Straight angles count = 4, second pairs lengths are equal`() {
        val rectangle = Quadrangle(
            Point(0, 0), Point(0, 2), Point(1, 2), Point(1, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(rectangle)

        assertEquals(QuadrangleTypes.RECTANGLE, determinedType)
    }

    @Test
    fun `Straight angles count != 4, both pairs are parallel`() {
        val parallelogram = Quadrangle(
            Point(0, 0), Point(1, 1), Point(3, 1), Point(2, 0)
        )
        val determinedType = QuadrangleTypeFinder.determineQuadrangleType(parallelogram)

        assertEquals(QuadrangleTypes.PARALLELOGRAM, determinedType)
    }
}