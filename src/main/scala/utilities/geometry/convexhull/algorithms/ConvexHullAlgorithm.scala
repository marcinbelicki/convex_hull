package utilities.geometry.convexhull.algorithms

import utilities.geometry.Point
import utilities.geometry.PointsUtils.Points
import utilities.geometry.ordering.OrientationOrdering

trait ConvexHullAlgorithm {
  def calculate(points: Points)(implicit ordering: OrientationOrdering): Points

}
