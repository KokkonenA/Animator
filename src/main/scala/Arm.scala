import scalafx.scene.shape.Line

class Arm(val parentCP: Joint) extends Line with ChildFrameComponent {
    def update(): Unit = {
        startX = parentCP.centerX.toDouble
        startY = parentCP.centerY.toDouble

        endX = parentCP.parentCP.centerX.toDouble
        endY = parentCP.parentCP.centerY.toDouble
    }

    def addFrame(): Unit = {}
    def deleteFrame(): Unit = {}
    def loadFrameData(): Unit = {}
    def saveFrameData(): Unit = {}
}