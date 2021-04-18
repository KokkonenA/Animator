import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

trait ControlPoint extends Circle with FrameComponent {
    this.radius = 10
    this.stroke = Gray
    this.fill = White

    def angleToScene: Double
    def isLocked: Boolean
    def rotate(dAngleToParent: Double): Unit
}
