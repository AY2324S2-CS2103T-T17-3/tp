package seedu.address.logic.messages;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

/**
 * Messages used by AddCommand and associated classes.
 */
public class AddCommandMessages extends Messages {

    /** Represents a command word tied to the "add" command" */
    public static final String COMMAND_WORD = "add";

    /** Represents a string that contains the usage of the add command */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to FitBook. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "john@gmail.com "
            + PREFIX_ADDRESS + "Clementi Ave 2 "
            + PREFIX_NOTE + "BFF "
            + PREFIX_TAG + "friend ";

    /** Represents a string to indicate the successful addition of a client. */
    public static final String MESSAGE_CLIENT_ADDED = "Client successfully added!";

    /** Represents a string to indicate that a person with the same details already exists */
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    /** Represents a string to indicate the successful addition of a client. */
    public static final String MESSAGE_SUCCESS = MESSAGE_CLIENT_ADDED + MESSAGE_RESULT_DIVIDER;

    /** Represents a string to indicate no parameters are specified. */
    public static final String MESSAGE_NO_PARAMETERS_ADD = String.format(MESSAGE_NO_PARAMETERS,
            MESSAGE_USAGE);

    /** Represents a string to indicate missing parameter for name. */
    public static final String MESSAGE_NAME_PARAMETER_MISSING_ADD = String.format(MESSAGE_NAME_PARAMETER_MISSING,
            MESSAGE_USAGE);

    /** Represents a string to indicate missing parameter for phone. */
    public static final String MESSAGE_PHONE_PARAMETER_MISSING_ADD = String.format(MESSAGE_PHONE_PARAMETER_MISSING,
            MESSAGE_USAGE);

    /** Represents a string to indicate invalid command format. */
    public static final String MESSAGE_INVALID_COMMAND_FORMAT_ADD = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_USAGE);
}
