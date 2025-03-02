package one.jackmyers.icwatask;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import one.jackmyers.icwatask.model.TabletopState;
import one.jackmyers.icwatask.parser.CommandParser;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "IcwaTask", mixinStandardHelpOptions = true, version = "1.0.0")
public class IcwaTask implements Callable<Integer> {
  @Parameters(
      description = "Path to file (can also be set via FILE_PATH env var)",
      defaultValue = "${env:FILE_PATH}")
  private Path path;

  public static void main(String[] args) {
    final var exitCode = new CommandLine(new IcwaTask()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    if (!Files.exists(path)) {
      final var message = String.format("Provided path '%s' not found!", path);
      throw new FileNotFoundException(message);
    }

    final var content = Files.readString(path);
    final var commands = CommandParser.parseString(content);

    final var state = new TabletopState();
    state.handleCommands(commands);

    return CommandLine.ExitCode.OK;
  }
}
