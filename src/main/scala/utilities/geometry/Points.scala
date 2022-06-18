package utilities.geometry

import PointFullList.PointFullList

abstract class Points(
                       val list: PointFullList
                     ) {


  def calculateConvexHull: Points

  override def equals(obj: Any): Boolean = obj match {
    case points: Points => list == points.list
    case _ => false
  }

}
