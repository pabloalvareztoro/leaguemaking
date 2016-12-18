package models

import play.api.libs.json.Reads._
import play.api.libs.json._

case class Team (name: String, var score: Int)