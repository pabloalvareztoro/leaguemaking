package api

import models.{Match, Team}
import play.api.libs.json._

object JsonCombinators {

  implicit val teamWrites = new Writes[Team] {
    def writes(t: Team) = Json.obj(
      "name" -> t.name,
      "score" -> t.score
    )
  }

  implicit val teamReads: Reads[Team] = (JsPath \ "name").read[String].map(name => Team(name, 0))

  implicit val matchWrites = new Writes[Match] {
    def writes(m: Match) = Json.obj(
      "teams" -> Seq(m.firstTeam, m.secondTeam)
    )
  }
}
