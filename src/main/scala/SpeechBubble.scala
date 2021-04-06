import scalafx.scene.shape.Circle

class SpeechBubble (private var pos: Pos) extends Component {
  private var text = ""

  val shape = new Circle {
    layoutX = SpeechBubble.this.pos.x
    layoutY = SpeechBubble.this.pos.y
    radius = 10
  }

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

  def update(): Unit = {
    //Animator.getG.fillOval(this.pos.x, this.pos.y, 10, 10)
  }
}