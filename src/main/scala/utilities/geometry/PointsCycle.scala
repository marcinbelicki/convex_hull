package utilities.geometry

abstract class PointsCycle private(points: PointsUtils.Points) extends Iterable[Point] {


  override def iterator: Iterator[Point] = new Iterator[Point] {
    private var currentPointRef: AbstractPointRef = NoPointRef

    override def hasNext: Boolean = currentPointRef.hasNext

    override def next(): Point = {
      val next = currentPointRef.next
      currentPointRef = next
      next.point
    }
  }

  private def createPointRefs(points: PointsUtils.Points): AbstractPointRef =
    points.foldLeft[AbstractPointRef](NoPointRef)(_.addPoint(_))

  createPointRefs(points)


  trait AbstractPointRef {
    var next: PointRef = _
    var prev: PointRef = _

    final def hasNext: Boolean = next ne null

    def addPoint(point: Point): PointRef

    def addPointToList(list: PointsUtils.Points): PointsUtils.Points =
      if (hasNext) next.point :: list
      else list.reverse
  }

  class PointRef(val point: Point) extends AbstractPointRef {

    override def addPoint(point: Point): PointRef = {
      val pointRef = new PointRef(point)
      pointRef.prev = this
      next = pointRef
      pointRef
    }

    def isInTriangle(t2: PointRef, t3: PointRef): Boolean =
      Orientation.calculateThreePointsOrientation(t2.point, t3.point, point) == Orientation.Left
  }

  object NoPointRef extends AbstractPointRef {

    override def addPoint(point: Point): PointRef = {
      val pointRef = new PointRef(point)
      next = pointRef
      pointRef
    }

  }

  def getHull: PointsUtils.Points = {

    if (NoPointRef.hasNext) {
      var q = NoPointRef.next
      while (q.hasNext) {
        if (q.next.isInTriangle(q, q.next.next)) {
          q.next = q.next.next
          q.next.prev = q
          if (q ne NoPointRef.next) q = q.prev
        } else q = q.next
      }
    }
    toList
  }


}

object PointsCycle {


}
