import scalafx.scene.shape.Line

class Frame (val previous: Option[Frame]) extends Line with TimelineComponent {
    private var timeline = Timeline.line
    startY = timeline.startY.toDouble - 20
    endY = timeline.endY.toDouble + 20
    startX = timeline.startX.toDouble
    endX = startX.toDouble

    var keyFrame = false

    def isKeyFrame = keyFrame

    def toggleKeyFrame() = {
        if (keyFrame) keyFrame = false
        else keyFrame = true
    }

    def update(): Unit = {
        if (previous.isDefined) {
            startX =
                previous.get.startX.toDouble + (timeline.endX.toDouble - timeline.startX.toDouble) / Animator.frameCount
            endX = startX.toDouble
        }
    }
}