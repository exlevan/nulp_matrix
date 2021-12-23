package xyz.exlevan.matrix.controller

import xyz.exlevan.matrix.model.{Dimensions, Matrix}
import xyz.exlevan.matrix.Matrix.view

class MatrixController {

  private var _matrix: Option[Matrix] = None

  def enterMatrixAction(): Unit = {

    val input = for {
      dim <- view.inputDimensions()
      mx <- view.inputMatrix(dim)
    } yield mx

    input.foreach { mx =>
      _matrix = Some(mx)
    }
  }

  def showMatrixAction(): Unit = _matrix match {
    case None     => view.showError("Матриця не задана")
    case Some(mx) => view.showMatrix(mx)
  }

  def multiplyByScalarAction(): Unit = ???

  def divideByScalarAction(): Unit = ???

  def addMatrixAction(): Unit = ???

  def subtractMatrixAction(): Unit = ???

  def multiplyByMatrixAction(): Unit = ???

  def calculateDeterminantAction(): Unit = ???
}
