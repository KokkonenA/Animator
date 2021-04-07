import scala.collection.mutable.ArrayBuffer
import scala.math.{cos, sin}
import scalafx.Includes._
import scalafx.scene.paint.Color.{Gray, Red}
import scalafx.scene.shape.Circle

class Joint (val name: String,
             val parentJoint: Option[Joint],
             val jointRadius: Int,
             private var angle: Int) extends Circle {

  this.centerX = (Viewer.width / 2).toInt
  this.centerY = (Viewer.width / 2).toInt
  this.radius = 10
  this.fill = Gray

  private var locked = false

  def calculatePos(): Unit = {
    val parentX = if (this.parentJoint.isDefined) this.parentJoint.get.centerX else this.centerX
    val parentY = if (this.parentJoint.isDefined) this.parentJoint.get.centerY else this.centerY
    val angleRadian = Math.toRadians(this.angle)

    val newX = cos(angleRadian) * this.jointRadius + parentX.toInt

    val newY = -sin(angleRadian) * this.jointRadius + parentY.toInt

    this.centerX = newX
    this.centerY = newY
  }
  this.calculatePos()

  def getLocked = this.locked
  def getAngle = this.angle

  def toggleLocked(): Unit = {
    if (this.locked) {
      this.locked = false
      this.fill = Gray
    } else {
      this.locked = true
      this.fill = Red
    }
  }

  def setAngle(newAngle: Int): Unit = {
    this.angle = newAngle
  }

  def getCopy(copyJoints: ArrayBuffer[Joint]) = {
    val copyParent = if (this.parentJoint.isDefined) {
      Some(copyJoints.find(_.name == this.parentJoint.get.name).get)
    } else None
    new Joint(this.name, copyParent, this.jointRadius, this.angle)
  }

  def update(): Unit = {
  }

  this.onMouseClicked = (event) => {
    this.toggleLocked()
  }

  Viewer.children += this
}