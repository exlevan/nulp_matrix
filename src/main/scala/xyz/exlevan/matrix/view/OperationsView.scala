package xyz.exlevan.matrix.view

import xyz.exlevan.matrix.controller.MatrixController

import javax.swing.{BorderFactory, Box, JComponent}
import scala.swing.*
import scala.swing.Dimension as SwDimension

class OperationsView(controller: MatrixController)
    extends BoxPanel(Orientation.Vertical) {

  val buttons: List[(String, () => Unit)] = List(
    "Ввести матрицю" -> controller.enterMatrix _,
    "Показати матрицю" -> controller.showMatrix _,
    "Помножити матрицю на скаляр" -> controller.multiplyByScalar _,
    "Поділити матрицю на скаляр" -> controller.divideByScalar _,
    "Помножити матрицю на матрицю" -> controller.multiplyByMatrix _,
    "Розрахувати детермінант" -> controller.calculateDeterminant _
  )

  def strut(height: Int): Component =
    Component.wrap(Box.createVerticalStrut(height).asInstanceOf[JComponent])

  contents += strut(10)
  contents ++= buttons.flatMap { case (label, action) =>
    val button = Button(label)(action())
    button.maximumSize = new SwDimension(Short.MaxValue, Short.MaxValue)
    button.border = BorderFactory.createEmptyBorder(20, 50, 20, 50)
    button.font = Font("SansSerif", Font.BoldItalic, 20)
    List(button, strut(10))
  }
}
