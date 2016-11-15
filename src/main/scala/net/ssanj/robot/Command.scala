package net.ssanj.robot

sealed trait Command
final case class Place(pos: BoardPos) extends Command
case object Move                      extends Command
case object Left                      extends Command
case object Right                     extends Command
case object Report                    extends Command
