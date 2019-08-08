Parse.parse("(list 1 2 3)")
Parse.parse("(list (list 1 2) 3)")
Parse.parse("(list (list (list)))")

val input = "(list (list 1 2) 3)"
Parse.parse(input).toString == input

val input = "(+ 1 2)"
Interpret.interpret(Parse.parse(input))