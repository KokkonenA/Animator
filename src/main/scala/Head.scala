import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

import scala.collection.mutable.ArrayBuffer
import scala.math.{cos, sin}

class Head (val parentCP: ControlPoint, private var expression: String) extends Circle with ChildFrameComponent {
    this.radius = 20
    this.stroke = Gray
    this.fill = White

    private val frameData = ArrayBuffer.fill(this.frameCount)(this.expression)

    def getExpression = this.expression

    def setExpression(newExpression: String): Unit = {
        this.expression = newExpression
    }

    def addFrame(): Unit = {
        this.frameData += this.frameData.last
        this.children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        this.frameData += this.frameData.last
        this.children.foreach(_.deleteFrame())
    }

    def loadFrameData(): Unit = {
        this.expression = this.frameData(this.currIdx)
    }

    def saveFrameData(): Unit = {
        this.frameData(this.currIdx) = this.expression
    }

    def update(): Unit = {
        val angle = this.parentCP.angleToScene.toRadians

        this.centerX = this.parentCP.centerX.toDouble + this.radius.toDouble * cos(angle)
        this.centerY = this.parentCP.centerY.toDouble - this.radius.toDouble * sin(angle)
    }
}
