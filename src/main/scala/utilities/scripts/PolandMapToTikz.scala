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

object PolandMapToTikz extends App {

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

  private def toTikz(list: List[Coordinates]) = list.map(_.toTikz).mkString("\\begin{tikzpicture}[scale=5.5] \\draw ", "--", "--cycle; \\end{tikzpicture}")

  private val tikzString = toTikz(polishBorderNormalized)


  private val convexHullNormalized = Jarvis().calculate(polishBorderNormalized.map(coordinates => Point(coordinates.latitude, coordinates.longitude))).map(point => Coordinates(point.y.toFloat, point.x.toFloat))
  reflect.io.File("data/poland.tikz").writeAll(tikzString)
  reflect.io.File("data/poland_convex_hull.tikz").writeAll(toTikz(convexHullNormalized))
}
