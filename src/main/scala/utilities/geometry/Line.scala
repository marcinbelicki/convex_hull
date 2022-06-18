package utilities.geometry

import utilities.geometry.orientation.Orientation

case class Line(
                 first: Point,
                 last: Point
               ) {

  def checkOrientation(point: Point): Orientation = first.checkOrientation(last, point)

}
