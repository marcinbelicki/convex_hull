package utilities.geometry

import PointFullList.PointFullList

abstract class PointsSet(
                          val list: PointFullList
                        ) {


  def calculateConvexHull: PointsSet

  override def equals(obj: Any): Boolean = obj match {
    case points: PointsSet => list == points.list
    case _ => false
  }

}
