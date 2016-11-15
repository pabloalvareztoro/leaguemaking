package models

case class Match(teams: Seq[Team]) {

  val firstTeam: Team = teams head
  val secondTeam: Team = teams last

  def playMatch() = {
    val totalGoals = getTotalGoals()
    val totalGoalsOfATeam = getGoalsOfATeam(totalGoals)
    firstTeam.score = totalGoalsOfATeam
    secondTeam.score = totalGoals - totalGoalsOfATeam
  }

  def getTotalGoals(): Int = {
    var totalGoals = 0
    do {
      totalGoals = getRandomNumber
    } while(getRandomNumber < (weights get(totalGoals) get))
    return totalGoals
  }

  def getGoalsOfATeam(totalGoals: Int): Int = (Math.random()*totalGoals).toInt

  def getRandomNumber: Int = (Math.random()*10).toInt

  val weights = Map[Int, Double](0 -> 0.6, 1 -> 0.7, 2 -> 0.8, 3 -> 1, 4 -> 1, 5 -> 0.8, 6 -> 0.7, 7 -> 0.4, 8 -> 0.1, 9 -> 0.1, 10 -> 0.1)

}
