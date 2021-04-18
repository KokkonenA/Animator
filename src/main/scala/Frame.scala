import scalafx.scene.shape.Line

class Frame extends Line with TimelineComponent {
    startY = Timeline.line.startY.toDouble - 20
    endY = Timeline.line.endY.toDouble + 20

    var keyFrame = false

    def isKeyFrame = keyFrame

    def toggleKeyFrame() = {
        if (keyFrame) keyFrame = false
        else keyFrame = true
    }
}