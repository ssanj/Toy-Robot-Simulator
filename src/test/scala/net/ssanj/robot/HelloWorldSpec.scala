package net.ssanj.robot

import org.scalatest.{Matchers, WordSpecLike}

final class HelloWorldSpec extends Matchers with WordSpecLike {

  "A HelloWorld" should {
    "do something" when {
      "used this way" in {
        ("Hello" + " World") should be ("Hello World")
      }
    }
  }
}

