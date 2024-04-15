package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.messages.HeightMessages.MESSAGE_NO_HEIGHT;
import static seedu.address.model.person.messages.HeightMessages.MESSAGE_RANGE;

import javafx.util.Pair;

/**
 * Represents a Person's height in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Height extends Attribute<Float> {
    /** Represents a maximum value a Height. */
    public static final Float HEIGHT_MAX_VALUE = 5000f;
    /** Regular expression to check for a valid height value. */
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";

    /**
     * Constructs a {@code height}.
     *
     * @param height A height.
     */
    public Height(Float height) {
        super(height);
        requireNonNull(height);
    }

    /**
     * Returns true if a given string is a valid height.
     *
     * @param test String value to test whether it is a valid height.
     * @return True if input is a valid height, false otherwise.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX) && Float.valueOf(test) <= HEIGHT_MAX_VALUE;
    }

    /**
     * Determines if the height value stored is within the range specified in
     * heightRange.
     * Returns true if specified value is within heightRange.
     *
     * @param heightRange Range of height to check against.
     * @return True if value is falls within heightRange, false otherwise.
     */
    @Override
    public boolean isMatch(Object heightRange) {
        if (!(heightRange instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) heightRange;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        Float firstVal = (Float) pair.getKey();
        Float secondVal = (Float) pair.getValue();

        assert (secondVal - firstVal >= 0) : MESSAGE_RANGE;
        // if a client has no height value
        if (this.isZero()) {
            return false;
        }

        return (this.getValue() >= firstVal && this.getValue() <= secondVal);
    }

    /**
     * Checks if the height object has a value of 0
     *
     * @return true if the weight object has a value of 0 and false otherwise
     */
    public boolean isZero() {
        return getValue() == 0f;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    /**
     * Adds a heading for the height field.
     * Empty height values (i.e. 0f) will be formatted as "N/A" for better clarity.
     *
     * @return A formatted height with headers and units for the height value.
     */
    public String getFormattedHeight() {
        if (this.isZero()) {
            return MESSAGE_NO_HEIGHT;
        }
        return "Height: " + this.getValue().toString() + " cm";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null types as well.
        if (!(other instanceof Height)) {
            return false;
        }

        Height otherHeight = (Height) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherHeight.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

    /**
     * Checks if the value is valid (i.e. greater than 0).
     *
     * @return True if this instance of height has a value more than 0.
     */
    public boolean isValid() {
        return this.getValue() > 0f;
    }
}
