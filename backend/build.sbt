name := """backend"""
organization := "vdjaramillog"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "io.vavr" % "vavr-jackson" % "0.9.3"
libraryDependencies += "com.oracle.ojdbc" % "ojdbc8" % "19.3.0.0"
libraryDependencies += "org.json" % "json" % "20190722"