import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

class SpeechBubble (val parentCP: ControlPoint) extends Circle with ChildComponent {
  private var text = ""

  this.radius = 30
  this.stroke = Gray
  this.fill = White

  private var dxToParent = 0.0
  private var dyToParent = -200.0

  def getText = this.text

  def setText(newText: String): Unit = {
    this.text = newText
  }

  this.onMouseDragged = (event) => {
    val x = this.centerX.toDouble
    val y = this.centerY.toDouble

    val mouseX = event.getX
    val mouseY = event.getY

    this.centerX = mouseX
    this.centerY = mouseY

    this.dxToParent += mouseX - x
    this.dyToParent += mouseY - y

  }

  def update(): Unit = {
    this.centerX = this.parentCP.centerX.toDouble + dxToParent
    this.centerY = this.parentCP.centerY.toDouble + dyToParent
  }
}