import scalafx.scene.paint.Color.{Red, White}
import scala.math.{acos, cos, pow, sin, sqrt, toRadians}

class Joint(val parentCP: ControlPoint,
            val jointRadius: Double,
            private var angle: Double)
      extends ControlPoint with ChildComponent {

  private var locked = false

  def setPos(x: Double, y: Double): Unit = {
    this.centerX = x
    this.centerY = y
  }

  def getAngle = this.angle

  def setAngle(newAngle: Double): Unit = {
    this.angle = if (newAngle < 0) 359 else if (newAngle > 360) 1 else newAngle
    this.update()
  }

  def getLocked = this.locked

  def toggleLocked(): Unit = {
    if (this.locked) {
      this.locked = false
      this.fill = White
    } else {
      this.locked = true
      this.fill = Red
    }
  }

  this.onMouseClicked = (event) => {
    this.toggleLocked()
  }

  this.onMouseDragged = (event) => {
    val dxParentToMouse = event.getX - this.parentCP.centerX.toDouble
    val dyParentToMouse = event.getY - this.parentCP.centerY.toDouble

    val dParentToMouse = sqrt(pow(dxParentToMouse, 2) + pow(dyParentToMouse, 2))

    val angleMouse = if (dParentToMouse != 0) {
      if (dyParentToMouse <= 0) acos(dxParentToMouse / dParentToMouse).toDegrees
      else acos(-dxParentToMouse / dParentToMouse).toDegrees + 180
    } else 0

    this.setAngle(angleMouse)
  }

  def update(): Unit = {
    val angleRadian = toRadians(this.angle)

    val newX = cos(angleRadian) * this.jointRadius + this.parentCP.centerX.toDouble

    val newY = -sin(angleRadian) * this.jointRadius + this.parentCP.centerY.toDouble

    this.setPos(newX, newY)
    this.children.foreach(_.update())
  }

  this.children += new Arm(this)
}