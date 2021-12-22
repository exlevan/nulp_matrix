package xyz.exlevan.matrix.model

case class Dimensions(rows: Int, columns: Int)

case class Matrix private (values: Array[Array[Double]])

object Matrix {

  def apply(
      dims: Dimensions,
      values: Array[Array[Double]]
  ): Either[String, Matrix] = {

    if (values.length != dims.rows) {
      Left("Невірна кількість рядків матриці")
    } else if (values.exists(_.length != dims.columns)) {
      Left("Невірна кількість стовпців матриці")
    } else {
      Right(Matrix(values))
    }
  }
}
