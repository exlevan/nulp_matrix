package xyz.exlevan.matrix

import xyz.exlevan.matrix.controller.MatrixController

import scala.swing.{Frame, MainFrame, SimpleSwingApplication}
import xyz.exlevan.matrix.view.MainView

import javax.swing.UIManager
import scala.util.Try

object Matrix extends SimpleSwingApplication {

  val controller = new MatrixController

  Try(
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
  ).orElse {
    Try(UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName))
  }.getOrElse(())

  lazy val frame: MainFrame = new MainFrame {
    title = "Операції над матрицями"
    contents = new MainView(controller)
  }

  def top: Frame = {
    frame.centerOnScreen()
    frame
  }
}
