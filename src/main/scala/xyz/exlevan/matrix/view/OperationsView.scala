package xyz.exlevan.matrix.view

import xyz.exlevan.matrix.Matrix.controller
import xyz.exlevan.matrix.model.Dimensions

import java.awt.Insets
import scala.swing.*
import scala.swing.GridBagPanel.Fill
import scala.swing.Swing.*

class OperationsView extends GridBagPanel {

  def addComponent(
      component: Component
  )(modifyConstraints: Constraints => Unit): Unit = {
    val c = new Constraints
    modifyConstraints(c)
    layout(component) = c
  }

  val buttons: Array[Array[(String, () => Unit)]] = Array(
    Array(
      "Ввести матрицю" -> controller.enterMatrixAction _,
      "Показати матрицю" -> controller.showMatrixAction _,
      "Помножити матрицю на скаляр" -> controller.multiplyByScalarAction _,
      "Поділити матрицю на скаляр" -> controller.divideByScalarAction _
    ),
    Array(
      "Додати матрицю" -> controller.addMatrixAction _,
      "Відняти матрицю" -> controller.subtractMatrixAction _,
      "Помножити матрицю на матрицю" -> controller.multiplyByMatrixAction _,
      "Розрахувати детермінант" -> controller.calculateDeterminantAction _
    )
  )

  Dimensions(2, 4).foreach { i => j =>
    val (label, action) = buttons(i)(j)

    val button = Button(label)(action())
    button.maximumSize = (Int.MaxValue, Int.MaxValue)
    button.border = Swing.EmptyBorder(20, 50, 20, 50)
    button.font = Font("SansSerif", Font.BoldItalic, 20)

    addComponent(button) { c =>
      c.grid = (i, j)
      c.insets = new Insets(2, 5, 2, 5)
      c.fill = Fill.Both
    }
  }
}
