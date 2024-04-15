package seedu.address.logic.messages;

/**
 * Messages used by WeightCommand and associated classes.
 */
public class WeightCommandMessages extends Messages {
    /** Represents a command word tied to the "weight" command" */
    public static final String COMMAND_WORD = "weight";

    /** Represents a string that contains the usage of the weight command */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a weight (in kg) to the client specified by the index number used in the client list.\n"
            + "If no weight or '0' is specified, the latest weight value will be removed from the client.\n"
            + "Parameters: INDEX [WEIGHT] (must be a number between 0 and 5000)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "70.0";

    /** Represents the header to a weight value. */
    public static final String WEIGHT_VALUE_HEADER = "Weight: ";

    /** Represents the header to a date value for an associated weight. */
    public static final String WEIGHT_DATE_HEADER = "Date Recorded: ";

    /** Represents a string to indicate the successful addition of a weight value. */
    public static final String MESSAGE_ADD_WEIGHT_SUCCESS =
            "Successfully added weight to client!"
            + MESSAGE_RESULT_DIVIDER;

    /** Represents a string to indicate the successful deletion of a weight value. */
    public static final String MESSAGE_DELETE_WEIGHT_SUCCESS =
            "Successfully removed weight from client!"
            + MESSAGE_RESULT_DIVIDER;

    /** Represents a string to indicate an invalid parameter was specified. */
    public static final String MESSAGE_INVALID_PARAMETER_WEIGHT = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);

    /** Represents a string to indicate that no parameter was specified. */
    public static final String MESSAGE_NO_PARAMETER_WEIGHT = String.format(MESSAGE_NO_PARAMETERS, MESSAGE_USAGE);

    /** Represents a string to indicate an invalid parameter was specified. */
    public static final String MESSAGE_INVALID_INDEX_WEIGHT = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);
}
