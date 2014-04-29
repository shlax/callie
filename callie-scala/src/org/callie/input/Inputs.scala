package org.callie.input

import java.awt.event.{MouseEvent, KeyEvent, MouseListener, KeyListener}

object Inputs extends KeyListener with MouseListener{
  // mouse
  override def mouseExited(e: MouseEvent){}

  override def mouseEntered(e: MouseEvent){}

  override def mouseReleased(e: MouseEvent){}

  override def mousePressed(e: MouseEvent){}

  override def mouseClicked(e: MouseEvent){}

  // key
  override def keyReleased(e: KeyEvent){}

  override def keyPressed(e: KeyEvent){}

  override def keyTyped(e: KeyEvent){}
}
