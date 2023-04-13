package utilities.geometry.ordering

import utilities.geometry.{Point, PointsUtils}

trait OrientationOrdering extends Ordering[Point] {
  final override def compare(x: Point, y: Point): Int = {
    import Ordering.Double.TotalOrdering.compare
    compare(phase(x), phase(y)) match {
      case 0 => compare(distance(x), distance(y))
      case phaseCompared => phaseCompared
    }
  }

  protected def distance(p: Point): Double
  protected def phase(p: Point): Double
}

object OrientationOrdering {
  implicit object Exact extends OrientationOrdering {
    override protected def distance(p: Point): Double = PointsUtils.distanceFromCenter(p)
    override protected def phase(p: Point): Double = PointsUtils.phase(p)
  }

  implicit object Indicator extends OrientationOrdering {
    override protected def distance(p: Point): Double = PointsUtils.distanceFromCenterSquared(p)
    override protected def phase(p: Point): Double = PointsUtils.alpha(p)
  }
}


