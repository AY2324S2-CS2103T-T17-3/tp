package seedu.address.model.person.weight;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.messages.WeightMessages.ASSERT_MESSAGE_RANGE;

import javafx.util.Pair;
import seedu.address.model.person.Attribute;

/**
 * Represents a Person's weight in the address book.
 * Guarantees: immutable; is always valid.
 */
public class Weight extends Attribute<Float> {
    /** Represents a maximum value of a Weight. */
    public static final Float WEIGHT_MAX_VALUE = 5000f;
    /** Regular expression to check for a valid weight value. */
    public static final String VALIDATION_REGEX = "^(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)?$";

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A weight.
     */
    public Weight(Float weight) {
        super(weight);
        requireNonNull(weight);
    }

    /**
     * Returns true if a given string is a valid weight.
     *
     * @param test String value to test whether it is a valid weight.
     * @return True if input is a valid weight, false otherwise.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX) && Float.valueOf(test) <= WEIGHT_MAX_VALUE;
    }

    /**
     * Checks if the weight object has a value of 0
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
     * Determine if the weight value stored is within the range specified in weightRange.
     * Returns true if specified value is within weightRange.
     *
     * @param weightRange Range of weight to check against.
     * @return True if value is falls within weightRange, false otherwise.
     */
    @Override
    public boolean isMatch(Object weightRange) {
        if (!(weightRange instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) weightRange;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        Float firstVal = (Float) pair.getKey();
        Float secondVal = (Float) pair.getValue();

        assert (secondVal - firstVal >= 0) : ASSERT_MESSAGE_RANGE;

        return (this.getValue() >= firstVal && this.getValue() <= secondVal);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null types as well.
        if (!(other instanceof Weight)) {
            return false;
        }

        Weight otherWeight = (Weight) other;

        // Use the equals() method of the underlying attribute to compare values
        return this.getValue().equals(otherWeight.getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
