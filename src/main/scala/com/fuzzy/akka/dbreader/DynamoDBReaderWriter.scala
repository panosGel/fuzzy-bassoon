package com.fuzzy.akka.dbreader

import akka.NotUsed
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.stream.{ActorMaterializer, scaladsl}
import akka.stream.alpakka.dynamodb.impl.DynamoSettings
import akka.stream.alpakka.dynamodb.scaladsl.DynamoClient
import com.amazonaws.services.dynamodbv2.model.{ListTablesRequest, ListTablesResult, ScanRequest, ScanResult}
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.scaladsl.Source
import com.amazonaws.services.dynamodbv2.model.{ListTablesRequest, ListTablesResult, ScanRequest, ScanResult}

import scala.concurrent.Future
import scala.util.{Failure, Success}


object DynamoDBReaderWriter {
  def props: Props = Props[DynamoDBReaderWriter]
  final case class WriteDocument(jsonString: String)
  final case class ReadDocuments(tableName: String)
  final case class ListTables()
}

class DynamoDBReaderWriter extends Actor with ActorLogging {

  import DynamoDBReaderWriter._

  implicit val ec = context.dispatcher

  implicit val system = ActorSystem("dbreader")

  implicit val materializer = ActorMaterializer()


  System.setProperty("aws.accessKeyId", "anAccessKeyId")
  System.setProperty("aws.secretKey", "aSecretKey")
  val settings = DynamoSettings(system)
  val client = DynamoClient(settings)
  private val config = context.system.settings.config
  override def receive: Receive = {
    case WriteDocument(jsonString: String) =>
      log.info(s"writing a json document to the db")
    case ReadDocuments(tableName: String) =>
      log.info(s"reading all documents from ${tableName}")
      readTable(tableName)
    case ListTables => dynamoDBListTables
      log.info(s"listing all tables in dynamoDB...")
  }


  private def dynamoDBListTables(): Unit = {
    import akka.stream.alpakka.dynamodb.scaladsl.DynamoImplicits._
    val listTablesResult: Future[ListTablesResult] = client.single(new ListTablesRequest())
    listTablesResult.onComplete {
      case Success(tables) => log.info(s"tables in DynamoDB: ${tables}")
      case Failure(e) => log.info(s"error listing tables")
    }
  }

  private def readTable(tableName: String): Unit = {
    import akka.stream.alpakka.dynamodb.scaladsl.DynamoImplicits._
    log.info(s"reading all documents from ${tableName}")
    val scanPages: Source[ScanResult, NotUsed] =
    client.source(new ScanRequest().withTableName(tableName))
    scanPages.runForeach(i => println(i))(materializer)
  }
}
