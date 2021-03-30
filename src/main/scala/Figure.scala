import scala.collection.mutable.ArrayBuffer

class Figure (private var pos: Pos, val joints: ArrayBuffer[Joint], val head: Option[Head]) {
  def draw(): Unit = {
    this.joints.foreach(_.draw())
  }

  def getPos = this.pos

  def moveJoint(moving: Joint, newPos: Pos): Unit = {
    moving.setPos(newPos)
  }

  def getCopy = {
    val copyPos = this.pos

    val copyJoints = ArrayBuffer[Joint] ()

    this.joints.foreach(copyJoints += _.getCopy(copyJoints))

    val copyHead = this.head match {
      case Some(head) => Some(head.getCopy(copyJoints))
      case None => None
    }

    new Figure(copyPos, copyJoints, copyHead)
  }
}
