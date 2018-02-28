name := "callie-scala"

version := "1.0"

scalaVersion := "2.12.4"

// https://github.com/sbt/sbt/issues/1821
//resolvers += "nexus releases" at "http://mr01.hq.gratex.com/nexus/content/groups/public/"

libraryDependencies += "org.jogamp.gluegen" % "gluegen-rt-main" % "2.3.2"
libraryDependencies += "org.jogamp.jogl" % "jogl-all-main" % "2.3.2"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0"

libraryDependencies += "javax.vecmath" % "vecmath" % "1.5.2" % Test
