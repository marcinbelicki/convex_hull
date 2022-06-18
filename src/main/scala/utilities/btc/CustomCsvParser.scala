package utilities.btc

import kantan.csv.CsvConfiguration.{Header, QuotePolicy}
import kantan.csv.DecodeError.TypeError
import kantan.csv.ops.toCsvInputOps
import kantan.csv.{CellDecoder, CsvConfiguration, CsvReader, ReadResult, RowDecoder}

import java.io.File
import java.time.{Instant, LocalDate, ZoneId}
import scala.util.Try

object CustomCsvParser {
  private def parseInstant(date: String): Instant =
    LocalDate
      .parse(date)
      .atStartOfDay(ZoneId.systemDefault())
      .toInstant

  private val rfc: CsvConfiguration = CsvConfiguration(',', '"', QuotePolicy.WhenNeeded, Header.Implicit)

  private implicit val instantDecoder: CellDecoder[Instant] =
    e => Try(parseInstant(e)).toEither.left.map(exception => TypeError(exception.getMessage))

  private implicit val priceDataDecoder: RowDecoder[PriceData] = RowDecoder.ordered(PriceData.apply _)

  def parse(data: File): CsvReader[ReadResult[PriceData]] = data.asCsvReader[PriceData](rfc)

}
