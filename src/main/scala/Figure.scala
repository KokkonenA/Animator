class Figure (private var pos: Pos, val joints: Array[Joint], val head: Option[Head]) {
  def getPos = this.pos

  def moveJoint(moving: Joint, newPos: Pos): Unit = {
    moving.setPos(newPos)
  }
}
