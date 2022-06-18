package utilities.btc

import java.io.File
import CustomCsvParser.parse
import scalafx.scene.shape.Polyline
import utilities.btc.CoordinateFunction.CoordinateFunction
import utilities.geometry.PointFullList.PointFullList
import utilities.geometry.{LeftPoints, Point}
import utilities.scalafx.WhitePolyline

class PricesData(file: File) {

  val prices: List[PriceData] =
    parse(file)
      .map(_.getOrElse(throw new Exception))
      .toList

  private def toList(
                      xFunction: CoordinateFunction,
                      yFunction: CoordinateFunction
                    ): PointFullList = prices.map(_.toPoint(xFunction,yFunction))

  def toLeftPoints(
                    xFunction: CoordinateFunction,
                    yFunction: CoordinateFunction
                  ) = new LeftPoints(toList(xFunction, yFunction))

}
