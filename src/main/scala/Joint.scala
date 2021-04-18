import scalafx.scene.paint.Color.{Red, White}
import scala.collection.mutable.ArrayBuffer
import scala.math.{acos, cos, pow, sin, sqrt, toRadians}

class Joint(val parentCP: ControlPoint,
            val jointRadius: Double,
            angle: Double)
    extends ControlPoint with ChildFrameComponent {

    private var locked = false
    private var angleToParent = angle - this.parentCP.angleToScene

    private val frameData = ArrayBuffer.fill(this.frameCount)(this.angleToParent)

    def setPos(x: Double, y: Double): Unit = {
        this.centerX = x
        this.centerY = y
    }

    def angleToScene = this.parentCP.angleToScene + this.angleToParent

    def rotate(dAngleToParent: Double): Unit = {
        if (!this.parentCP.isLocked) {
            this.angleToParent += {
                if (dAngleToParent < 0) dAngleToParent + 360
                else if (dAngleToParent > 360) dAngleToParent - 360
                else dAngleToParent
            }
        } else this.parentCP.rotate(dAngleToParent)
    }

    def isLocked = this.locked

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
        if (!this.isLocked) {
            val dxParentToMouse = event.getX - this.parentCP.centerX.toDouble
            val dyParentToMouse = event.getY - this.parentCP.centerY.toDouble

            val dParentToMouse = sqrt(pow(dxParentToMouse, 2) + pow(dyParentToMouse, 2))

            val angleMouse = if (dParentToMouse != 0) {
                if (dyParentToMouse <= 0) acos(dxParentToMouse / dParentToMouse).toDegrees
                else acos(-dxParentToMouse / dParentToMouse).toDegrees + 180
            } else 0

            val dAngleToParent = angleMouse - this.angleToScene

            this.rotate(dAngleToParent)
        }
    }

    def loadFrameData(): Unit = {
        this.rotate(this.frameData(this.currIdx) - this.angleToParent)
        this.children.foreach(_.loadFrameData())
    }

    def saveFrameData(): Unit = {
        this.frameData(this.currIdx) = this.angleToParent
        this.children.foreach(_.saveFrameData())
    }

    def addFrame(): Unit = {
        this.frameData += this.frameData.last
        this.children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        this.frameData -= this.frameData.last
        this.children.foreach(_.deleteFrame())
    }

    def update(): Unit = {
        val angleRadian = toRadians(this.angleToScene)

        val newX = cos(angleRadian) * this.jointRadius + this.parentCP.centerX.toDouble

        val newY = -sin(angleRadian) * this.jointRadius + this.parentCP.centerY.toDouble

        this.setPos(newX, newY)
        this.children.foreach(_.update())
    }

    this.children += new Arm(this)
}