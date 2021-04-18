import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

trait ControlPoint extends Circle with FrameComponent {
    radius = 10
    stroke = Gray
    fill = White

    def getParent = this
    def angleToScene: Double
    def isLocked: Boolean
    def rotate(dAngleToParent: Double): Unit
}
