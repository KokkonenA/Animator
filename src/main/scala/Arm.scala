import scalafx.scene.shape.Line

class Arm(val parentCP: Joint) extends Line {
    def update(): Unit = {
        startX = parentCP.centerX.toDouble
        startY = parentCP.centerY.toDouble

        endX = parentCP.parentCP.centerX.toDouble
        endY = parentCP.parentCP.centerY.toDouble
    }
    Viewer.children.add(this)
}