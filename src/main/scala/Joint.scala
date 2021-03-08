class Joint(val parent: Joint, val radius: Int, private var angle: Int, private var pos: Pos) {
  private var locked = false

  def getLocked = this.locked
  def getAngle = this.angle

  def toggleLocked(): Unit = {
    if (this.locked) this.locked = false
    else this.locked = true
  }

  def move(newPos: Pos): Unit = {
    this.pos = newPos
  }

  def setAngle(newAngle: Int): Unit = {
    this.angle = newAngle
  }
}