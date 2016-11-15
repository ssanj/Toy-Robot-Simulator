package net.ssanj.robot

import org.scalacheck.{Prop, Properties, Gen}

object DirectionProps extends Properties("Direction") {

  private def genValidDirection: Gen[String] = for {
    d   <- Gen.oneOf(Direction.directions.map(_.name))
    du  = d.map(_.toUpper)
    dl  = d.map(_.toLower)
    dir <- Gen.oneOf(d, du, dl)
  } yield dir

  property("getDirection") =
    Prop.forAll(genValidDirection) { dir =>
      Direction.getDirection(dir).isDefined
    }
  }