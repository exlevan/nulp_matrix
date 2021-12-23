package xyz.exlevan.matrix.controller

import xyz.exlevan.matrix.model.{Dimensions, Matrix}

/* Perform various operations on matrix.
 * Matrix is changed in-place where possible.
 */
object MatrixCalculator {

  def multiplyByScalar(mx: Matrix, x: Double): Unit = {
    mx.dimensions().foreach { i => j =>
      mx.values(i)(j) *= x
    }
  }

  def divideByScalar(mx: Matrix, x: Double): Unit = {
    mx.dimensions().foreach { i => j =>
      mx.values(i)(j) /= x
    }
  }

  def addMatrices(mx1: Matrix, mx2: Matrix): Unit = {
    mx1.dimensions().foreach { i => j =>
      mx1.values(i)(j) += mx2.values(i)(j)
    }
  }

  def subtractMatrices(mx1: Matrix, mx2: Matrix): Unit = {
    mx1.dimensions().foreach { i => j =>
      mx1.values(i)(j) -= mx2.values(i)(j)
    }
  }

  def multiplyMatrices(mx1: Matrix, mx2: Matrix): Matrix = {
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
    Matrix(dim3, mx3).toOption.get
  }

  def calcDeterminant(
      values: Array[Array[Double]],
      i: Int = 0,
      skipJ: Set[Int] = Set.empty
  ): Double = {
    val n = values.length
    val js = 0.until(n).filterNot(skipJ.contains).toList
    if (i == n - 1) {
      val (j :: Nil): List[Int] @unchecked = js
      values(i)(j)
    } else {
      js.zipWithIndex.map { case (j, mappedJ) =>
        val sign = if ((i + mappedJ) % 2 == 0) 1.0 else -1.0
        sign * values(i)(j) * calcDeterminant(values, i + 1, skipJ + j)
      }.sum
    }
  }

}
