package org.callie.ldap

trait LdapElement

trait User extends LdapElement{

  def login:String

  def firstName:String
  def lastName:String

  def mail:String

  def groups:List[Group]

  /** lambda v java 1.8, lepsie ako set-re a commit/apply metoda */
  def change[T](f:UserChange=>T)
}

trait UserChange{
  def firstName(nm:String):UserChange
  def lastName(nm:String):UserChange
  def mail(nm:String):UserChange

  def addMember(g:Group):UserChange
  def removeMember(g:Group):UserChange

  def pass(newPass:String, oldPass:String=""):UserChange
}

trait Group extends LdapElement{

  def name:String

  def members:List[User]

  /** lambda v java 1.8, lepsie ako set-re a commit/apply metoda */
  def change[T](f : GroupChange=>T)
}

trait GroupChange{
  def addMember(u:User):GroupChange
  def removeMember(u:User):GroupChange
}

trait Ldap{

  def create(login:String, f : UserChange=>Unit):User

  def create(name:String, f : GroupChange=>Unit):Group

  // cache control
  def refresh(u:LdapElement*):Ldap
  def clear()
}

object Test{

  def user(u:User) {
    u.change {
      c =>
        c.firstName("some")
        c.mail("a@b.c")
        1
    }

    u.change(_.firstName("some").mail("a@b.c"))
  }
}