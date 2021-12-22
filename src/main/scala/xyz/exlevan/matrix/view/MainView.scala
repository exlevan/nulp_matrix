package xyz.exlevan.matrix.view

import xyz.exlevan.matrix.controller.MatrixController
import xyz.exlevan.matrix.model.{Dimensions, Matrix}

import javax.swing.BorderFactory
import scala.swing.*

class MainView(controller: MatrixController)
    extends BoxPanel(Orientation.Vertical) {

  def inputDimensions(
      rows: Option[Int] = None,
      columns: Option[Int] = None
  ): Option[Dimensions] = ???

  def inputMatrix(dimensions: Dimensions): Option[Matrix] = ???

  def inputScalar(): Option[Double] = ???

  def showScalar(scalar: Double): Unit = ???

  def showMatrix(matrix: Matrix): Unit = ???

  def showError(error: String): Unit = ???

  val operationsView = new OperationsView(controller)
  val panel = new BoxPanel(Orientation.NoOrientation) {
    contents += operationsView
  }
  panel.border = BorderFactory.createEmptyBorder(0, 15, 0, 15)

  contents += panel
}
