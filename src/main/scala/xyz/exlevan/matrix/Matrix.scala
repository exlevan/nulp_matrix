package xyz.exlevan.matrix

import xyz.exlevan.matrix.controller.MatrixController

import scala.swing.{Frame, MainFrame, SimpleSwingApplication}
import xyz.exlevan.matrix.view.MainView

import javax.swing.UIManager
import scala.util.Try

object Matrix extends SimpleSwingApplication {

  Try(
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
  ).orElse {
    Try(UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName))
  }.getOrElse(())

  val controller = new MatrixController
  val view = new MainView

  lazy val frame: MainFrame = new MainFrame {
    title = "Операції над матрицями"
    contents = view
  }

  def top: Frame = {
    frame.centerOnScreen()
    frame
  }
}
