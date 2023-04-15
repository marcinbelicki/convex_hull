package utilities.geometry

object Orientation extends Enumeration {
  type Orientation = Value
  val Left,
      Right,
      NoTurn
  = Value

  private val signumMap: Map[Double, Orientation] = Map(
   1d -> Left,
   0d -> NoTurn,
   -1d -> Right
  )

  private def calculateOrientationFromDouble(double: Double): Orientation = signumMap(double.sign)

  def calculateThreePointsOrientation(a: Point, b: Point, c: Point): Orientation =
   calculateOrientationFromDouble(
         a.x * b.y
       + a.y * c.x
       + b.x * c.y
       - b.y * c.x
       - a.x * c.y
       - a.y * b.x
    )

  def calculateLineAndPointOrientation(line: Line, point: Point): Orientation =
    calculateThreePointsOrientation(line.a, line.b, point)

}
