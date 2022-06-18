name := "convex_hull"

version := "0.1"

scalaVersion := "2.13.8"

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

libraryDependencies += "org.scalatest" %% "scalatest-wordspec" % "3.3.0-SNAP3" % Test
libraryDependencies += "com.nrinaudo" %% "kantan.csv-generic" % "0.6.2"
libraryDependencies += "com.nrinaudo" %% "kantan.csv" % "0.6.2"
libraryDependencies += "org.scalafx" %% "scalafx" % "15.0.1-R21"


lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")

libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "11" classifier osName
)