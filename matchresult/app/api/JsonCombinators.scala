package api

import models.Team
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Json, Reads, Writes}

object JsonCombinators {

  implicit val teamWrites = new Writes[Team] {
    def writes(t: Team) = Json.obj(
      "name" -> t.name
    )
  }

  implicit val teamReads: Reads[Team] =
    (JsPath \ "name").read[String](minLength[String](1)).map(name => Team(name, 0))
}
