
name := "fuzzy-bassoon"

version := "1.0"

lazy val akkaVersion="2.5.3"
lazy val alpakkaVersion = "0.16"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-dynamodb" % alpakkaVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

