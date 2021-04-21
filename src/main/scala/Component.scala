import scalafx.scene.Node
import scala.collection.mutable.ArrayBuffer

trait FrameComponent extends Node {
    def frames = Animator.frames
    def frameCount = Animator.frameCount
    def currFrame = Animator.currFrame

    def addFrameToEnd(): Unit
    def deleteLastFrame(): Unit
    def loadFrameData(): Unit
    def saveFrameData(): Unit
    def setDataEqual(start: Frame, end: Frame): Unit
    def interpolate(start: Frame, end: Frame, length: Int): Unit

    val children = ArrayBuffer[ChildFrameComponent] ()

    def remove(): Unit = {
        Viewer.children.remove(this)
        children.foreach(_.remove())
    }
    Viewer.children.add(this)
}

trait ChildFrameComponent extends FrameComponent {
    val parentCP: ControlPoint
    def update(): Unit
}

trait TimelineComponent extends Node {
    def remove(): Unit = {
        Timeline.children.remove(this)
    }
    Timeline.children.add(this)
}