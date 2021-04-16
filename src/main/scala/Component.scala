import scalafx.Includes._
import scalafx.scene.Node
import scala.collection.mutable.ArrayBuffer

trait FrameComponent extends Node {
    val children = ArrayBuffer[ChildComponent] ()
    Viewer.children += this
}

trait ChildComponent extends FrameComponent {
    val parentCP: ControlPoint
    def update(): Unit
}