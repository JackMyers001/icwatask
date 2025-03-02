package one.jackmyers.icwatask.parser;

import java.util.ArrayList;
import java.util.List;
import one.jackmyers.icwatask.model.CardinalDirection;
import one.jackmyers.icwatask.model.Command;
import one.jackmyers.icwatask.model.Command.PlaceCommand;
import one.jackmyers.icwatask.model.Vector2;
import one.jackmyers.icwatask.parser.generated.CommandLangBaseListener;
import one.jackmyers.icwatask.parser.generated.CommandLangLexer;
import one.jackmyers.icwatask.parser.generated.CommandLangParser;

class CommandListener extends CommandLangBaseListener {
  private final List<Command> commands = new ArrayList<>();

  public List<Command> getCommands() {
    return commands;
  }

  @Override
  public void exitPlaceCommand(CommandLangParser.PlaceCommandContext ctx) {
    final var x = Integer.parseInt(ctx.INT(0).getText());
    final var y = Integer.parseInt(ctx.INT(1).getText());
    final var direction = parseDirection(ctx.direction());

    commands.add(new PlaceCommand(new Vector2(x, y), direction));
  }

  @Override
  public void exitMoveCommand(CommandLangParser.MoveCommandContext ctx) {
    commands.add(Command.MOVE);
  }

  @Override
  public void exitReportCommand(CommandLangParser.ReportCommandContext ctx) {
    commands.add(Command.REPORT);
  }

  @Override
  public void exitLeftCommand(CommandLangParser.LeftCommandContext ctx) {
    commands.add(Command.LEFT);
  }

  @Override
  public void exitRightCommand(CommandLangParser.RightCommandContext ctx) {
    commands.add(Command.RIGHT);
  }

  private CardinalDirection parseDirection(CommandLangParser.DirectionContext ctx) {
    var startToken = ctx.getStart();
    var tokenType = startToken.getType();

    return switch (tokenType) {
      case CommandLangLexer.NORTH -> CardinalDirection.NORTH;
      case CommandLangLexer.SOUTH -> CardinalDirection.SOUTH;
      case CommandLangLexer.EAST -> CardinalDirection.EAST;
      case CommandLangLexer.WEST -> CardinalDirection.WEST;
      default -> throw new IllegalArgumentException("Unknown direction token type: " + tokenType);
    };
  }
}
