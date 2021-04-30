import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Figure (val structureName: String) extends ControlPoint {
    centerX = (Viewer.width / 2).toInt
    centerY = (Viewer.height / 2).toInt

    private var angle = 0.0
    private val frameData =
        collection.mutable.Map((frames zip Array.fill(frameCount)(centerX.toDouble, centerY.toDouble)).toSeq: _*)

    def angleToScene = angle

    def isLocked = false

    def rotate(dAngleToParent: Double): Unit = {
        angle += dAngleToParent
    }

    def setPos(x: Double, y: Double): Unit = {
        centerX = x
        centerY = y
    }

    private def loadStructure(structureName: String) {
        val lines = ArrayBuffer [String] ()
        val bufferedSource = Source.fromFile("Structures")

        for (line <- bufferedSource.getLines()) {
            lines += line.trim
        }
        bufferedSource.close()

        val startIdx = lines.indexOf(structureName)
        val endIdx = lines.indexOf("/" + structureName)

        if (startIdx != -1 || endIdx != -1) {
            val structure = lines.slice(startIdx, endIdx + 1)
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
        } else {
            new Alert(AlertType.Error) {
                initOwner(Animator.stage)
                title = "Error"
                headerText = "Couldn't find a structure \"" + structureName + "\""
            }.showAndWait()
        }
    }

    def addFrameToEnd(): Unit = {
        frameData += (frames.last -> frameData.last._2)
        children.foreach(_.addFrameToEnd())
    }

    def deleteLastFrame(): Unit = {
        frameData -= frameData.last._1
        children.foreach(_.deleteLastFrame())
    }

    def loadFrameData(): Unit = {
        val data = frameData(currFrame)
        setPos(data._1, data._2)
        children.foreach(_.loadFrameData())
    }

    def saveFrameData(): Unit = {
        frameData(currFrame) = (centerX.toDouble, centerY.toDouble)
        children.foreach(_.saveFrameData())
    }

    def setDataEqual(start: Frame, end: Frame): Unit = {
        var frame = end

        while (frame != start) {
            frameData(frame) = frameData(start)
            frame = frame.previous.get
        }
        children.foreach(_.setDataEqual(start, end))
    }

    def interpolate(start: Frame, end: Frame, length: Int): Unit = {
        var idx = 0

        var frame = end

        while(frame != start) {
            frameData(frame) = (
                frameData(end)._1 + (frameData(start)._1 - frameData(end)._1) * idx / length,
                    frameData(end)._2 + (frameData(start)._2 - frameData(end)._2) * idx / length
                )
            idx += 1
            frame = frame.previous.get
        }
        children.foreach(_.interpolate(start, end, length))
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

        if (!currFrame.isKeyFrame) {
            currFrame.toggleKeyFrame()
        }
    }

    def read(lines: Array[String]): Array[String] = {
        var left = lines.tail

        children.foreach(n => {
            left = n.read(left)
        })
        left
    }

    def write(file: PrintWriter): Unit = {
        file.write("Figure\n")
        file.write(structureName + "\n")
        children.foreach(_.write(file))
    }

    def update(): Unit = {
        children.foreach(_.update())
    }
    loadStructure(structureName)
}