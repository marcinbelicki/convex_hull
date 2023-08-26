package utilities.scripts

import utilities.scripts.PolishBorderData.{convexHullNormalized, tikzString, toTikz}


object PolandMapToTikz extends App {
  reflect.io.File("data/poland.tikz").writeAll(tikzString)
  reflect.io.File("data/poland_convex_hull.tikz").writeAll(toTikz(convexHullNormalized))
}
