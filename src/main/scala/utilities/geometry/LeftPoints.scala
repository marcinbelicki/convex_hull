package utilities.geometry

import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.White
import scalafx.scene.shape.Polyline
import utilities.geometry.PointFullList.{MaybePoint, PointFullList}
import utilities.geometry.orientation._
import utilities.scalafx.WhitePolyline

import scala.annotation.tailrec

class LeftPoints(
                  list: PointFullList
                ) extends PointsSet(list) {

  val originalLeftBottom: Point = Point(
    list.map(_.x).min,
    list.map(_.y).min
  )

  val originalRightTop: Point = Point(
    list.map(_.x).max,
    list.map(_.y).max
  )


  def scale(
             xScale: Double,
             yScale: Double,
             leftBottom: Point
           ): LeftPoints = new LeftPoints(list.map(_.scale(xScale, yScale, leftBottom, originalLeftBottom)))

  def flipPoints = new LeftPoints(list.map(_.flip))


  def toPolygon(color: Color = White): Polyline = WhitePolyline(color)(list.flatMap(_.toList): _*)

  override def calculateConvexHull: LeftPoints = {
    @tailrec
    def firstBlock(
                    pointsLeft: PointFullList,
                    q: PointFullList,
                    y: MaybePoint
                  ): (PointFullList, PointFullList, MaybePoint) =
      pointsLeft match {
        case Nil => (pointsLeft, q, y)
        case head :: tail =>
          q match {
            case first :: second :: _ =>
              second.checkOrientation(first, head) match {
                case Right =>
                  (tail, head :: q, tail.headOption)
                case _ => firstBlock(tail, q, y)
              }
          }
      }

    @tailrec
    def untilLeft(
                   pointsLeft: PointFullList,
                   line: Line,
                   x: Point
                 ): (MaybePoint, PointFullList) =
      line.checkOrientation(x) match {
        case Left => (Some(x), pointsLeft)
        case _ => pointsLeft match {
          case head :: tail => untilLeft(tail, line, head)
          case _ => (None, pointsLeft)
        }
      }

    @tailrec
    def fourthBlock(
                     q: PointFullList,
                     x: Point
                   ): (PointFullList, Point) =
      q match {
        case first :: (tail@second :: _) =>
          second.checkOrientation(first, x) match {
            case Right => (x :: q, second)
            case _ => fourthBlock(tail, x)
          }
      }

    @tailrec
    def otherBlocks(pointsLeft: PointFullList, q: PointFullList, y: Point, q0: Point): PointFullList =
      pointsLeft match {
        case Nil => q
        case head :: tail =>
          q match {
            case first :: second :: _ =>
              second.checkOrientation(first, head) match {
                case Right | NoTurn =>
                  val l = y.checkOrientation(first, head) match {
                    case Left => Line(second, first)
                    case _ => Line(first, q0)
                  }
                  untilLeft(tail, l, head) match {
                    case (Some(x), simplePolygon) =>
                      val (newQ, y) = fourthBlock(q, x)
                      otherBlocks(simplePolygon, newQ, y, q0)
                    case _ => q
                  }
                case _ =>
                  val (newQ, y) = fourthBlock(q, head)
                  otherBlocks(tail, newQ, y, q0)
              }
          }
      }

    list.last::list.dropRight(1) match {
      case Nil | _ :: Nil | _ :: _ :: Nil => this
      case first :: second :: tail =>
        firstBlock(tail, second :: first :: Nil, tail.headOption) match {
          case (_, q, None) => new LeftPoints(q)

          case (rest, q, Some(y)) =>
            val listPoints = otherBlocks(rest, q, y, first)

            new LeftPoints((listPoints.last::listPoints.dropRight(1)).reverse)
        }
    }
  }

}
