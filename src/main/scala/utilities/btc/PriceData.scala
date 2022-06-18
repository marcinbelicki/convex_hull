package utilities.btc

import CoordinateFunction.CoordinateFunction
import utilities.geometry.Point

import java.time.Instant


case class PriceData(
                      date: Instant,
                      open: Double,
                      high: Double,
                      low: Double,
                      close: Double,
                      adjClose: Double,
                      volume: BigInt
                    ) {
  def toPoint(
               xFunction: CoordinateFunction,
               yFunction: CoordinateFunction
             ): Point =
    Point(
      x = xFunction(this),
      y = yFunction(this)
    )
}
