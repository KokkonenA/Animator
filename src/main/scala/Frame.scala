import scala.collection.mutable.ArrayBuffer

class Frame {
    val figures = ArrayBuffer [Figure] ()
    val speechBubbles = ArrayBuffer [SpeechBubble] ()
    private var background = "basic"

    def getBackground = this.background

    def setBackground(newBg: String): Unit = {
        this.background = newBg
    }

    def getCopy = {
        val copy = new Frame
        copy.background = this.background
        this.figures.foreach(copy.figures += _.getCopy)
        this.speechBubbles.foreach(copy.speechBubbles += _.getCopy)
        copy
    }

    def loadJoints = {
        (ArrayBuffer [Joint] (), None: Option[Head])
    }

    def addFigure = {
        val structure = this.loadJoints
        val newPos = new Pos(Animator.viewerW / 2, Animator.viewerH / 2)
        val newJoints = structure._1
        val newHead = structure._2

        this.figures += new Figure(newPos, newJoints, newHead)
        true
    }

    def addSpeech = true
}