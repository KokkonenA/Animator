import scalafx.scene.shape.Line

class Frame (val previous: Option[Frame]) extends Line with TimelineComponent {
    private var timeline = Timeline.line

    startY = timeline.startY.toDouble - 20
    endY = timeline.endY.toDouble + 20
    startX = timeline.startX.toDouble
    endX = startX.toDouble

    private var key: Option[Key] = None

    def isKeyFrame = key.isDefined

    def toggleKeyFrame() = {
        if (isKeyFrame) {
            key.get.remove()
            key = None
        }
        else key = Some(new Key(this))
    }

    def update(): Unit = {
        if (previous.isDefined) {
            startX =
                previous.get.startX.toDouble + (timeline.endX.toDouble - timeline.startX.toDouble) / Animator.frameCount
            endX = startX.toDouble
        }
        if (isKeyFrame) {
            key.get.update()
        }
    }

    override def remove(): Unit = {
        Timeline.children.remove(this)
        if (isKeyFrame) Timeline.children.remove(key.get)
    }
}