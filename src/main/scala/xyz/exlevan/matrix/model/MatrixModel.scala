package xyz.exlevan.matrix.model

case class Dimensions(rows: Int, columns: Int) {

  def foreach(f: Int => Int => Unit): Unit =
    0.until(rows).foreach { i =>
      val g = f(i)
      0.until(columns).foreach { j =>
        g(j)
      }
    }
}

case class Matrix private (values: Array[Array[Double]]) {

  def dimensions(): Dimensions = Dimensions(values.length, values(0).length)

  override def equals(obj: Any): Boolean = obj match {
    case mx: Matrix =>
      values.length == mx.values.length &&
        values.indices.forall { i =>
          values(i).length == mx.values(i).length &&
          values(i).indices.forall { j =>
            values(i)(j) == mx.values(i)(j)
          }
        }
    case _ => false
  }
}

object Matrix {

  def apply(
      dims: Dimensions,
      values: Array[Array[Double]]
  ): Either[String, Matrix] = {

    if (dims.rows <= 0 || dims.columns <= 0) {
      Left("Неприпустима розмірність матриці")
    } else if (values.length != dims.rows) {
      Left("Невірна кількість рядків матриці")
    } else if (values.exists(_.length != dims.columns)) {
      Left("Невірна кількість стовпців матриці")
    } else {
      Right(Matrix(values))
    }
  }
}
