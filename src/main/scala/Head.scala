import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

class Head (val parentCP: ControlPoint, private var expression: String) extends Circle with ChildComponent {
    this.radius = 20
    this.stroke = Gray
    this.fill = White

    def getExpression = this.expression

    def setExpression(newExpression: String): Unit = {
        this.expression = newExpression
    }

    def update(): Unit = {
        this.centerX = this.parentCP.centerX.toDouble
        this.centerY = this.parentCP.centerY.toDouble - this.radius.toDouble
    }
}
