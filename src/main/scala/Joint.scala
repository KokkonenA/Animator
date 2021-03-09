import scala.math.{sin, cos}

class Joint(val parent: Joint, val radius: Int, private var angle: Int, private var pos: Pos) {
  private var locked = false

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

  def calculatePos(): Unit = {
    val newX = {
      if (this.angle <= 90 || angle >= 270) cos(this.angle) * this.radius + this.parent.getPos.y
      else -cos(this.angle) * this.radius + this.parent.getPos.x
    }

    val newY = sin(this.angle) * this.radius + this.parent.getPos.y

    this.setPos(new Pos(newX.asInstanceOf[Int], newY.asInstanceOf[Int]))
  }
}