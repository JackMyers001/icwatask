package one.jackmyers.icwatask.parser;

import java.util.List;
import one.jackmyers.icwatask.model.Command;
import one.jackmyers.icwatask.parser.generated.CommandLangLexer;
import one.jackmyers.icwatask.parser.generated.CommandLangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class CommandParser {
  /**
   * Parses a string of commands in the CommandLang syntax
   *
   * @param input input the string containing CommandLang instructions
   * @return a list of {@link Command}s
   * @throws ParseCancellationException if the input contains syntax errors or invalid commands
   */
  public static List<Command> parseString(String input) throws ParseCancellationException {
    var lexer = new CommandLangLexer(CharStreams.fromString(input));

    // By default, ANTLR will try to recover from / skip over invalid input. We don't want this, as
    // we'd rather disallow *any* invalid input.
    lexer.removeErrorListeners();
    lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

    var tokens = new CommonTokenStream(lexer);
    var parser = new CommandLangParser(tokens);
    var listener = new CommandListener();

    var walker = new ParseTreeWalker();

    try {
      walker.walk(listener, parser.program());
    } catch (ParseCancellationException e) {
      throw e;
    }
    // TODO: we should probably explicitly handle all known Exception types
    catch (Exception e) {
      throw new ParseCancellationException(e.getMessage(), e.getCause());
    }

    return listener.getCommands();
  }
}
