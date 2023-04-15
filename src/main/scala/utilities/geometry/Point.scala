package utilities.geometry

import utilities.geometry.orientation.{Left, NoTurn, Orientation, Right}

case class Point(x: Double, y: Double) {

  def -(that: Point): Point = Point(x - that.x, y - that.y)
  def +(that: Point): Point = Point(x + that.x, y + that.y)
  def /[T](number: T)(implicit numeric: Numeric[T]): Point = {
    val double = numeric.toDouble(number)
    Point(x / double, y / double)
  }

  def checkOrientation(two: Point, thr: Point): Orientation = (
        x      * two.y
      + y      * thr.x
      + two.x  * thr.y
      - two.y  * thr.x
      - x      * thr.y
      - y      * two.x
    ).sign match {
    case 1 => Left
    case 0 => NoTurn
    case _ => Right
  }




  def scale(xScale: Double, yScale: Double, leftBottom: Point, originalLeftBottom: Point): Point = {

    val difference = this - originalLeftBottom

    leftBottom + Point(difference.x * xScale, difference.y * yScale)
  }

  def toList: List[Double] = List(x, y)

  def flip: Point = Point(y, x)

}
