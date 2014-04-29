package org.callie.input

import java.awt.event.{MouseEvent, KeyEvent, MouseListener, KeyListener}

object Inputs extends KeyListener with MouseListener{

  def hasKey(k:Int) = lockKb.synchronized{ keys.contains(k) }
  def hasButton(b:Int) = lockMb.synchronized{ buttons.companion(b) }

  def key() = lockKe.synchronized{
    keyEvents match {
      case h :: t =>
        keyEvents = t
        Some(h)
      case Nil => None
    }
  }

  def button() = lockMe.synchronized{
    mouseEvents match {
      case h :: t =>
        mouseEvents = t
        Some(h)
      case Nil => None
    }
  }

  // impl

  var lockMb = new Object
  var lockMe = new Object

  var buttons = Set[Int]()
  var mouseEvents = List[Int]()

  // mouse

  override def mouseReleased(e: MouseEvent){
    lockMb.synchronized{
      buttons = buttons - e.getButton
    }
  }

  override def mousePressed(e: MouseEvent){
    lockMb.synchronized {
      buttons = buttons + e.getButton
    }
  }

  override def mouseClicked(e: MouseEvent){
    lockMe.synchronized {
      mouseEvents = mouseEvents :+ e.getButton
    }
  }

  var lockKb = new Object
  var lockKe = new Object

  var keys = Set[Int]()
  var keyEvents = List[Int]()

  // key
  override def keyReleased(e: KeyEvent){
    lockKb.synchronized {
      keys = keys - e.getKeyCode
    }
  }

  override def keyPressed(e: KeyEvent){
    lockKb.synchronized {
      keys = keys + e.getKeyCode
    }
  }

  override def keyTyped(e: KeyEvent){
    lockKe.synchronized {
      keyEvents = keyEvents :+ e.getKeyCode
    }
  }

  // none
  override def mouseExited(e: MouseEvent){}
  override def mouseEntered(e: MouseEvent){}
}
