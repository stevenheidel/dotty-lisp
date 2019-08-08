import org.junit.Test
import org.junit.Assert._

class TestParse {
  @Test def simpleExamples(): Unit = {
    val examples = List(
      "(list 1 2 3)",
      "(list (list 1 2) 3)",
      "(list (list (list)))",
    )

    for (example <- examples) {
      assertEquals(Parse.parse(example).toString, example)
    }
  }
}