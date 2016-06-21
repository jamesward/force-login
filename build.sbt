name := "force-login"

scalaVersion := "2.11.8"

libraryDependencies += "com.force.api" % "force-partner-api" % "36.0.0"

lazy val default = inputKey[Unit]("default")

default := (run in Compile).evaluated
