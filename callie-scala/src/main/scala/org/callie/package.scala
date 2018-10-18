package org

package object callie {

  implicit class AutoClose[T <: AutoCloseable](c:T){
    def |[V](f: T=> V) =  try{ f(c)
    }finally { c.close() }
  }

}
