import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Line(
    val point1: Point,
    val point2: Point
) {
    constructor(points: Pair<Point, Point>) : this(points.first, points.second)

    val A = point1.y - point2.y
    val B = point2.x - point1.x
    val C = point1.x * point2.y + point2.x * point1.y

    fun isPerpendicularTo(line: Line): Boolean {
        return A * line.A + B * line.B == 0
    }

    fun slope(): Double {
        return (point2.y - point1.y).toDouble() / (point2.x - point1.x)
    }

    fun isParallelTo(line: Line): Boolean {
        val slope1 = slope()
        val slope2 = line.slope()
        val inf = Double.POSITIVE_INFINITY

        return abs(slope1 - slope2) < 0.00000001
                || (abs(slope1) == inf && abs(slope2) == inf)
    }

    fun length(): Double {
        return sqrt(
            (point1.x - point2.x).toDouble().pow(2)
                + (point1.y - point2.y).toDouble().pow(2)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other is Line) {
            return (other.point1 == point1 && other.point2 == point2)
                    || (other.point1 == point2 && other.point2 == point1)
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = point1.hashCode()
        result = 31 * result + point2.hashCode()
        return result
    }
}