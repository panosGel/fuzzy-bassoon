package com.fuzzy.akka.dbreader

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import com.fuzzy.akka.dbreader.DynamoDBReaderWriter.{ListTables, ReadDocuments, WriteDocument}

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
object FuzzyApp extends App {
  val system: ActorSystem = ActorSystem("dbreader")

  val readerWriter: ActorRef = system.actorOf(DynamoDBReaderWriter.props, "dynamoDBReaderWriterActor")

  readerWriter ! ListTables
  readerWriter ! ReadDocuments("robotCritic_movieReviews")

}
