package utilities.geometry.orientation

trait Orientation extends Enumeration {
  type Orientation = Value

  val
    LEFT,
    RIGHT,
    NO_TURN
  = Value

}