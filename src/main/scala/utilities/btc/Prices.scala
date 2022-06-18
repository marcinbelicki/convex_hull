package utilities.btc

import java.io.File

object Prices {
  val BTC = new PricesData(new File("data/BTC-USD.csv"))
}
