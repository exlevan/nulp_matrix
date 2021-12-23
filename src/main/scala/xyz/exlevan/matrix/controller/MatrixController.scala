package xyz.exlevan.matrix.controller

import xyz.exlevan.matrix.model.Matrix
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

  private def withDefinedMatrix(f: Matrix => Unit): Unit = _matrix match {
    case None => view.showError("Матриця не задана")
    case Some(mx) => f(mx)
  }

  def showMatrixAction(): Unit = withDefinedMatrix(view.showMatrix)

  def multiplyByScalarAction(): Unit =
    withDefinedMatrix { mx =>
      view.inputScalar().foreach { x =>
        MatrixCalculator.multiplyByScalar(mx, x)
        view.showMatrix(mx)
      }
    }

  def divideByScalarAction(): Unit =
    withDefinedMatrix { mx =>
      view.inputScalar().foreach { x =>
        MatrixCalculator.divideByScalar(mx, x)
        view.showMatrix(mx)
      }
    }

  def addMatrixAction(): Unit =
    withDefinedMatrix { mx1 =>
      view.inputMatrix(mx1.dimensions()).foreach { mx2 =>
        MatrixCalculator.addMatrices(mx1, mx2)
      }
      view.showMatrix(mx1)
    }

  def subtractMatrixAction(): Unit =
    withDefinedMatrix { mx1 =>
      view.inputMatrix(mx1.dimensions()).foreach { mx2 =>
        MatrixCalculator.subtractMatrices(mx1, mx2)
      }
      view.showMatrix(mx1)
    }

  def multiplyByMatrixAction(): Unit =
    withDefinedMatrix { mx1 =>
      val kmax = mx1.dimensions().columns
      val input = for {
        dim2 <- view.inputDimensions(rows = Some(kmax))
        mx2 <- view.inputMatrix(dim2)
      } yield mx2

      input.foreach { mx2 =>
        val mx = MatrixCalculator.multiplyMatrices(mx1, mx2)
        _matrix = Some(mx)
        view.showMatrix(mx)
      }
    }

  def calculateDeterminantAction(): Unit =
    withDefinedMatrix { mx =>
      val dim = mx.dimensions()
      if (dim.rows != dim.columns) {
        view.showError(
          "Детермінант можливо обчислити\nлише для квадратної матриці"
        )
      } else {
        val det = MatrixCalculator.calcDeterminant(mx.values)
        view.showDeterminant(det)
      }
    }

}
