class QuadrangleTypeFinder {
    companion object {

        fun determineQuadrangleType(quad: Quadrangle): QuadrangleTypes? {
            val lines = quad.lines()
            val linePair1 = lines[0] to lines[2]
            val linePair2 = lines[1] to lines[3]

            return if (!linePair1.first.isParallelTo(linePair1.second)
                && !linePair2.first.isParallelTo(linePair2.second)
            ) {
                QuadrangleTypes.GENERAL_QUADRANGLE
            } else if (
                linePair1.first.isParallelTo(linePair1.second)
                && !linePair2.first.isParallelTo(linePair2.second)
                || linePair2.first.isParallelTo(linePair2.second)
                && !linePair1.first.isParallelTo(linePair1.second)
            ) {
                // Трапеция
                if (linePair1.first.length() == linePair1.second.length()
                    || linePair2.first.length() == linePair2.second.length()
                ) {
                    QuadrangleTypes.ISOSCELES_TRAPEZOID
                } else if (quad.straightAnglesCount() == 2) {
                    QuadrangleTypes.RECTANGULAR_TRAPEZOID
                } else {
                    QuadrangleTypes.GENERAL_TRAPEZOID
                }
            } else if (lines.all { it.length() == lines[0].length() }) {
                if (quad.straightAnglesCount() == 4) {
                    QuadrangleTypes.SQUARE
                } else {
                    QuadrangleTypes.RHOMBUS
                }
            } else if (quad.straightAnglesCount() == 4) {
                QuadrangleTypes.RECTANGLE
            } else if (quad.parallelLinesCount() == 4) {
                QuadrangleTypes.PARALLELOGRAM
            } else {
                null
            }
        }
    }
}