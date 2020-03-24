data class Quadrangle(
    val point1: Point,
    val point2: Point,
    val point3: Point,
    val point4: Point
) {
    fun lines(): List<Line> {
        return listOf(
            Line(point1 to point2),
            Line(point2 to point3),
            Line(point3 to point4),
            Line(point4 to point1)
        )
    }

    fun straightAnglesCount(): Int {
        val lines = lines()
        var perpendicularLinesCount = 0
        for (i in 0 until 3) {
            for (j in 3 downTo i + 1) {
                if (lines[i].isPerpendicularTo(lines[j])) {
                    perpendicularLinesCount++
                }
            }
        }

        return perpendicularLinesCount
    }

    fun parallelLinesCount(): Int {
        val lines = lines()
        var parallelLinesCount = 0
        for (i in 0 until 3) {
            for (j in 3 downTo i + 1) {
                if (lines[i].isParallelTo(lines[j])) {
                    parallelLinesCount += 2
                }
            }
        }

        return parallelLinesCount
    }
}