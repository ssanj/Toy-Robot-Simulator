package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class CommandParserSpec extends Matchers with WordSpecLike {
  "A CommandParser" should {
    "parse commands" when {
      "given a valid String" in {
        CommandParser.interpret("MOVE")   should be (Option(Move))
        CommandParser.interpret("LEFT")   should be (Option(Left))
        CommandParser.interpret("RIGHT")  should be (Option(Right))
        CommandParser.interpret("REPORT") should be (Option(Report))
      }
    }
  }

  it should {
    "drop invalid commands" when {
      "given an invalid String" in {
        CommandParser.interpret("UP")     should be (None)
        CommandParser.interpret("DOWN")   should be (None)
        CommandParser.interpret("Move")   should be (None)
        CommandParser.interpret("report") should be (None)
      }
    }
  }
}