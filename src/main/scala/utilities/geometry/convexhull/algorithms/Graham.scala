package utilities.geometry.convexhull.algorithms
import utilities.geometry.PointsUtils.Points
import utilities.geometry.ordering.OrientationOrdering
import utilities.geometry.{Point, PointsUtils}

import scala.collection.mutable


object Graham extends ConvexHullAlgorithm {
  private def isInsideTriangle(p: Point)(t1: Point, t2: Point, t3: Point): Boolean = ???

  override def calculate(points: Points)(implicit ordering: OrientationOrdering): Points = {
    val centroid = PointsUtils.calculateCentroid(points)
    def helper(points: Points, currentHull: Points): Points = {
      def handlePoints(q: Point, nextQ: Point, nextNextQ: Point, prevQ: Point, remainingPoints: Points, cu): Points =
        if (isInsideTriangle(nextQ)(centroid, q, nextNextQ)) helper()
      (points, currentHull) match {
        case (q :: nextQ :: nextNextQ :: remainingPoints, Nil) =>
          if (isInsideTriangle(nextQ)(centroid, q, nextNextQ))
        case _ => currentHull
      }
    }

    val pointsSorted = points.map(_ - centroid).sorted
  }
}
