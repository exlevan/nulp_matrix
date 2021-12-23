package xyz.exlevan.matrix.view

import xyz.exlevan.matrix.model.{Dimensions, Matrix}

import java.awt.Insets
import javax.swing.{BorderFactory, JOptionPane}
import scala.swing.*
import scala.swing.Dialog.{Message, Options, Result}
import scala.swing.GridBagPanel.Anchor
import scala.swing.Swing.*
import scala.util.Try
import scala.util.chaining.scalaUtilChainingOps

class MainView extends BoxPanel(Orientation.Vertical) {

  def inputDimensions(
      rows: Option[Int] = None,
      columns: Option[Int] = None
  ): Option[Dimensions] = {

    val textFieldRows = new TextField(3)
    rows.foreach { x =>
      textFieldRows.text = x.toString
      textFieldRows.enabled = false
    }

    val textFieldColumns = new TextField(3)
    columns.foreach { x =>
      textFieldColumns.text = x.toString
      textFieldColumns.enabled = false
    }

    val dialogContents: GridBagPanel = new GridBagPanel {

      def addComponent(
          component: Component
      )(modifyConstraints: Constraints => Unit): Unit = {
        val c = new Constraints
        modifyConstraints(c)
        layout(component) = c
      }

      addComponent(new Label("Кількість рядків n:")) { c =>
        c.grid = (1, 1)
        c.anchor = Anchor.West
        c.insets = new Insets(0, 0, 0, 10)
      }

      addComponent(textFieldRows) { c =>
        c.grid = (2, 1)
        c.anchor = Anchor.West
      }

      addComponent(new Label("Кількість стовпців m:")) { c =>
        c.grid = (1, 2)
        c.anchor = Anchor.West
        c.insets = new Insets(0, 0, 0, 10)
      }

      addComponent(textFieldColumns) { c =>
        c.grid = (2, 2)
        c.anchor = Anchor.West
      }
    }

    val pressedButton =
      JOptionPane.showOptionDialog(
        null,
        dialogContents.peer,
        "Введіть розміри матриці",
        Options.OkCancel.id,
        Message.Question.id,
        Swing.wrapIcon(EmptyIcon),
        null,
        null
      )

    Result(pressedButton) match {
      case Result.Ok =>
        val dimensions = for {
          rows <- textFieldRows.text.trim.toIntOption.filter(x =>
            0 < x && x < 20
          )
          columns <- textFieldColumns.text.trim.toIntOption.filter(x =>
            0 < x && x < 20
          )
        } yield Dimensions(rows = rows, columns = columns)

        dimensions.tap(x =>
          if (x.isEmpty) showError("Помилка зчитування даних")
        )
      case _ => None
    }
  }

  def inputMatrix(dimensions: Dimensions): Option[Matrix] = {

    val textFields = Array.fill(dimensions.rows)(
      Array.fill(dimensions.columns)(
        new TextField(3)
      )
    )

    val dialogContents: GridBagPanel = new GridBagPanel {

      def addComponent(
          component: Component
      )(modifyConstraints: Constraints => Unit): Unit = {
        val c = new Constraints
        modifyConstraints(c)
        layout(component) = c
      }

      0.until(dimensions.rows).foreach { i =>
        0.until(dimensions.columns).foreach { j =>
          addComponent(textFields(i)(j)) { c =>
            c.grid = (j, i)
            c.insets = new Insets(2, 5, 2, 5)
          }
        }
      }
    }

    val pressedButton =
      JOptionPane.showOptionDialog(
        null,
        dialogContents.peer,
        "Введіть матрицю",
        Options.OkCancel.id,
        Message.Question.id,
        Swing.wrapIcon(EmptyIcon),
        null,
        null
      )

    Result(pressedButton) match {
      case Result.Ok =>
        Try {
          val matrix = new Array[Array[Double]](dimensions.rows)
          0.until(dimensions.rows).foreach { i =>
            matrix(i) = new Array[Double](dimensions.columns)
            0.until(dimensions.columns).foreach { j =>
              val number = textFields(i)(j).text.trim.toDouble
              if (number.isInfinite || number.isNaN) {
                sys.error("Неприпустиме значення")
              }
              matrix(i)(j) = number
            }
          }
          Matrix(dimensions, matrix).toOption
        }.toOption.flatten.tap(x =>
          if (x.isEmpty) showError("Помилка зчитування даних")
        )
      case _ => None
    }
  }

  def inputScalar(): Option[Double] = ???

  def showScalar(scalar: Double): Unit = ???

  def showMatrix(matrix: Matrix): Unit = {

    val dimensions = matrix.dimensions()

    val dialogContents: GridBagPanel = new GridBagPanel {

      def addComponent(
          component: Component
      )(modifyConstraints: Constraints => Unit): Unit = {
        val c = new Constraints
        modifyConstraints(c)
        layout(component) = c
      }

      0.until(dimensions.rows).foreach { i =>
        0.until(dimensions.columns).foreach { j =>
          val textField = new TextField(matrix.values(i)(j).toString, 3)
          textField.enabled = false
          addComponent(textField) { c =>
            c.grid = (j, i)
            c.insets = new Insets(2, 5, 2, 5)
          }
        }
      }
    }

    val _ =
      JOptionPane.showOptionDialog(
        null,
        dialogContents.peer,
        "Матриця",
        Options.Default.id,
        Message.Info.id,
        Swing.wrapIcon(EmptyIcon),
        null,
        null
      )

    ()
  }

  def showError(error: String): Unit =
    JOptionPane.showOptionDialog(
      null,
      error,
      "Виникла помилка",
      Options.Default.id,
      Message.Error.id,
      Swing.wrapIcon(EmptyIcon),
      null,
      null
    )

  val operationsView = new OperationsView
  val panel = new BoxPanel(Orientation.NoOrientation) {
    contents += operationsView
  }
  panel.border = BorderFactory.createEmptyBorder(0, 15, 0, 15)

  contents += panel
}
