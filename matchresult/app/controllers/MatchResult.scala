package controllers

import models.Team
import api.JsonCombinators._
import play.api.libs.json.{JsResult, JsValue, Json}
import play.api.libs.json.Json._
import play.api.mvc._

object MatchResult extends Controller {

  def matchResult = Action(parse.json) { implicit request =>
    val json: JsValue = request.body
    val firstTeam: Team = (json \ "teams")(0).validate[Team].get
    val secondTeam: Team = (json \ "teams")(1).validate[Team].get
    firstTeam.score = 1
    secondTeam.score = 4
    Ok(firstTeam.name + " " + firstTeam.score + "-" + secondTeam.score + " " + secondTeam.name)
  }

}
