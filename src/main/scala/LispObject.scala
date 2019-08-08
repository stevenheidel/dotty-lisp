enum LispObject {
  case LSymbol(name: String)
  case LList(elems: List[LispObject])
  
  case LInt(value: Int)

  override def toString: String = this match {
    case LSymbol(name) => name
    case LList(elems) => elems.mkString("(", " ", ")")

    case LInt(value) => value.toString
  }
}