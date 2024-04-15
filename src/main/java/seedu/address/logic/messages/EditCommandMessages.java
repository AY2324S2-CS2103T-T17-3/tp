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
 * Messages used by EditCommand and associated classes.
 */
public class EditCommandMessages extends Messages {

    /** Represents a command word tied to the "edit" command" */
    public static final String COMMAND_WORD = "edit";

    /** Represents a string that contains the usage of the edit command */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a client's details specified "
            + "by the corresponding index in the client list.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_WEIGHT + "WEIGHT] "
            + "[" + PREFIX_HEIGHT + "HEIGHT] "
            + "[" + PREFIX_NOTE + "NOTE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "john@gmail.com";

    /** Represents a string to indicate that a person with the same details already exists */
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    /** Represents a string to indicate the successful edition of a client **/
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Successfully edited client!" + MESSAGE_RESULT_DIVIDER;

    /** Represents a string to indicate that a person was not edited **/
    public static final String MESSAGE_NOT_EDITED = String.format("At least one field to edit must be provided.\n%1$s",
            MESSAGE_USAGE);

    /** Represents a string to indicate an invalid index was specified. **/
    public static final String MESSAGE_INVALID_INDEX_EDIT = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);

    /** Represents a string to indicate an invalid command format was specified. **/
    public static final String MESSAGE_INVALID_COMMAND_FORMAT_EDIT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            MESSAGE_USAGE);

    /** Represents a string to indicate no index was specified. **/
    public static final String MESSAGE_NO_INDEX_EDIT = String.format(MESSAGE_NO_INDEX, MESSAGE_USAGE);

}
