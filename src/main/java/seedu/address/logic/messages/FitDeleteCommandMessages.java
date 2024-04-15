package seedu.address.logic.messages;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FITDELETE_DELETE_ALL;

/**
 * Messages used by FitDeleteCommand and associated classes.
 */
public class FitDeleteCommandMessages extends Messages {

    public static final String COMMAND_WORD = "fitdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the exercise specified by name, "
            + "from the client identified by their corresponding index.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EXERCISE_NAME + "EXERCISE_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXERCISE_NAME + "squats\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_FITDELETE_DELETE_ALL + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_FITDELETE_DELETE_ALL;

    public static final String MESSAGE_DELETE_ALL_EXERCISES_FAILURE = "Client does not have any existing exercises";
    public static final String MESSAGE_EXERCISE_NAME_DOES_NOT_EXIST =
            "Exercise name '%s' does not exist for the client";
    public static final String MESSAGE_DELETE_EXERCISE_SUCCESS = "Successfully removed exercise '%s' for client";
    public static final String MESSAGE_DELETE_ALL_EXERCISES_SUCCESS = "Successfully removed all exercise(s) for client";
    public static final String MESSAGE_CONCURRENT_PREFIX = String.format(
            "Exercise name parameter cannot be supplied together with '/all' prefix\n%1$s",
            MESSAGE_USAGE);
    public static final String MESSAGE_EXERCISE_NAME_PARAMETER_AND_ALL_PREFIX_MISSING = String.format(
            "Either exercise name parameter or '/all' prefix must be supplied\n%1$s",
            MESSAGE_USAGE);
    public static final String MESSAGE_NO_INDEX_FITDELETE = String.format(MESSAGE_NO_INDEX, MESSAGE_USAGE);
    public static final String MESSAGE_INVALID_INDEX_FITDELETE = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);
    public static final String MESSAGE_INVALID_COMMAND_FORMAT_FITDELETE = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_USAGE);
}
