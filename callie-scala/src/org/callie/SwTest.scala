package org.callie

import scala.language.implicitConversions
import scala.concurrent.{ExecutionContextExecutor, ExecutionContext}
import java.util.concurrent.{Executor, Executors}
import javax.swing.SwingUtilities

object SwTest extends App{

  def g1() = {
    println("2"+Thread.currentThread().getName)
    4
  }

  def j1(i:Int) = {
    println("3"+Thread.currentThread().getName)
    println(i)
    7
  }

  def g2(a:Int) = {
    println(a+"/"+Thread.currentThread().getName)
    (j:Int) => {
      println("4"+Thread.currentThread().getName+""+j)
      5
    }
  }

  println("1"+Thread.currentThread().getName)

  Command.gui(g1()).job(j1).gui(g2(9)).job{ i =>
    println("5"+Thread.currentThread().getName)
    println(i)
  }.run()

  Command.job{
    println("-3"+Thread.currentThread().getName)
    7
  }.gui{ j =>
    println("-4"+Thread.currentThread().getName+""+j)
    5
  }.gui{ j =>
    println("-5"+Thread.currentThread().getName+""+j)
    5
  }.run()

}

object Command{
  implicit def asRun[F](f: => F) = new Runnable(){ def run() { f } }

  val eJob = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())
  val eGui = ExecutionContext.fromExecutor(new Executor(){
    override def execute(command: Runnable){ SwingUtilities.invokeLater(command) }
  })

  def gui[T](f : => T) = new GuiCommand[T](f)
  def job[T](f : => T) = new JobCommand[T](f)
}

trait Command[T] extends Runnable{
  //def execute()

  var next : Option[Task[T, _]] = None

  def gui[V](nf : T => V) ={
    val t = new GuiTask(this, nf)
    next = Some(t)
    t
  }

  def job[V](nf : T => V) ={
    val t = new JobTask(this, nf)
    next = Some(t)
    t
  }

  def execute(t: Task[T, _],b:T)
}

trait CommandGui[T] extends Command[T]{
  override def execute(t: Task[T, _], v:T){
    import Command._
    t match {
      case g: GuiTask[T, _] => g.complete(v)
      case j: JobTask[T, _] => Command.eJob.execute(j.complete(v))
    }
  }
}

trait CommandJob[T] extends Command[T]{
  override def execute(t: Task[T, _], v:T){
    import Command._
    t match {
      case g: GuiTask[T, _] => Command.eGui.execute(g.complete(v))
      case j: JobTask[T, _] => j.complete(v)
    }
  }
}

abstract class BaseCommand[T](f: => T, ec:ExecutionContextExecutor) extends Command[T]{
  override def run() {
    import Command._
    ec.execute(asRun{
      val v = f
      next.foreach(execute(_, v))
    })
  }
}

class GuiCommand[T](f: => T) extends BaseCommand[T](f, Command.eGui) with CommandGui[T]
class JobCommand[T](f: => T) extends BaseCommand[T](f, Command.eJob) with CommandJob[T]

abstract class Task[A, B](c: Command[_], f: A => B) extends Command[B]{
  override def run(){ c.run() }

  def complete(a: A){
    val v = f(a)
    next.foreach(execute(_, v))
  }

}

class GuiTask[A, B](c: Command[_], f: A => B) extends Task[A, B](c, f) with CommandGui[B]
class JobTask[A, B](c: Command[_], f: A => B) extends Task[A, B](c, f) with CommandJob[B]
