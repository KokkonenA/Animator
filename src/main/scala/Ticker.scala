// JavaFX AnimationTimer is extended directly.
// Trying to extend ScalaFX AnimationTimer gives error about needing JavaFX AnimationTimer as a delegete.
import javafx.animation.AnimationTimer

//This class calls function given as a parameter repeatedly.
class Ticker(function: () => Unit) extends AnimationTimer {

    //Override from animation timer
    override def handle(now: Long): Unit = {function()}

}