package seedu.address.model.person.messages;

/**
 * Messages used by Height and associated classes.
 */
public class HeightMessages {

    public static final String MESSAGE_RANGE = "Range should be more than or equals to zero."
            + " Should have been handled in Parser class";
    public static final String MESSAGE_NO_HEIGHT = "Height: N/A";
    public static final String MESSAGE_JSON_EMPTY_HEIGHT = "Height value specified in JSON cannot be an empty string.";
    public static final String MESSAGE_CONSTRAINTS =
            "Height value can only be a number between 0 and 5000 (inclusive).";
}
