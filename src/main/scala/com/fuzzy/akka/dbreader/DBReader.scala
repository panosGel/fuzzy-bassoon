package com.fuzzy.akka.dbreader

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object Printer {

  def props: Props = Props[Printer]
  final case class Output(message: String)

}
class Printer extends Actor with ActorLogging {
  import Printer._

  override def receive = {
    case Output(outputMessage) =>
      log.info(s"Received output message from ${sender}: ${outputMessage}")
  }
}
object DBReader extends App {
  val system: ActorSystem = ActorSystem("dbreader")
}
