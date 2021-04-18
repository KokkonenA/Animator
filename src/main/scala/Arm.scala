import scalafx.scene.shape.Line

class Arm(val parentCP: Joint) extends Line with ChildFrameComponent {
    def updatePos(): Unit = {
        this.startX = this.parentCP.centerX.toDouble
        this.startY = this.parentCP.centerY.toDouble

        this.endX = this.parentCP.parentCP.centerX.toDouble
        this.endY = this.parentCP.parentCP.centerY.toDouble
    }

    def addFrame(): Unit = {}
    def deleteFrame(): Unit = {}
    def updateFrame(): Unit = {}
    def updateFrameData(): Unit = {}
}