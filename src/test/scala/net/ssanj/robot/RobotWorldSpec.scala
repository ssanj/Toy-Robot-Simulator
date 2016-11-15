package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class RobotWorldSpec extends Matchers with WordSpecLike {
  "A RobotWorld" should {
    "interpret commands" when {
      "given a valid String" in {
        CommandParser.interpret("MOVE")   should be (Option(Move))
        CommandParser.interpret("LEFT")   should be (Option(Left))
        CommandParser.interpret("RIGHT")  should be (Option(Right))
        CommandParser.interpret("REPORT") should be (Option(Report))
      }
    }
  }

  it should {
    "not interpret commands" when {
      "given an invalid String" in {
        CommandParser.interpret("UP")     should be (None)
        CommandParser.interpret("DOWN")   should be (None)
        CommandParser.interpret("Move")   should be (None)
        CommandParser.interpret("report") should be (None)
      }
    }
  }
}