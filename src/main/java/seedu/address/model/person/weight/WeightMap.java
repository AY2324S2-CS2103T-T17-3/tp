package seedu.address.model.person.weight;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NavigableMap;

import javafx.util.Pair;
import seedu.address.model.person.Attribute;

/**
 * Represents a map of date-weight values in the address book belonging to a Person.
 */
public class WeightMap extends Attribute<NavigableMap<LocalDateTime, Weight>> {

    /**
     * Constructs a {@code WeightMap}
     *
     * @param weightMap Set of valid weights
     */
    public WeightMap(NavigableMap<LocalDateTime, Weight> weightMap) {
        super(weightMap);
        requireNonNull(weightMap);
    }

    /**
     * Returns an immutable weight map, which throws
     * {@code UnsupportedOperationException} if modification is attempted.
     */
    @Override
    public NavigableMap<LocalDateTime, Weight> getValue() {
        return Collections.unmodifiableNavigableMap(super.getValue());
    }

    /**
     * Determines if the weight values stored falls within the range specified.
     * Returns true if any values in the weight map falls within the specified range.
     *
     * @param otherValue Other value to check against
     * @return True if there exists a weight value that falls within the range, false otherwise.
     */
    @Override
    public boolean isMatch(Object otherValue) {
        if (!(otherValue instanceof Pair)) {
            return false;
        }

        Pair<?, ?> pair = (Pair<?, ?>) otherValue;

        if (!(pair.getKey() instanceof Float) || !(pair.getValue() instanceof Float)) {
            return false;
        }

        // Already checked that set contains Float objects, so it is safe to cast
        @SuppressWarnings("unchecked")
        Pair<Float, Float> weightRange = (Pair<Float, Float>) otherValue;

        assert (weightRange.getValue() - weightRange.getKey() >= 0)
                : "Range should be more than or equals to zero. Should have been handled in Parser class";

        // If Person has no weights saved
        if (this.getValue().isEmpty()) {
            return false;
        }

        Float latestWeight = this.getValue().lastEntry().getValue().getValue();
        return (latestWeight >= weightRange.getKey()) && (latestWeight <= weightRange.getValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeightMap)) {
            return false;
        }

        WeightMap otherMap = (WeightMap) other;
        return this.getValue().equals(otherMap.getValue());
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
