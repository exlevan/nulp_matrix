import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "xyz.exlevan"
ThisBuild / organizationName := "Oleksii Levan"

lazy val root = (project in file("."))
  .settings(
    name := "matrix",
    libraryDependencies += scalaTest % Test
  )
