import scalafx.scene.shape.Line

class Frame extends Line with TimelineComponent {
    this.startY = Timeline.line.startY.toDouble - 20
    this.endY = Timeline.line.endY.toDouble + 20

    var keyFrame = false

    def isKeyFrame = this.keyFrame

    def toggleKeyFrame() = {
        if (this.keyFrame) this.keyFrame = false
        else this.keyFrame = true
    }
}