package xyz.exlevan.matrix.controller

import xyz.exlevan.matrix.model.{Dimensions, Matrix}

class MatrixController {

  private var state: MatrixControllerState = MatrixControllerState(
    dimensions1 = None,
    matrix1 = None,
    dimensions2 = None,
    matrix2 = None
  )

  def enterMatrix(): Unit = ???

  def showMatrix(): Unit = ???

  def multiplyByScalar(): Unit = ???

  def divideByScalar(): Unit = ???

  def addMatrix(): Unit = ???

  def subtractMatrix(): Unit = ???

  def multiplyByMatrix(): Unit = ???

  def calculateDeterminant(): Unit = ???
}

case class MatrixControllerState(
    dimensions1: Option[Dimensions],
    matrix1: Option[Matrix],
    dimensions2: Option[Dimensions],
    matrix2: Option[Matrix]
)
