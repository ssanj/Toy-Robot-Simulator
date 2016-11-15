package net.ssanj.robot.io

import net.ssanj.robot._

import org.scalatest.{Matchers, WordSpecLike}

final class IOSpec extends Matchers with WordSpecLike {
  "An IO loader" should {
    "load commands from the classpath" when {
      "given a command file without errors" in {
        val commands = IO.loadCommandsFromClasspath("commands1.txt")
        commands should have ('size (6))
        commands should be (
          Seq(
            Place(BoardPos(1, 2, East)),
            Move,
            Move,
            Left,
            Move,
            Report)
          )
      }
    }

    "load commands from the classpath" when {
      "a given a command file with errors" in {
        val commands = IO.loadCommandsFromClasspath("commands2.txt")
        commands should have ('size (3))
        commands should be (
          Seq(
            Place(BoardPos(4, 1, North)),
            Move,
            Left
          )
        )
      }
    }
  }
}