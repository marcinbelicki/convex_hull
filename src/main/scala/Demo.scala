

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color.{Black, Red, White}
import utilities.btc.Prices.{BTC, BTC_POINTS}
import utilities.geometry.{LeftPoints, Point, Shapes}
import utilities.scalafx.WhitePolyline



object a extends App {

  object Orientation extends Enumeration {
    type Orientation = Value

    val
    RIGHT,
    NO_TURN,
    LEFT
    = Value

    private lazy val valuesMap: Map[Int, Orientation] =
      values
        .map(value => (value.id - 1, value))
        .toMap

    private def getFromSign(sign: Int) =
      valuesMap.apply(sign)

    def get(d: Double): Orientation = {
      val sign = d.sign.toInt
      getFromSign(sign)
    }
  }

  println(Orientation.get(-1))
}
object Demo extends JFXApp {
//  val leftPoints = new LeftPoints(
//    list = List(
//      Point(-3, 0),
//      Point(-4, 2),
//      Point(-1, 3),
//      Point(-4, 3.5),
//      Point(-2, 4),
//      Point(-1,4.5),
//      Point(-2, 5)
//    )
//  )

  private val leftPoints = BTC_POINTS.flipPoints

  val convexHull = leftPoints.calculateConvexHull

  val scale: Shapes => Shapes = _.scale(Point(0,1000), Point(1000,0))


  val colors = List(
    White,
    Red
  )
  val shapes =
    scale(Shapes(List(leftPoints, convexHull))).list.zip(colors).map {

      case (shape, color) => shape.toPolygon(color)

    }

  stage = new JFXApp.PrimaryStage {
    title.value = "Convex Hull"
    width = 1000
    height = 1100
    scene = new Scene {
      fill = Black
      content = shapes
    }
  }
}
