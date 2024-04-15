package seedu.address.logic.messages;

import static seedu.address.model.person.messages.PhoneMessages.MESSAGE_EXPECTED;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    /** Represents a string to indicate an unknown command was specified. */
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command, please type 'help' for possible commands";

    /** Represents a string to indicate an invalid command was specified. */
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    /** Represents a string to indicate no index was specified. */
    public static final String MESSAGE_NO_INDEX = "No index specified! \n%1$s";

    /** Represents a string to indicate an invalid index was specified. */
    public static final String MESSAGE_INVALID_INDEX = "Invalid index provided. \n%1$s";

    /** Represents a string to indicate duplicate fields was specified. */
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /** Represents a string to indicate no parameters was specified. */
    public static final String MESSAGE_NO_PARAMETERS = "No parameters specified! \n%1$s";

    /** Represents a string to indicate missing name parameter. */
    public static final String MESSAGE_NAME_PARAMETER_MISSING = "Name parameter missing! \n%1$s";

    /** Represents a string to indicate missing phone parameter. */
    public static final String MESSAGE_PHONE_PARAMETER_MISSING = "Phone number parameter missing! \n%1$s";

    /** Represents a string to indicate the header of warning. */
    public static final String MESSAGE_WARN = "\n\nWARNING: %s";

    /** Represents a string to indicate a divider for formatting. */
    public static final String MESSAGE_RESULT_DIVIDER = "\n-------------------------------------------\n%1$s";

    /** Represents a string to indicate warning for phone number not matching an expected format. */
    public static final String MESSAGE_PHONE_WARN = String.format(MESSAGE_WARN, MESSAGE_EXPECTED);

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Note: ")
                .append(person.getNote())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
