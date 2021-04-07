import scalafx.scene.shape.Circle

class SpeechBubble extends Circle {
  private var text = ""

  def getText = this.text

  def setText(newText: String): Unit = {
    this.text = newText
  }

  def getCopy = {
    val newBubble = new SpeechBubble
    newBubble.setText(this.text)
    newBubble
  }

  def update(): Unit = {
    //Animator.getG.fillOval(this.pos.x, this.pos.y, 10, 10)
  }
}