package org.callie;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.*;

public class MouseScroller extends JFrame {

  public MouseScroller() {
    super("Mouse Scroller");
    addMouseWheelListener(new MouseWheelListener() {
      public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println(e);
      }
    });
  }

  public static void main(String[] args) {
    MouseScroller ms = new MouseScroller();
    ms.setVisible(true);
  }
}