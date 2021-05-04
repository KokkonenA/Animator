import scalafx.scene.Node

import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer

//Components that are in the Viewer window
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

    def read(lines: Array[String]): Array[String]
    def write(file: PrintWriter): Unit

    val children = ArrayBuffer[ChildFrameComponent] ()

    def remove(): Unit = {
        Viewer.children.remove(this)
        children.foreach(_.remove())
    }
    Viewer.children.add(this)
}

//frame components that have a parent
trait ChildFrameComponent extends FrameComponent {
    val parentCP: ControlPoint
    def update(): Unit
}

//components that show in the timeline
trait TimelineComponent extends Node {
    def remove(): Unit = {
        Timeline.children.remove(this)
    }
    Timeline.children.add(this)
}