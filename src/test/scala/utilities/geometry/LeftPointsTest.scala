package utilities.geometry

import org.scalatest.wordspec.AnyWordSpec

class LeftPointsTest extends AnyWordSpec {

  "LeftPoints" must {
    "form a proper convex hull" in {
      val leftPoints = new LeftPoints(
        list = List(
          Point(0, 0),
          Point(-4, 2),
          Point(-2, 3),
          Point(-4, 3.5),
          Point(-2, 4),
          Point(-2.5, 5),
          Point(0, 5)
        )
      )

      val actualHull = leftPoints.calculateConvexHull
      val expectedHull = new LeftPoints(
        list = List(
          Point(0, 0),
          Point(-4, 2),
          Point(-4, 3.5),
          Point(-2.5, 5),
          Point(0, 5)
        )
      )
      assert(actualHull == expectedHull)
    }
  }

}
