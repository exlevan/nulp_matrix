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

  private def withDefinedMatrix(f: Matrix => Unit): Unit = _matrix match {
    case None => view.showError("Матриця не задана")
    case Some(mx) => f(mx)
  }

  def showMatrixAction(): Unit = withDefinedMatrix(view.showMatrix)

  def multiplyByScalarAction(): Unit =
    withDefinedMatrix { mx =>
      view.inputScalar().foreach { x =>
        mx.dimensions().foreach { i => j =>
          mx.values(i)(j) *= x
        }
        view.showMatrix(mx)
      }
    }

  def divideByScalarAction(): Unit =
    withDefinedMatrix { mx =>
      view.inputScalar().foreach { x =>
        mx.dimensions().foreach { i => j =>
          mx.values(i)(j) /= x
        }
        view.showMatrix(mx)
      }
    }

  def addMatrixAction(): Unit =
    withDefinedMatrix { mx1 =>
      view.inputMatrix(mx1.dimensions()).foreach { mx2 =>
        mx1.dimensions().foreach { i => j =>
          mx1.values(i)(j) += mx2.values(i)(j)
        }
      }
      view.showMatrix(mx1)
    }

  def subtractMatrixAction(): Unit =
    withDefinedMatrix { mx1 =>
      view.inputMatrix(mx1.dimensions()).foreach { mx2 =>
        mx1.dimensions().foreach { i => j =>
          mx1.values(i)(j) -= mx2.values(i)(j)
        }
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
        val dim3 = Dimensions(
          rows = mx1.dimensions().rows,
          columns = mx2.dimensions().columns
        )
        val mx3 = new Array[Array[Double]](dim3.rows)
        dim3.foreach { i =>
          mx3(i) = new Array[Double](dim3.columns)

          { j =>
            mx3(i)(j) = 0
              .until(mx1.dimensions().columns)
              .map { k =>
                mx1.values(i)(k) * mx2.values(k)(j)
              }
              .sum
          }
        }
        val mx = Matrix(dim3, mx3).toOption.get
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
        val det = calcDeterminant(mx.values, 0, Set.empty)
        view.showDeterminant(det)
      }
    }

  def calcDeterminant(
      values: Array[Array[Double]],
      i: Int,
      skipJ: Set[Int]
  ): Double = {
    val n = values.length
    val js = 0.until(n).filterNot(skipJ.contains).toList
    if (i == n - 1) {
      val j :: Nil = js
      values(i)(j)
    } else {
      js.zipWithIndex.map { case (j, mappedJ) =>
        val sign = if ((i + mappedJ) % 2 == 0) 1.0 else -1.0
        sign * values(i)(j) * calcDeterminant(values, i + 1, skipJ + j)
      }.sum
    }
  }
}
