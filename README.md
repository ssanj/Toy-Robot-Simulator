# Rea Robot #

REA Robot Assignment

Todo

[] Add more property tests around the board
[] Add scaladoc - much of this is needed?

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

To run the main application (RobotRunner) invoke:

```
sbt run
```

## Coverage

Coverage can be enabled by doing the following:

1. Instrument code and tests with

```
sbt clean coverage test
```

2. Generate coverage reports with:

```
sbt coverageReport
```

## Scapegoat


To run [Scapegoat](https://github.com/sksamuel/scapegoat) use:

```
sbt scapegoat
```

Reports can be found under __target/scala-2.11/scoverage-report/index.html__.

## Using the API

The __RobotController__ is the main entry point into the api. Use the __execute__ method to run a list of commands against a Robot. Here's some example code:

```
  import io.IO.loadCommandsFromClasspath

  val board    = Board(Size(5, 5))
  val commands = loadCommandsFromClasspath("commands.txt")
  val output   = RobotController.execute(board, commands)

  println(output.value)

```