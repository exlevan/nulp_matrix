package xyz.exlevan

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import xyz.exlevan.matrix.controller.MatrixCalculator
import xyz.exlevan.matrix.model.{Dimensions, Matrix}

class MatrixCalculatorSpec extends AnyFlatSpec with Matchers with MockFactory {

  private def mkMatrix(mx: Array[Array[Double]]): Matrix =
    Matrix(Dimensions(mx.length, mx(0).length), mx).toOption.get

  "MatrixCalculator" should "multiply a matrix by scalar" in {

    val mx1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.multiplyByScalar(mx1, 0)
    mx1 should equal(mkMatrix(Array(Array(0, 0), Array(0, 0))))

    val mx2 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.multiplyByScalar(mx2, 1)
    mx2 should equal(mkMatrix(Array(Array(1, 2), Array(3, 4))))

    val mx3 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.multiplyByScalar(mx3, 2)
    mx3 should equal(mkMatrix(Array(Array(2, 4), Array(6, 8))))

    val mx4 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.multiplyByScalar(mx4, 0.5)
    mx4 should equal(mkMatrix(Array(Array(0.5, 1), Array(1.5, 2))))
  }

  it should "divide a matrix by scalar" in {

    val mx1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.divideByScalar(mx1, 0)
    mx1 should equal(
      mkMatrix(
        Array(
          Array(Double.PositiveInfinity, Double.PositiveInfinity),
          Array(Double.PositiveInfinity, Double.PositiveInfinity)
        )
      )
    )

    val mx2 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.divideByScalar(mx2, 1)
    mx2 should equal(mkMatrix(Array(Array(1, 2), Array(3, 4))))

    val mx3 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.divideByScalar(mx3, 2)
    mx3 should equal(mkMatrix(Array(Array(0.5, 1), Array(1.5, 2))))

    val mx4 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    MatrixCalculator.divideByScalar(mx4, 0.5)
    mx4 should equal(mkMatrix(Array(Array(2, 4), Array(6, 8))))
  }

  it should "add two matrices" in {

    val mx1_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx1_2 = mkMatrix(Array(Array(5, 6), Array(7, 8)))
    MatrixCalculator.addMatrices(mx1_1, mx1_2)
    mx1_1 should equal(mkMatrix(Array(Array(6, 8), Array(10, 12))))

    val mx2_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx2_2 = mkMatrix(Array(Array(0, 0), Array(0, 0)))
    MatrixCalculator.addMatrices(mx2_1, mx2_2)
    mx2_1 should equal(mkMatrix(Array(Array(1, 2), Array(3, 4))))

    val mx3_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx3_2 = mkMatrix(Array(Array(1, 1), Array(1, 1)))
    MatrixCalculator.addMatrices(mx3_1, mx3_2)
    mx3_1 should equal(mkMatrix(Array(Array(2, 3), Array(4, 5))))

    val mx4_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx4_2 = mkMatrix(Array(Array(-1, -1), Array(-1, -1)))
    MatrixCalculator.addMatrices(mx4_1, mx4_2)
    mx4_1 should equal(mkMatrix(Array(Array(0, 1), Array(2, 3))))
  }

  it should "subtract two matrices" in {

    val mx1_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx1_2 = mkMatrix(Array(Array(5, 6), Array(7, 8)))
    MatrixCalculator.subtractMatrices(mx1_1, mx1_2)
    mx1_1 should equal(mkMatrix(Array(Array(-4, -4), Array(-4, -4))))

    val mx2_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx2_2 = mkMatrix(Array(Array(0, 0), Array(0, 0)))
    MatrixCalculator.subtractMatrices(mx2_1, mx2_2)
    mx2_1 should equal(mkMatrix(Array(Array(1, 2), Array(3, 4))))

    val mx3_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx3_2 = mkMatrix(Array(Array(1, 1), Array(1, 1)))
    MatrixCalculator.subtractMatrices(mx3_1, mx3_2)
    mx3_1 should equal(mkMatrix(Array(Array(0, 1), Array(2, 3))))

    val mx4_1 = mkMatrix(Array(Array(1, 2), Array(3, 4)))
    val mx4_2 = mkMatrix(Array(Array(-1, -1), Array(-1, -1)))
    MatrixCalculator.subtractMatrices(mx4_1, mx4_2)
    mx4_1 should equal(mkMatrix(Array(Array(2, 3), Array(4, 5))))
  }
}
