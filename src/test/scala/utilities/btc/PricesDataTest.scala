package utilities.btc

import org.scalatest.wordspec.AnyWordSpec
import utilities.btc.Prices.BTC

import java.io.File

class PricesDataTest extends AnyWordSpec {
  private def isSorted[T](s: Seq[T])(implicit ord: Ordering[T]): Boolean = s match {
    case Seq() => true
    case Seq(_) => true
    case _ => s.sliding(2).forall { case Seq(x, y) => ord.lteq(x, y) }
  }

  private val priceData = Prices.BTC
  "PricesData" must {
    "be sorted by date" in
      assert(
        isSorted(
          priceData
            .prices
            .map(_.date)
        )
      )
  }
}
