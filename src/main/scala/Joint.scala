import scala.collection.mutable.ArrayBuffer
import scala.math.{cos, sin}

class Joint(val name: String, val parent: Option[Joint], val radius: Int, private var angle: Int) extends Component {
  private var pos = new Pos(Animator.viewerW / 2, Animator.viewerH / 2)
  private var locked = false

  def calculatePos(): Unit = {
    val parentPos = if (this.parent.isDefined) this.parent.get.getPos else this.pos
    val angleRadian = Math.toRadians(this.angle)

    val newX = cos(angleRadian) * this.radius + parentPos.x

    val newY = -sin(angleRadian) * this.radius + parentPos.y

    this.setPos(new Pos(newX.toInt, newY.toInt))
  }
  this.calculatePos()

  def getLocked = this.locked
  def getAngle = this.angle
  def getPos = this.pos

  def toggleLocked(): Unit = {
    if (this.locked) this.locked = false
    else this.locked = true
  }

  def setPos(newPos: Pos): Unit = {
    this.pos = newPos
  }

  def setAngle(newAngle: Int): Unit = {
    this.angle = newAngle
  }

  def getCopy(copyJoints: ArrayBuffer[Joint]) = {
    val copyParent = if (this.parent.isDefined) {
      Some(copyJoints.find(_.name == this.parent.get.name).get)
    } else None
    new Joint(this.name, copyParent, this.radius, this.angle)
  }

  def update(): Unit = {
    //Animator.getG.fillOval(this.pos.x, this.pos.y, 10, 10)
  }
}