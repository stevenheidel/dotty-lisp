import LispObject._

import scala.collection.mutable.ListBuffer
import scala.util.Try

object Parse {
  def parse(source: String): LispObject = {
    parseList() given tokenize(source)
  }

  case class Tokens(tokens: Array[String], var position: Int = 0)

  def tokenize(source: String) = {
    val tokens = source.replace("(", "( ").replace(")", " )").split(' ').filter(_ != "")
    Tokens(tokens)
  }

  type Parser[T] = given Tokens => T

  def next(): Parser[String] = {
    val tokens = the[Tokens]
    val result = tokens.tokens(tokens.position)
    tokens.position += 1
    result
  }

  def skip(numChars: Int = 1): Parser[Unit] = {
    the[Tokens].position += numChars
  }

  def peek: Parser[String] = the[Tokens].tokens(the[Tokens].position)

  def consume(expected: String): Parser[Unit] = {
    if (peek == expected) skip()
    else                  throw RuntimeException(f"Expecting $expected but got $peek")
  }

  def parseList(): Parser[LispObject] = {
    var result = ListBuffer[LispObject]()

    consume("(")
    while (peek != ")") {
      if (peek == "(") result += parseList()
      else             result += parseValue()
    }
    consume(")")
    LList(result.toList)
  }

  def parseValue(): Parser[LispObject] = {
    val token = next()
    Try(token.toInt).map(LInt.apply) getOrElse LSymbol(token)
  }
}