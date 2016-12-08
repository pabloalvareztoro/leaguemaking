package controllers

import models.{Match, Team}
import api.JsonCombinators._
import play.api.libs.json.{JsArray, JsResult, JsValue, Json}
import play.api.mvc._

import scala.collection.mutable.ListBuffer

object MatchResult extends Controller {

  def matchResult = Action(parse.json) { implicit request =>
    try{
      request.body match{
        case _: JsArray => {
          val schedule: Seq[Match] = playSchedule(request.body.asInstanceOf[JsArray])
          Ok(Json.toJson(schedule))
        }
        case _: JsValue => {
          val newMatch: Match = playMatch(request.body)
          Ok(Json.toJson(newMatch))
        }
      }
    }catch{
      case ex: NoSuchElementException => BadRequest
      case ex: Exception => InternalServerError
    }
  }

  def fixtureResult = Action(parse.json) { implicit request =>
    try{
      Ok(Json.toJson(playFixture(request.body.asInstanceOf[JsArray])))
    }catch{
      case ex: NoSuchElementException => BadRequest
      case ex: Exception => InternalServerError
    }
  }

  def playFixture(json: JsArray): Seq[Seq[Match]] = {
    val newFixture: ListBuffer[Seq[Match]] = ListBuffer[Seq[Match]]()
    val fixture: Seq[JsValue] = json.value
    for(x: JsValue <- fixture) {
      val play: Seq[Match] = playSchedule(x.asInstanceOf[JsArray])
      newFixture.append(play)
    }
    return newFixture
  }

  def playSchedule(json: JsArray): Seq[Match] = {
    val schedule: ListBuffer[Match] = ListBuffer[Match]()
    val matches: Seq[JsValue] = json.value
    for(x <- matches) {
      val newMatch: Match = playMatch(x)
      schedule.append(newMatch)
    }
    return schedule
  }

  def playMatch(json: JsValue): Match = {
    val firstTeam: Team = (json)(0).validate[Team].get
    val secondTeam: Team = (json)(1).validate[Team].get
    val newMatch = new Match(Seq[Team](firstTeam, secondTeam))
    newMatch.playMatch()
    return newMatch
  }

}
