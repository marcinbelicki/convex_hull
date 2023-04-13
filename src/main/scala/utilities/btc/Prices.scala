package utilities.btc

import utilities.geometry.LeftPoints

import java.io.File

object Prices {
  private val BTC_FILE = new File("data/BTC-USD.csv")
  val BTC              = new PricesData(BTC_FILE)

  val BTC_POINTS: LeftPoints  = BTC.toLeftPoints(_.date.getEpochSecond, _.low)
}
