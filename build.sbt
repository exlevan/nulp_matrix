import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "xyz.exlevan"
ThisBuild / organizationName := "Oleksii Levan"
ThisBuild / scalacOptions := List(
  "-encoding",
  "UTF-8",
  "-deprecation",
  "-unchecked",
  "-Xsource:3",
  "-Xlint",
  "-Ywarn-value-discard"
)

lazy val root = (project in file("."))
  .settings(
    name := "matrix",
    libraryDependencies ++= List(
      compilerPlugin(betterMonadicFor),
      scalaSwing,
      scalaTest % Test
    )
  )
