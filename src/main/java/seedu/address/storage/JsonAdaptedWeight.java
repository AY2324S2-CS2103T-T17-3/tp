package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.messages.WeightMessages;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightEntry;

/**
 * Jackson-friendly version of {@link Weight} with {@code LocalDateTime} as key.
 */
class JsonAdaptedWeight {

    private final String weightDate;
    private final String weightValue;

    /**
     * Constructs a {@code JsonAdaptedWeight} with the given {@code weightDate} and {@code weightValue}.
     */
    @JsonCreator
    public JsonAdaptedWeight(@JsonProperty("weightDate") String weightDate,
                             @JsonProperty("weightValue") String weightValue) {
        this.weightDate = weightDate;
        this.weightValue = weightValue;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedWeight(WeightEntry source) {
        this.weightDate = source.getValue().getKey().toString();
        this.weightValue = source.getValue().getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted date-weight key-value pair into the model's
     * {@code Entry<LocalDateTime, Weight>} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public WeightEntry toModelType() throws IllegalValueException {
        try {
            LocalDateTime date = LocalDateTime.parse(this.weightDate);

            // Invalid weight modification in JSON file
            if (!Weight.isValidWeight(this.weightValue)) {
                throw new IllegalValueException(WeightMessages.MESSAGE_CONSTRAINTS);
            }

            return new WeightEntry(new AbstractMap.SimpleEntry<>(date, new Weight(Float.valueOf(this.weightValue))));
        } catch (DateTimeParseException e) {
            // Invalid date modification in JSON file
            throw new IllegalValueException(WeightMessages.MESSAGE_CONSTRAINTS_DATE);
        } catch (NullPointerException e) {
            // Date or weight key not found in JSON file
            throw new IllegalValueException(WeightMessages.MESSAGE_JSON_KEY_NOT_FOUND);
        } catch (NumberFormatException e) {
            // Weight specified to be an empty string. In the case of parsing from JSON, this is not allowed
            throw new IllegalValueException(WeightMessages.MESSAGE_JSON_EMPTY_WEIGHT);
        }
    }
}
