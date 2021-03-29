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

    def addFigure = ???

    def addSpeech = ???
}
