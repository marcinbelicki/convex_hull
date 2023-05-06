package utilities.geometry.convexhull.algorithms
import utilities.geometry.PointsUtils.Points
import utilities.geometry.ordering.OrientationOrdering
import utilities.geometry.PointsUtils
import utilities.geometry.pointcycles.GrahamPointsCycle


case class Graham()(implicit ordering: OrientationOrdering) extends ConvexHullAlgorithm {

  override protected def nonEmptyCalculate(points: Points): Points = {
    val centroid = PointsUtils.calculateCentroid(points)
    val pointsSorted = points.map(_ - centroid).sorted
    val pointsCycle = new GrahamPointsCycle(pointsSorted)

    pointsCycle.getHull.map(_ + centroid)
  }
}
