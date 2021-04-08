import scalafx.scene.image.Image
import scala.collection.mutable.ArrayBuffer
import scalafx.Includes._

class Frame {
    val figures = ArrayBuffer [Figure] ()
    val speechBubbles = ArrayBuffer [SpeechBubble] ()

    private var background = {
        new Image("file:basic.png")
    }

    def getBackground = this.background

    def setBackground(newBg: String): Unit = {
        this.background = new Image(newBg)
    }

    def addAll(): Unit = {
        this.figures.foreach(_.joints.foreach(n => {
            Viewer.children += n
            if (n.hasParent) Viewer.children += n.arm.get
        }))
        this.figures.foreach {
            n => if (n.head.isDefined) Viewer.children += n.head.get
        }
        this.speechBubbles.foreach(Viewer.children += _)
    }

    def addFigure(): Unit = {
        val newFigure = new Figure
        this.figures += newFigure
    }

    def addSpeech(): Unit = {
        val newBubble = new SpeechBubble
        this.speechBubbles += newBubble
    }

    def getCopy = {
        val copy = new Frame
        copy.background = this.background
        this.figures.foreach(copy.figures += _.getCopy)
        this.speechBubbles.foreach(copy.speechBubbles += _.getCopy)
        copy
    }

    def update(): Unit = {
        this.figures.foreach(_.update())
        this.speechBubbles.foreach(_.update())
    }
}