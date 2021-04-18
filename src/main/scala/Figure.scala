import scala.collection.mutable.ArrayBuffer

class Figure (structure: ArrayBuffer[String]) extends ControlPoint {
    centerX = (Viewer.width / 2).toInt
    centerY = (Viewer.height / 2).toInt

    private var angle = 0.0

    private var frameData = ArrayBuffer.fill(frameCount)(centerX.toDouble, centerY.toDouble)

    def angleToScene = angle

    def isLocked = false

    def rotate(dAngleToParent: Double): Unit = {
        angle += dAngleToParent
    }

    def setPos(x: Double, y: Double): Unit = {
        centerX = x
        centerY = y
    }

    private def loadStructure(structure: ArrayBuffer [String]) {
        var newJoints = Map [String, Joint] ()

        var i = 2

        while (structure(i).trim != "/Joints") {
            val line = structure(i).split(",")
            val name = line(0)
            val parentName = line(1)
            val parent = if (parentName == "Center") this else newJoints(parentName)
            val radius = line(2).toInt
            val angle = line(3).toInt
            val newJoint = new Joint(parent, radius, angle)
            parent.children += newJoint
            newJoints += (name -> newJoint)
            i += 1
        }

        children +: newJoints.values.toArray

        i += 1

        if (structure(i)(0) != '/') {
            val split = structure(i).split(",")
            val parent = newJoints(split(0))
            val expression = split(1)

            parent.children += new Head(parent, expression)
        }
        children += new SpeechBubble(this)
    }

    def addFrame(): Unit = {
        frameData += frameData.last
        children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        frameData -= frameData.last
        children.foreach(_.deleteFrame())
    }

    def loadFrameData(): Unit = {
        val data = frameData(currIdx)
        setPos(data._1, data._2)
        children.foreach(_.loadFrameData())
    }

    def saveFrameData(): Unit = {
        frameData(currIdx) = (centerX.toDouble, centerY.toDouble)
        children.foreach(_.saveFrameData())
    }

    onMouseDragged = (event) => {
        val x = centerX.toDouble
        val y = centerY.toDouble

        val mouseX = event.getX
        val mouseY = event.getY

        centerX = mouseX
        centerY = mouseY

        val dx = mouseX - x
        val dy = mouseY - y
    }

    def update(): Unit = {
        children.foreach(_.update())
    }

    loadStructure(structure)
}