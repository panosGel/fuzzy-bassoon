package com.fuzzy.akka.dbreader

import akka.actor.{Actor, ActorLogging, Props}
import com.fuzzy.akka.dbreader.MovieCrawler.{Crawl, CrawlRequest, SaveMovieJson}

object MovieCrawler {
  def props: Props = Props[DynamoDBReaderWriter]
  case class CrawlRequest(url: String)
  case class CrawlResponse(links: Set[String])
  case class Crawl(url: String)
  case class SaveMovieJson(jsonString: String)
}


class MovieCrawler extends Actor with ActorLogging {
  override def receive: Receive = {
    case CrawlRequest(url) =>
      log.info(s"Crawl Request received")
    case Crawl(url) =>
      log.info(s"Crawl received")
    case SaveMovieJson(movieJson) =>
      log.info(s"Save movie received")
  }
}
