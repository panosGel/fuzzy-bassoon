import sbt._

object Dependencies {

  object Akka {
    lazy val version = "2.5.3"
    val Default = Seq(
      "com.typesafe.akka" %% "akka-actor" % version,
      "com.typesafe.akka" %% "akka-testkit" % version
    )
  }

  object Alpakka {
    lazy val version = "0.16"
    val Default = Seq(
      "com.lightbend.akka" %% "akka-stream-alpakka-dynamodb" % version
    )
  }

  object Scalatest {
    val Default = Seq("org.scalatest" %% "scalatest" % "3.0.4" % "test")
  }

  object Logback {
    val Default = Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  }

  val SharedDependencies: Seq[ModuleID] =
    Akka.Default ++
    Alpakka.Default ++
    Scalatest.Default ++
    Logback.Default
}
