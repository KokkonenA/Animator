class Expression(private var mood: String) {
  def getMood = this.mood

  def setMood(newMood: String): Unit = {
    this.mood = newMood
  }
}