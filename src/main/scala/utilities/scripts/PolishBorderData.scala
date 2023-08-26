package utilities.scripts

import org.geotools.geometry.DirectPosition2D
import org.geotools.geometry.jts.JTS
import org.geotools.referencing.CRS
import utilities.geometry.Point
import utilities.geometry.cartography.Coordinates
import utilities.geometry.convexhull.algorithms.{Graham, Jarvis}
import utilities.serialization.JsonMapper
import utilities.geometry.ordering.OrientationOrdering.Indicator

import java.io.File
object PolishBorderData {
  private val List(polishBorderCoordinatesAsList): List[List[List[Double]]] =
    JsonMapper
      .readValue(new File("data/poland.geojson"), classOf[Map[String, Any]])
      .apply("geometry")
      .asInstanceOf[Map[String, Any]]
      .apply("coordinates")
      .asInstanceOf[List[List[List[Double]]]]

  private val sourceCRS = CRS.decode("EPSG:4326")
  private val targetCRS = CRS.decode("EPSG:2180")
  private val mathTransform = CRS.findMathTransform(sourceCRS, targetCRS)


  private def transformToEPSG2180(coordinates: Coordinates) = {
    val point = new DirectPosition2D(coordinates.latitude, coordinates.longitude)
    val targetPoint = new DirectPosition2D()
    mathTransform.transform(point, targetPoint)
    val List(latitude, longitude) = targetPoint.getCoordinate.toList.map(_.toFloat)
    Coordinates(longitude, latitude)
  }


  private val polishBorderCoordinates = polishBorderCoordinatesAsList.map(_.map(_.toFloat)).map(Coordinates.fromList).map(transformToEPSG2180)

  private val (longitudes, latitudes) = polishBorderCoordinates.flatMap(Coordinates.unapply).unzip

  private val maxLatitude = latitudes.max
  private val maxLongitude = longitudes.max

  private val minLatitude = latitudes.min
  private val minLongitude = longitudes.min

  private def normalize(coordinates: Coordinates): Coordinates = {
    import coordinates._
    Coordinates(
      (longitude - minLongitude) / (maxLongitude - minLongitude),
      (latitude - minLatitude) / (maxLongitude - minLongitude)
    )
  }


  private val polishBorderNormalized = polishBorderCoordinates.map(normalize)

  def toTikz(list: List[Coordinates]): String = list.map(_.toTikz).mkString("\\begin{tikzpicture}[scale=5.5] \\draw ", "--", "--cycle; \\end{tikzpicture}")

  val polishBorderPoints: List[Point] = polishBorderNormalized.map(coordinates => Point(coordinates.latitude, coordinates.longitude))
  def tikzString: String = toTikz(polishBorderNormalized)

  def convexHullNormalized: List[Coordinates] = Graham().calculate(polishBorderPoints).map(point => Coordinates(point.y.toFloat, point.x.toFloat))
}
