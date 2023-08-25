package utilities.geometry.cartography

import utilities.geometry.cartography.Coordinates.tikzFormat

import java.util.Locale

case class Coordinates(
                        longitude: Float,
                        latitude: Float
                      ) {
  def toTikz: String = String.format(Locale.US, tikzFormat, longitude, latitude)
}

object Coordinates {
  private val tikzFormat = "(%.5f,%.5f)"

  def fromList(coordinates: List[Float]): Coordinates = {
    val List(longitude, latitude) = coordinates
    Coordinates(longitude, latitude)
  }
}
