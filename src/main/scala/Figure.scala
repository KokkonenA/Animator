import scala.collection.mutable.ArrayBuffer

class Figure (structure: ArrayBuffer[String]) extends ControlPoint {
    this.centerX = (Viewer.width / 2).toInt
    this.centerY = (Viewer.height / 2).toInt

    private var angle = 0.0

    private var frameData = ArrayBuffer.fill(this.frameCount)(this.centerX.toDouble, this.centerY.toDouble)

    def angleToScene = this.angle

    def isLocked = false

    def rotate(dAngleToParent: Double): Unit = {
        this.angle += dAngleToParent
    }

    def setPos(x: Double, y: Double): Unit = {
        this.centerX = x
        this.centerY = y
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

        this.children +: newJoints.values.toArray

        i += 1

        if (structure(i)(0) != '/') {
            val split = structure(i).split(",")
            println(split(0))
            val parent = newJoints(split(0))
            val expression = split(1)

            parent.children += new Head(parent, expression)
        }
        this.children += new SpeechBubble(this)
    }

    def addFrame(): Unit = {
        this.frameData += this.frameData.last
        this.children.foreach(_.addFrame())
    }

    def deleteFrame(): Unit = {
        this.frameData -= this.frameData.last
        this.children.foreach(_.deleteFrame())
    }

    def updateFrame(): Unit = {
        val data = this.frameData(this.currIdx)
        this.setPos(data._1, data._2)
        this.children.foreach(_.updateFrame())
        this.children.foreach(_.updatePos())
    }

    def updateFrameData(): Unit = {
        this.frameData(this.currIdx) = (this.centerX.toDouble, this.centerY.toDouble)
        this.children.foreach(_.updateFrameData())
    }

    this.onMouseDragged = (event) => {
        val x = this.centerX.toDouble
        val y = this.centerY.toDouble

        val mouseX = event.getX
        val mouseY = event.getY

        this.centerX = mouseX
        this.centerY = mouseY

        val dx = mouseX - x
        val dy = mouseY - y

        this.children.foreach(_.updatePos())
    }
    this.loadStructure(structure)
    this.children.foreach(_.updatePos())
}