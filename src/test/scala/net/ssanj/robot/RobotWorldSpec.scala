package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class RobotWorldSpec extends Matchers with WordSpecLike {
  "A RobotWorld" should {
    "interpret commands" when {
      "given a valid String" in {
        RobotWorld.interpret("MOVE")   should be (Option(Move))
        RobotWorld.interpret("LEFT")   should be (Option(Left))
        RobotWorld.interpret("RIGHT")  should be (Option(Right))
        RobotWorld.interpret("REPORT") should be (Option(Report))
      }
    }
  }

  it should {
    "not interpret commands" when {
      "given an invalid String" in {
        RobotWorld.interpret("UP")     should be (None)
        RobotWorld.interpret("DOWN")   should be (None)
        RobotWorld.interpret("Move")   should be (None)
        RobotWorld.interpret("report") should be (None)
      }
    }
  }
}