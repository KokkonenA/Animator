class SpeechBubble (private var pos: Pos) {
  private var text = ""

  def getPos = this.pos
  def getText = this.text

  def setPos(newPos: Pos): Unit = {
    this.pos = newPos
  }

  def setText(newText: String): Unit = {
    this.text = newText
  }
}
