import Dependencies._
import sbt.Keys.resolvers
import sbt.Resolver

ThisBuild / scalaVersion := "2.13.7"
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
ThisBuild / versionScheme := Some("semver-spec")

githubTokenSource := TokenSource.Environment("GITHUB_TOKEN") ||
  TokenSource.GitConfig("github.token")
resolvers += Resolver.githubPackages("exlevan")
githubOwner := "exlevan"
githubRepository := "nulp_matrix"

lazy val root = (project in file("."))
  .settings(
    name := "matrix",
    libraryDependencies ++= List(
      compilerPlugin(betterMonadicFor),
      scalaSwing,
      scalaTest % Test,
      scalaMock % Test
    )
  )
