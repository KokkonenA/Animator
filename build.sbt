name := "OS2 animator"

version := "0.1"

scalaVersion := "2.13.5"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "14-R19"
// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
case n if n.startsWith("Linux") => "linux"
case n if n.startsWith("Mac") => "mac"
case n if n.startsWith("Windows") => "win"
case _ => throw new Exception("Unknown platform!")
}

// Add JavaFX dependencies. ScalaFx works on top of these.
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map( m =>
"org.openjfx" % s"javafx-$m" % "14.0.1" classifier osName
)

// Testing
// Scalatest
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.2" % "test"