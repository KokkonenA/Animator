import scalafx.scene.Node
import scala.collection.mutable.ArrayBuffer

trait FrameComponent extends Node {
    def frameCount = Animator.frameCount
    def currIdx = Animator.currIdx

    def addFrame(): Unit
    def deleteFrame(): Unit
    def loadFrameData(): Unit
    def saveFrameData(): Unit

    val children = ArrayBuffer[ChildFrameComponent] ()

    def remove(): Unit = {
        Viewer.children.remove(this)
        this.children.foreach(_.remove())
    }

    Viewer.children.add(this)
}

trait ChildFrameComponent extends FrameComponent {
    val parentCP: ControlPoint
    def update(): Unit
}

trait TimelineComponent extends Node {
    Timeline.children.add(this)
}