import scalafx.scene.paint.Color.{Red, White}
import scala.collection.mutable.ArrayBuffer
import scala.math._

class Joint(val parentCP: ControlPoint, val jointRadius: Double, angle: Double)
    extends ControlPoint with ChildFrameComponent {

    private var locked = false
    private var angleToParent = angle - parentCP.angleToScene

    private val frameData = ArrayBuffer.fill(frameCount)(angleToParent)

    override def getParent = if (!parentCP.isLocked) parentCP else parentCP.getParent

    def setPos(x: Double, y: Double): Unit = {
        centerX = x
        centerY = y
    }

    def angleToScene = parentCP.angleToScene + angleToParent

    def rotate(dAngleToParent: Double): Unit = {
        if (!parentCP.isLocked) {
            angleToParent += {
                if (dAngleToParent < 0) dAngleToParent + 360
                else if (dAngleToParent > 360) dAngleToParent - 360
                else dAngleToParent
            }
        } else parentCP.rotate(dAngleToParent)
    }

    def isLocked = locked

    def toggleLocked(): Unit = {
        if (locked) {
            locked = false
            fill = White
        } else {
            locked = true
            fill = Red
        }
    }

    def loadFrameData(): Unit = {
        rotate(frameData(currIdx) - angleToParent)
        children.foreach(_.loadFrameData())
    }

    def saveFrameData(): Unit = {
        frameData(currIdx) = angleToParent
        children.foreach(_.saveFrameData())
    }

    def addFrame(): Unit = {
        frameData += frameData.last
        children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        frameData -= frameData.last
        children.foreach(_.deleteFrame())
    }

    onMouseClicked = (event) => {
        toggleLocked()
    }


    onMouseDragged = (event) => {
        if (!isLocked) {
            val dxParentToMouse = event.getX - getParent.centerX.toDouble
            val dyParentToMouse = event.getY - getParent.centerY.toDouble

            val dParentToMouse = sqrt(pow(dxParentToMouse, 2) + pow(dyParentToMouse, 2))

            val angleMouse = if (dParentToMouse != 0) {
                if (dyParentToMouse <= 0) acos(dxParentToMouse / dParentToMouse).toDegrees
                else acos(-dxParentToMouse / dParentToMouse).toDegrees + 180
            } else 0

            val dAngleToParent = angleMouse - angleToScene

            rotate(dAngleToParent)
        }
    }

    def update(): Unit = {
        val angleRadian = toRadians(angleToScene)

        val newX = cos(angleRadian) * jointRadius + parentCP.centerX.toDouble

        val newY = -sin(angleRadian) * jointRadius + parentCP.centerY.toDouble

        setPos(newX, newY)
        children.foreach(_.update())
    }

    children += new Arm(this)
}