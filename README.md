# Rea Robot #

REA Robot Assignment

## Building

To compile the source and tests run:

```
sbt test:compile
```

## Tests

To run the tests use:

```
sbt test
```

## Running

To run the main application (net.ssanj.robot.RobotRunner) invoke:

```
sbt run
```

## Coverage

Coverage can be enabled by doing the following:

1. Instrument code and tests with:

```
sbt clean coverage test
```

2. Generate coverage reports with:

```
sbt coverageReport
```

Reports can be found under __target/scala-2.11/scoverage-report/index.html__.

## Scapegoat


To run [Scapegoat](https://github.com/sksamuel/scapegoat) use:

```
sbt scapegoat
```

Reports can be found under __target/scala-2.11/scapegoat-report/scapegoat.html__.

## Scaladoc

To generate scaladoc for the sources use:

```
sbt doc
```

The API documentation can be found under __target/scala-2.11/api/index.html__.


## Using the API

The __RobotController__ is the main entry point into the api. Use the __execute__ method to run a list of commands against a Robot.

Here's example of loading some commands from a file on the classpath:

```
  import net.ssanj.robot._
  import io.IO

  val board    = Board(Size(5, 5))
  val commands = IO.loadCommandsFromClasspath("commands.txt")
  val output   = RobotController.execute(board, commands)

  println(output.value)

```

Here's example of running commands directly against the __RobotController__:

```
  import net.ssanj.robot._

  val board    = Board(Size(5, 5))
  val commands = Seq(Place(BoardPos(0,0, East)), Move, Move, Move, Report)
  val output   = RobotController.execute(board, commands)

  println(output.value)

```