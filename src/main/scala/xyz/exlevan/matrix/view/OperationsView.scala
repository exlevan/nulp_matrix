package xyz.exlevan.matrix.view

import xyz.exlevan.matrix.Matrix.controller

import scala.swing.*
import scala.swing.Swing.*

class OperationsView extends BoxPanel(Orientation.Vertical) {

  val buttons: List[(String, () => Unit)] = List(
    "Ввести матрицю" -> controller.enterMatrixAction _,
    "Показати матрицю" -> controller.showMatrixAction _,
    "Помножити матрицю на скаляр" -> controller.multiplyByScalarAction _,
    "Поділити матрицю на скаляр" -> controller.divideByScalarAction _,
    "Помножити матрицю на матрицю" -> controller.multiplyByMatrixAction _,
    "Розрахувати детермінант" -> controller.calculateDeterminantAction _
  )

  contents += VStrut(10)
  contents ++= buttons.flatMap { case (label, action) =>
    val button = Button(label)(action())
    button.maximumSize = (Int.MaxValue, Int.MaxValue)
    button.border = Swing.EmptyBorder(20, 50, 20, 50)
    button.font = Font("SansSerif", Font.BoldItalic, 20)
    List(button, VStrut(10))
  }
}
