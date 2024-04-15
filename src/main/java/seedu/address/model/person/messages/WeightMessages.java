package seedu.address.model.person.messages;

/**
 * Messages used by Weight and associated classes.
 */
public class WeightMessages {
    /** Represents an assert message indicating the valid input for RANGE */
    public static final String ASSERT_MESSAGE_RANGE = "Range should be more than or equals to zero."
            + " Should have been handled in Parser class";
    
    /** Represents a message indicating that there are no more weight values associated with a client. */
    public static final String MESSAGE_EMPTY_WEIGHT_MAP = "There are no more weight values to be removed.";
    
    /** Represents a message indicating the valid inputs for WEIGHT */
    public static final String MESSAGE_CONSTRAINTS =
            "Weight value can only be a number between 0 and 5000 (inclusive).";
    
    /** Represents a message indicating the valid inputs for DATE */
    public static final String MESSAGE_CONSTRAINTS_DATE = "Date value is invalid. Should follow the format "
            + "YYYY-MM-DDTHH:mm:ss, e.g. 2024-03-27T10:15:30";
    
    /** Represents a message indicating that the JSON key for WEIGHT is not found */
    public static final String MESSAGE_JSON_KEY_NOT_FOUND = "Key value not found in JSON file.";
    
    /** Represents a message indicating that the JSON weight value cannot be empty */
    public static final String MESSAGE_JSON_EMPTY_WEIGHT = "Weight value specified in JSON cannot be an empty string.";
}
