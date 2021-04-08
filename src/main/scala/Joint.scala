import scala.collection.mutable.ArrayBuffer
import scala.math._
import scalafx.Includes._
import scalafx.scene.paint.Color.{Gray, Red, White}
import scalafx.scene.shape.Circle

class Joint (val name: String,
             val parentJoint: Option[Joint],
             val jointRadius: Int,
             private var angle: Int) extends Circle {

  this.centerX = (Viewer.width / 2).toInt
  this.centerY = (Viewer.width / 2).toInt
  this.radius = 10
  this.stroke = Gray
  this.fill = White

  private var locked = false
  private var moved = false
  private var rotated = false

  val hasParent = this.parentJoint.isDefined

  val arm: Option[Arm] = if (this.hasParent) Some(new Arm {
    startX = Joint.this.centerX.toDouble
    startY = Joint.this.centerY.toDouble
    endX = Joint.this.parentJoint.get.centerX.toDouble
    endY = Joint.this.parentJoint.get.centerY.toDouble
  }) else None


  def setPos(x: Double, y: Double): Unit = {
    this.centerX = x
    this.centerY = y
    this.moved = true
  }

  def getAngle = this.angle

  def setAngle(newAngle: Int): Unit = {
    this.angle = if (newAngle < 0) 359 else if (newAngle > 360) 1 else newAngle
    this.rotated = true
  }

  def rotateClockwise() = {
    this.setAngle(this.angle + 1)
  }
  def rotateCounterclockwise() = {
    this.setAngle(this.angle - 1)
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

  def isMoved = this.moved
  def reset() = {
    this.moved = false
    this.rotated = false
  }

  def calculatePos(): Unit = {
    val parentX = if (hasParent) this.parentJoint.get.centerX else this.centerX
    val parentY = if (hasParent) this.parentJoint.get.centerY else this.centerY
    val angleRadian = toRadians(this.angle)

    val newX = cos(angleRadian) * this.jointRadius + parentX.toInt

    val newY = -sin(angleRadian) * this.jointRadius + parentY.toInt

    this.setPos(newX, newY)
  }

  def getCopy(copyJoints: ArrayBuffer[Joint]) = {
    val copyParent = if (this.hasParent) {
      Some(copyJoints.find(_.name == this.parentJoint.get.name).get)
    } else None
    new Joint(this.name, copyParent, this.jointRadius, this.angle)
  }

  this.onMouseClicked = (event) => {
    this.toggleLocked()
  }

  this.onMouseDragged = (event) => {
    val mouseX = event.getX
    val mouseY = event.getY

    val x = this.centerX.toInt
    val y = this.centerY.toInt

    if (hasParent) {
      val parentX = this.parentJoint.get.centerX.toInt
      val parentY = this.parentJoint.get.centerY.toInt

      val dxParentToMouse = mouseX - parentX
      val dyParentToMouse = mouseY - parentY

      val dParentToMouse = sqrt(pow(dxParentToMouse, 2) + pow(dyParentToMouse, 2))

      val angleMouse = if (dParentToMouse != 0) {
        if (dyParentToMouse <= 0) acos(dxParentToMouse / dParentToMouse).toDegrees
        else acos(-dxParentToMouse / dParentToMouse).toDegrees + 180
      } else 0

      this.setAngle(angleMouse.toInt)
    } else {
      this.setPos(mouseX, mouseY)
    }
  }

  def update(): Unit = {
    if (hasParent && (this.parentJoint.get.isMoved) || this.rotated) {
      this.calculatePos()

      this.arm.get.startX = Joint.this.centerX.toDouble
      this.arm.get.startY = Joint.this.centerY.toDouble
      this.arm.get.endX = Joint.this.parentJoint.get.centerX.toDouble
      this.arm.get.endY = Joint.this.parentJoint.get.centerY.toDouble
    }
  }

  this.calculatePos()
  Viewer.children += this
}