import scalafx.scene.image.Image
import scala.collection.mutable.ArrayBuffer

class Frame {
    val figures = ArrayBuffer [Figure] ()
    val speechBubbles = ArrayBuffer [SpeechBubble] ()

    private var background = {
        new Image("file:basic.png")
    }

    def cpoints = {
        val resArray = Array [ControlPoint] ()
        figures.foreach(n => (resArray +: n.cpoints) :+ n.cpoint)
        speechBubbles.foreach(resArray :+ _.cpoint)
        resArray
    }

    def getBackground = this.background

    def setBackground(newBg: Image): Unit = {
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

    def update(): Unit = {
        this.figures.foreach(_.update())
        this.speechBubbles.foreach(_.update())
    }
}