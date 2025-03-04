# ICWA Assessment Solution

A solution to the [Toy Robot Coding Puzzle] problem created by Jon Eaves.

- **Developed by:** Jack Myers
- **Submission date:** 2025-03-04

[Toy Robot Coding Puzzle]: https://joneaves.wordpress.com/2014/07/21/toy-robot-coding-test/

## Solution Details

### Tech Stack

- Java 21
- Gradle
- JUnit 5

### Features

- [x] Read in command file from path (CLI argument or environment variable)
- [x] Parse command file
- [x] `PLACE`, `MOVE`, `LEFT`, `RIGHT`, `REPORT` commands
  - [x] Robot isn't created until `PLACE` command issued
  - [x] Robot prevented from falling off the tabletop
- [x] Building a "fat" JAR
- [x] Unit testing
- [x] Formatting with Spotless and `google-java-format`
- [x] Multi-step GitHub Action to build Docker container image

### Roadmap

- [ ] Unit/integration tests for main application entrypoint
- [ ] Better user-facing error handling/output

### 3rd Party

#### Libraries

- [ANTLR]

    *"ANTLR (ANother Tool for Language Recognition) is a powerful parser generator for reading, processing, executing, or translating structured text or binary files"*

    ANTLR is used to define [the grammar] and create [a parser] for processing command files.

    [the grammar]: ./src/main/antlr/CommandLang.g4
    [a parser]: ./src/main/java/one/jackmyers/icwatask/parser/CommandParser.java

- [Apache Commons Lang]

    *"A package of Java utility classes for the classes that are in `java.lang`'s hierarchy, or are considered to be so standard as to justify existence in `java.lang`"*

    I literally only use this for `StringUtils.EMPTY`. How is this not already in the base JDK.

- [JetBrains Java Annotations]

    *"A set of annotations used for code inspection support and code documentation"*

- [Picocli]

    *Picocli aims to be the easiest-to-use way to create rich command line applications that can run on and off the JVM*

    Picocli is used to create the program's CLI.

- [System Lambda]

    *"System Lambda is a collection of functions for testing code that uses `java.lang.System`"*

    Used in unit tests to capture/validate output written to `System.out.printX`

[ANTLR]: https://www.antlr.org
[Apache Commons Lang]: https://commons.apache.org/proper/commons-lang/
[JetBrains Java Annotations]: https://github.com/JetBrains/java-annotations
[Picocli]: https://picocli.info
[System Lambda]: https://github.com/stefanbirkner/system-lambda

#### Tools

- [JUnit 5]

    *"JUnit 5 is the current generation of the JUnit testing framework, which provides a modern foundation for developer-side testing on the JVM"*

- [Shadow]

    *"Gradle plugin for creating fat/uber JARs with support for package relocation"*

    It turns out that building a JAR with all dependencies included is hard.

- [Spotless]

    *"Keep your code spotless"*

    Code formatting / linting. Configured with `google-java-format` for Java files, and also configured to format ANTLR and Kotlin Gradle files.

[JUnit 5]: https://junit.org/junit5/
[Shadow]: https://github.com/GradleUp/shadow
[Spotless]: https://github.com/diffplug/spotless

### Notes

I sent an email asking to clarify a couple of things but I didn't get a response in time. The questions (and my interpretations / solutions) are noted here, in addition to the submission email.

- How/where/what test data/results do you want for the app and its logic?
  - I've included some example test data in the `examples/` folder
- What kind of "API or other interface" do you want to be able to test my application?
  - I created a GitHub Action to build and publish a Docker container image to the GitHub Container Registry. Instructions for running are included above
- How should the program handle grammatically/in-parsable commands?
  - My program will attempt to parse the entire file, and if it finds any invalid commands, it will terminate. It does not try to parse the file line-by-line, skipping over/erroring when it hits an invalid command
- Should there be any user feedback for (grammatically) valid but erroneous commands? (i.e. placing the robot outside the bounds, walking off the edge)
  - There’s no user feedback for erroneous commands

## Building

### Requirements

- Java JDK 21

### Building from source

1. Clone the repository

    ```bash
    git clone https://github.com/JackMyers001/icwatask.git
    cd icwatask
    ```

2. Build the project

    ```bash
    ./gradlew shadowJar
    ```

3. The generated JAR will be available at `./build/libs/icwatask-1.0-SNAPSHOT-all.jar`

## Usage

Example input:

| Path                     | Expected Output |
| :----------------------- | :-------------- |
| [`examples/move.txt`]    | `0, 1, NORTH`   |
| [`examples/rotate.txt`]  | `0, 0, WEST`    |
| [`examples/complex.txt`] | `3, 3, NORTH`   |

[`examples/move.txt`]: ./examples/move.txt
[`examples/rotate.txt`]: ./examples/rotate.txt
[`examples/complex.txt`]: ./examples/complex.txt

### JAR with CLI args

```bash
java -jar ./build/libs/icwatask-1.0-SNAPSHOT-all.jar <PATH-TO-FILE>
```

### JAR with environmental variable

```bash
export FILE_PATH=<PATH-TO-FILE>
java -jar ./build/libs/icwatask-1.0-SNAPSHOT-all.jar
```

### Docker

```bash
docker run --rm -v <PATH-TO-FILE>:/app/input.txt:ro ghcr.io/jackmyers001/icwatask:master input.txt
```
