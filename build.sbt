name := "convex_hull"

version := "0.1"

scalaVersion := "2.13.8"

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

resolvers += "geotools" at "https://repo.osgeo.org/repository/geotools-releases"
def javaFxModule(name: String) = "org.openjfx" % s"javafx-$name" % "11" classifier osName
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web").map(javaFxModule)

libraryDependencies ++= Seq(
  "org.scalatest"                %% "scalatest-wordspec"   % "3.3.0-SNAP3" % Test,
  "com.nrinaudo"                 %% "kantan.csv-generic"   % "0.6.2",
  "com.nrinaudo"                 %% "kantan.csv"           % "0.6.2",
  "org.scalafx"                  %% "scalafx"              % "15.0.1-R21",
  "com.fasterxml.jackson.core"   %  "jackson-databind"     % "2.15.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.15.0",
  "org.geotools"                 %  "gt-main"              % "28.1",
  "org.geotools"                 %  "gt-epsg-hsql"         % "28.1"
) ++ javaFXModules