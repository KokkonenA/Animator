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

    def addFigure = {
        val newPos = new Pos(Animator.viewerW / 2, Animator.viewerH / 2)

        this.figures += new Figure()
        true
    }

    def addSpeech = {
        val newPos = new Pos(Animator.viewerW / 2, Animator.viewerH / 2)
        val newSpeech = new SpeechBubble(newPos)
        this.speechBubbles += newSpeech
        true
    }

    def draw(): Unit = {
        this.figures.foreach(_.draw())
        this.speechBubbles.foreach(_.draw())
    }
}