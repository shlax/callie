package org.callie

import scala.language.implicitConversions
import scala.concurrent.{ExecutionContextExecutor, ExecutionContext}
import java.util.concurrent.Executors

object SwTest extends App{

  println("1"+Thread.currentThread().getName)

  Command.gui{
    println("2"+Thread.currentThread().getName)
    4
  }.job{ i =>
    println("3"+Thread.currentThread().getName)
    println(i)
    7
  }.gui{ j =>
    println("4"+Thread.currentThread().getName+""+j)
    5
  }.job{ i =>
    println("5"+Thread.currentThread().getName)
    println(i)
  }.execute()

  Command.job{
    println("-3"+Thread.currentThread().getName)
    7
  }.gui{ j =>
    println("-4"+Thread.currentThread().getName+""+j)
    5
  }.gui{ j =>
    println("-5"+Thread.currentThread().getName+""+j)
    5
  }.execute()

}

object Command{
  implicit def asRun[F](f: => F) = new Runnable(){ def run() { f } }

  val eJob = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())
  val eGui = ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor())

  def gui[T](f : => T) = new GuiCommand[T](f)
  def job[T](f : => T) = new JobCommand[T](f)
}

trait Command[T]{
  def execute()

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
  def execute() {
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
  override def execute(){ c.execute() }

  def complete(a: A){
    val v = f(a)
    next.foreach(execute(_, v))
  }

}

class GuiTask[A, B](c: Command[_], f: A => B) extends Task[A, B](c, f) with CommandGui[B]
class JobTask[A, B](c: Command[_], f: A => B) extends Task[A, B](c, f) with CommandJob[B]
