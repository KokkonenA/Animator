class Expression {
  private var mood = "happy"

  def getMood = this.mood

  def setMood(newMood: String): Unit = {
    this.mood = newMood
  }
}
