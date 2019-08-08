import LispObject._

object Interpret {
  def interpret(input: LispObject): LispObject = input match {
    case LList(elems) => elems.headOption match {
      case Some(LSymbol(functionName)) => funcCall(functionName, elems.tail)
      case _ => throw RuntimeException("Invalid function call")
    }
    case _ => input
  }

  trait LispFunction {
    def call(arguments: List[LispObject]): LispObject
  }

  case class NumericalLispFunction(
    function: (Int, Int) => Int,
    initial: Int,
  ) extends LispFunction {
    def call(arguments: List[LispObject]): LispObject = {
      val unpackedArgs: List[Int] = arguments map {
        case LInt(int) => int
        case other     => interpret(other) match {
          case LInt(int) => int
          case other     => throw RuntimeException(f"Expecting int but got $other")
        }
      }

      val result = unpackedArgs.foldLeft(initial)(function)
      LInt(result)
    }
  }

  val functions = Map(
    "+" -> NumericalLispFunction(_ + _, 0),
    "-" -> NumericalLispFunction(_ - _, 0),
    "*" -> NumericalLispFunction(_ * _, 1),
  )

  def funcCall(name: String, arguments: List[LispObject]): LispObject = {
    val function = functions.getOrElse(name, throw RuntimeException(f"No function named $name"))
    function.call(arguments)
  }
}