import scalafx.scene.shape.Circle
import scalafx.Includes._

class SpeechBubble extends Circle {
  private var text = ""

  def getText = this.text

  def setText(newText: String): Unit = {
    this.text = newText
  }

  def getCopy = {
    val newBubble = new SpeechBubble {
      this.centerX = SpeechBubble.this.centerX.toDouble
      this.centerY = SpeechBubble.this.centerY.toDouble
    }
    newBubble.setText(this.text)
    newBubble
  }

  def update(): Unit = {
  }

  Viewer.children += this
}