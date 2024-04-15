package seedu.address.logic.messages;

/**
 * Messages used by ListCommand and associated classes.
 */
public class NoteCommandMessages extends Messages {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates and overrides the note of the client identified "
            + "by their corresponding index.\n"
            + "Parameters: INDEX (must be a positive integer) [NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Likes to swim.";
    public static final String MESSAGE_EDIT_FEEDBACK_TO_USER = "Editing note for client: ";
    public static final String NOTE_EDIT_TAG = "/edit";
    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Successfully added note to client!"
            + MESSAGE_RESULT_DIVIDER;
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Successfully removed note from client!"
            + MESSAGE_RESULT_DIVIDER;
    public static final String MESSAGE_INVALID_INDEX_NOTE = String.format(MESSAGE_INVALID_INDEX, MESSAGE_USAGE);
    public static final String MESSAGE_NO_INDEX_NOTE = String.format(MESSAGE_NO_INDEX, MESSAGE_USAGE);
}
