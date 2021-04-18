import scalafx.scene.Node
import scala.collection.mutable.ArrayBuffer

trait FrameComponent extends Node {
    def frameCount = Animator.frameCount
    def currIdx = Animator.currIdx

    def addFrame(): Unit
    def deleteFrame(): Unit
    def updateFrame(): Unit
    def updateFrameData(): Unit

    val children = ArrayBuffer[ChildFrameComponent] ()

    def remove(): Unit = {
        Viewer.children.remove(this)
        this.children.foreach(_.remove())
    }

    Viewer.children.add(this)
}

trait ChildFrameComponent extends FrameComponent {
    val parentCP: ControlPoint
    def updatePos(): Unit
}

trait TimelineComponent extends Node {
    def remove(): Unit = {
        Timeline.children.remove(this)
    }
    Timeline.children.add(this)
}