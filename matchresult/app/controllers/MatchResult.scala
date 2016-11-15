package controllers

import models.{Match, Team}
import api.JsonCombinators._
import play.api.libs.json.{JsResult, JsValue, Json}
import play.api.mvc._

object MatchResult extends Controller {

  def matchResult = Action(parse.json) { implicit request =>
    try{
      val newMatch: Match = playMatch(request.body)
      //Ok(newMatch.firstTeam.name + " " + newMatch.firstTeam.score + "-" + newMatch.secondTeam.score + " " + newMatch.secondTeam.name)
      Ok(Json.toJson(newMatch))
    }catch{
      case ex: NoSuchElementException => BadRequest
      case ex: Exception => InternalServerError
    }
  }

  def playMatch(json: JsValue): Match = {
    val firstTeam: Team = (json \ "teams")(0).validate[Team].get
    val secondTeam: Team = (json \ "teams")(1).validate[Team].get
    val newMatch = new Match(Seq[Team](firstTeam, secondTeam))
    newMatch.playMatch()
    return newMatch
  }

}
