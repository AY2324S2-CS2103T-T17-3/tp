package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.HeightCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Height;

/**
 * Parses input arguments and creates a new {@code HeightCommand} object
 */
public class HeightCommandParser implements Parser<HeightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code HeightCommand}
     * and returns a {@code HeightCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HeightCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_HEIGHT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HeightCommand.MESSAGE_USAGE), ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_HEIGHT);

        if (argMultimap.getValue(PREFIX_HEIGHT).isPresent()) {
            return new HeightCommand(index, new Height(Float.valueOf(argMultimap.getValue(PREFIX_HEIGHT).get())));
        }

        return new HeightCommand(index, new Height(0f));
    }
}