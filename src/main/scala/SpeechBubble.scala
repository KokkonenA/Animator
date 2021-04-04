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

  def getCopy = {
    val newBubble = new SpeechBubble(this.pos)
    newBubble.setText(this.text)
    newBubble
  }

  def draw(): Unit = {
    Animator.getG.fillOval(this.pos.x, this.pos.y, 10, 10)
  }
}