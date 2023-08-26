package utilities.geometry.pointcycles

import utilities.geometry.PointsUtils.Points
import utilities.geometry.{Orientation, Point}

trait PointsCycle extends Iterable[Point] {

  this: ListOfPoints =>

  protected lazy val first: PointRef = NoPointRef.next

  private var firstPoint: PointRef = _

  private def createPointRefs(points: Points): AbstractPointRef =
    points.foldLeft[AbstractPointRef](NoPointRef)(_.addPoint(_)).addLastPointAndReturn()

  createPointRefs(this.points)

  override def iterator: Iterator[Point] = new Iterator[Point] {
    private var currentPointRef: AbstractPointRef = NoPointRef

    override def hasNext: Boolean = currentPointRef.isNotLast

    override def next(): Point = {
      val next = currentPointRef.next
      currentPointRef = next
      next.point
    }
  }


  trait AbstractPointRef {
    var next: PointRef = _
    var prev: PointRef = _

    def isNotFirst: Boolean

    def isNotLast: Boolean

    def addPoint(point: Point): PointRef

    protected def addLastPoint(): Unit

    final def addLastPointAndReturn(): AbstractPointRef = {
      addLastPoint()
      this
    }
  }

  class PointRef(val point: Point) extends AbstractPointRef {

    final def isNotFirst: Boolean = this ne first

    final def isNotLast: Boolean = next ne first

    override def addPoint(point: Point): PointRef = {
      val pointRef = new PointRef(point)
      pointRef.prev = this

      if (pointRef.point.y < NoPointRef.next.point.y) NoPointRef.next = pointRef
      else if (pointRef.point.y == NoPointRef.next.point.y && pointRef.point.x < NoPointRef.next.point.x) NoPointRef.next = pointRef

      next = pointRef
      pointRef
    }

    override protected def addLastPoint(): Unit = {
      next = firstPoint
      firstPoint.prev = this
    }
  }

  object NoPointRef extends AbstractPointRef {

    override def addPoint(point: Point): PointRef = {
      val pointRef = new PointRef(point)
      next = pointRef
      firstPoint = pointRef
      pointRef
    }

    override def isNotFirst: Boolean = first ne null

    override def isNotLast: Boolean = first ne null

    override protected def addLastPoint(): Unit = ()
  }

}
