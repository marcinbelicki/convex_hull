package utilities.geometry

import utilities.geometry.orientation.Orientation

case class Line(
                 a: Point,
                 b: Point
               ) {

  def checkOrientation(point: Point): Orientation = a.checkOrientation(b, point)

}
