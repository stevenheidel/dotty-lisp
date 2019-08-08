import org.junit.Test
import org.junit.Assert._

class TestInterpret {
  @Test def numericalFunctions(): Unit = {
    val examples = List(
      "(+ 1 2)" -> 3,
      "(+ (- 5) 5)" -> 0,
      "(* (+ 1) (+ 1 1) (+ 1 1 1))" -> 6,
      "(+)" -> 0,
      "(*)" -> 1,
    )

    for ((input, output) <- examples) {
      assertEquals(Interpret.interpret(Parse.parse(input)), LispObject.LInt(output))
    }
  }
}