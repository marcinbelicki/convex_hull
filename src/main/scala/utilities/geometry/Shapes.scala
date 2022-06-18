package utilities.geometry

case class Shapes(
                   list: List[LeftPoints]
                 ) {

  val originalLeftBottom = Point(
    list.map(_.originalLeftBottom.x).min,
    list.map(_.originalLeftBottom.y).min
  )

  val originalRightTop = Point(
    list.map(_.originalRightTop.x).max,
    list.map(_.originalRightTop.y).max
  )

  def scale(
             leftBottom: Point,
               rightTop: Point,
           ) = {

    val difference = rightTop - leftBottom

    val originalDifference = originalRightTop - originalLeftBottom

    val xScale = difference.x / originalDifference.x


    val yScale = difference.y / originalDifference.y

    Shapes(list.map(_.scale(xScale,yScale,leftBottom)))
  }
}
