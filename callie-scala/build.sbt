name := "callie-scala"

version := "1.0"

scalaVersion := "2.11.7"

// https://github.com/sbt/sbt/issues/1821
//resolvers += "nexus releases" at "http://mr01.hq.gratex.com/nexus/content/groups/public/"

libraryDependencies ++= Seq("org.jogamp.gluegen" % "gluegen-rt-main" % "2.3.1",
                            "org.jogamp.jogl" % "jogl-all-main" % "2.3.1",
                            "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.4" )

libraryDependencies += "org.scala-lang.modules" % "scala-java8-compat_2.11" % "0.5.0"

libraryDependencies += "java3d" % "vecmath" % "1.3.1" % Test

//scalacOptions += "-optimise"

scalacOptions += "-feature"
scalacOptions += "-target:jvm-1.8"

scalacOptions += "-Xexperimental"
scalacOptions += "-Ybackend:GenBCode"
scalacOptions += "-Ydelambdafy:method"
