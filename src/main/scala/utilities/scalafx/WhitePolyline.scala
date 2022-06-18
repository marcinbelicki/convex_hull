package utilities.scalafx

import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.White
import scalafx.scene.shape.Polyline

object WhitePolyline {

  def apply(color: Color = White)(points: Double*): Polyline = {
    val polyline = Polyline(points: _*)
    polyline.stroke = color
    polyline
  }

}
