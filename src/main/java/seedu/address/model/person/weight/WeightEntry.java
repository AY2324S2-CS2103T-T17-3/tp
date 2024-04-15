package seedu.address.model.person.weight;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Map;

import seedu.address.model.person.Attribute;

/**
 * Represents a Person's weight in the address book.
 * Guarantees: immutable; is always valid.
 */
public class WeightEntry extends Attribute<Map.Entry<LocalDateTime, Weight>> {

    /**
     * Constructs a {@code WeightEntry}.
     *
     * @param weightEntry A weight entry containing the date of the recorded weight.
     */
    public WeightEntry(Map.Entry<LocalDateTime, Weight> weightEntry) {
        super(weightEntry);
        requireNonNull(weightEntry);
    }

    /**
     * Returns a {@code LocalDateTime} that is rounded down to the nearest second.
     *
     * @return The current date and time, rounded down to the nearest second.
     */
    public static LocalDateTime getTimeOfExecution() {
        return LocalDateTime.now().withNano(0);
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public boolean isMatch(Object weightRange) {
        return (this.equals(weightRange));
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
