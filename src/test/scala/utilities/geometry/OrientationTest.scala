package utilities.geometry

import orientation._
import org.scalatest.wordspec.AnyWordSpec

class OrientationTest extends AnyWordSpec {


  private def assertOrientation(
                                 first: Point,
                                 second: Point,
                                 third: Point
                               )(orientation: Orientation) =
    assert(first.checkOrientation(second, third) == orientation)


  "Points" must {
    "have proper left orientation" in
      assertOrientation(
        first = Point(0, 0),
        second = Point(1, 0),
        third = Point(0, 1)
      )(Left)

    "have proper right orientation" in
      assertOrientation(
        first = Point(0, 0),
        second = Point(1, 0),
        third = Point(0, -1)
      )(Right)

    "have proper no orientation" in
      assertOrientation(
        first = Point(0, 0),
        second = Point(1, 0),
        third = Point(2, 0)
      )(NoTurn)
  }

}
