package utilities.geometry.convexhull.algorithms

import utilities.geometry.PointsUtils.Points

trait ConvexHullAlgorithm extends Product {
  final def calculate(points: Points): Points = {
    require(points.nonEmpty, "You can not calculate convex hull of an empty points set.")
    nonEmptyCalculate(points)
  }

  protected def nonEmptyCalculate(points: Points): Points
}
