name := "callie-scala"

version := "1.0"

scalaVersion := "2.11.6"

// https://github.com/sbt/sbt/issues/1821
resolvers += "nexus releases" at "http://mr01.hq.gratex.com/nexus/content/groups/public/"

libraryDependencies ++= Seq("org.jogamp.gluegen" % "gluegen-rt-main" % "2.3.1",
                            "org.jogamp.jogl" % "jogl-all-main" % "2.3.1",
                            "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4" )

libraryDependencies += "java3d" % "vecmath" % "1.3.1" % Test
