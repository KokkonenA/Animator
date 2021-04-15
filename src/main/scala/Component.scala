import scalafx.Includes._
import scalafx.scene.Node
import scalafx.scene.paint.Color.{Gray, White}
import scalafx.scene.shape.Circle

import scala.collection.mutable.ArrayBuffer

trait FrameComponent extends Node {
    Viewer.children += this
}

trait ChildComponent extends FrameComponent {
    val parentCP: ControlPoint
    def update(): Unit
}

trait ControlPoint extends Circle with FrameComponent {
    this.radius = 10
    this.stroke = Gray
    this.fill = White

    val children = ArrayBuffer[ChildComponent] ()
}