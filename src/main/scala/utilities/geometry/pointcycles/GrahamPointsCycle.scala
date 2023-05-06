package utilities.geometry.pointcycles

import utilities.geometry.Orientation
import utilities.geometry.PointsUtils.Points

class GrahamPointsCycle(val points: Points) extends ListOfPoints with PointsCycle {

  def isInTriangle(pointRef: PointRef, t2: PointRef, t3: PointRef): Boolean =
    Orientation.calculateThreePointsOrientation(t2.point, t3.point, pointRef.point) == Orientation.Left

  def getHull: Points = {

    if (NoPointRef.isNotLast) {
      var q = NoPointRef.next
      while (q.isNotLast) {
        if (isInTriangle(q.next, q, q.next.next)) {
          q.next = q.next.next
          q.next.prev = q
          if (q.isNotFirst) q = q.prev
        } else q = q.next
      }
    }
    toList
  }

}
