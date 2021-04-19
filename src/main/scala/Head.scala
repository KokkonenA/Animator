import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle
import scala.math.{cos, sin}

class Head (val parentCP: ControlPoint, private var expression: String) extends Circle with ChildFrameComponent {
    radius = 20
    stroke = Gray
    fill = White

    private val frameData =
        collection.mutable.Map((Animator.frames zip Array.fill(frameCount)(expression)).toSeq: _*)

    def getExpression = expression

    def setExpression(newExpression: String): Unit = {
        expression = newExpression
    }

    def addFrameToEnd(): Unit = {
        frameData += frameData.last
        children.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame(): Unit = {
        frameData += frameData.last
        children.foreach(_.deleteLastFrame())
    }

    def loadFrameData(): Unit = {
        expression = frameData(currFrame)
    }

    def saveFrameData(): Unit = {
        frameData(currFrame) = expression
    }

    def update(): Unit = {
        val angle = parentCP.angleToScene.toRadians

        centerX = parentCP.centerX.toDouble + radius.toDouble * cos(angle)
        centerY = parentCP.centerY.toDouble - radius.toDouble * sin(angle)
    }
}
