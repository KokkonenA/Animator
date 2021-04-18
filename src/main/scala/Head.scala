import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

import scala.collection.mutable.ArrayBuffer
import scala.math.{cos, sin}

class Head (val parentCP: ControlPoint, private var expression: String) extends Circle with ChildFrameComponent {
    radius = 20
    stroke = Gray
    fill = White

    private val frameData = ArrayBuffer.fill(frameCount)(expression)

    def getExpression = expression

    def setExpression(newExpression: String): Unit = {
        expression = newExpression
    }

    def addFrame(): Unit = {
        frameData += frameData.last
        children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        frameData += frameData.last
        children.foreach(_.deleteFrame())
    }

    def loadFrameData(): Unit = {
        expression = frameData(currIdx)
    }

    def saveFrameData(): Unit = {
        frameData(currIdx) = expression
    }

    def update(): Unit = {
        val angle = parentCP.angleToScene.toRadians

        centerX = parentCP.centerX.toDouble + radius.toDouble * cos(angle)
        centerY = parentCP.centerY.toDouble - radius.toDouble * sin(angle)
    }
}
