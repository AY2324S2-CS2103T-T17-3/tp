package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.messages.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is
 * maintained.
 * Keys are unique, but the list of argument values may contain duplicate
 * argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /**
     * Prefixes mapped to their respective arguments
     **/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is
     * appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be
     *                 associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        this.argMultimap.put(prefix, argValues);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Gets the value associated with the prefix or an empty string
     *
     * @param prefix the associated prefix
     * @return a string value or empty string
     */
    public String getValueOrEmpty(Prefix prefix) {
        return this.getValue(prefix).orElse("");
    }

    /**
     * Gets the value associated with the prefix or a preamble if the value does not
     * exist
     *
     * @param prefix the associated prefix
     * @return the associated value or preamble
     */
    public String getValueOrPreamble(Prefix prefix) {
        return this.getValue(prefix).orElse(getPreamble());
    }

    /**
     * Gets string value associated with the given prefix
     *
     * @param prefix the associated prefix
     * @return a string
     */
    public String getStringValue(Prefix prefix) {
        return getValue(prefix).get();
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty
     * list.
     * Modifying the returned list will not affect the underlying data structure of
     * the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!this.argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(this.argMultimap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any
     * leading/trailing spaces.
     */
    public String getPreamble() {
        return this.getValue(new Prefix("")).orElse("");
    }

    /**
     * Gets the number of whitespace-separated segments in a preamble
     *
     * @return an integer representing the number of the preamble segments
     */
    public Integer getPreambleSegmentNumber() {
        return getPreamble().split(" ").length;
    }

    /**
     * Throws a {@code ParseException} if any of the prefixes given in
     * {@code prefixes} appeared more than
     * once among the arguments.
     */
    public void verifyNoDuplicatePrefixesFor(Prefix... prefixes) throws ParseException {
        Prefix[] duplicatedPrefixes = Stream.of(prefixes).distinct()
                .filter(prefix -> this.argMultimap.containsKey(prefix) && this.argMultimap.get(prefix).size() > 1)
                .toArray(Prefix[]::new);

        if (duplicatedPrefixes.length > 0) {
            throw new ParseException(Messages.getErrorMessageForDuplicatePrefixes(duplicatedPrefixes));
        }
    }

    /**
     * Checks if the argument multimap contains a non-empty value for any of the
     * specified prefixes.
     *
     * @param prefixes the prefixes to check for argument values
     * @return {@code true} if at least one of the specified prefixes has a
     *         non-empty argument value,
     *         {@code false} otherwise
     */
    public boolean hasArgumentValueForPrefixes(Prefix... prefixes) {
        Prefix[] prefixesWithValues = Stream.of(prefixes).distinct()
                .filter(prefix -> this.argMultimap.containsKey(prefix) && !this.argMultimap.get(prefix).isEmpty()
                        && !this.argMultimap.get(prefix).stream().allMatch(String::isEmpty))
                .toArray(Prefix[]::new);

        return prefixesWithValues.length > 0;
    }

    /**
     * Returns true if the prefix exists as a key in the map.
     *
     * @param prefix to check
     * @return true if prefix is present and false otherwise
     */
    public boolean contains(Prefix prefix) {
        return this.argMultimap.containsKey(prefix);
    }

    /**
     * Checks if any number of the prefixes specified is present in the
     * ArgumentMultimap object
     *
     * @param prefixes to check
     * @return true if prefix/s present and false otherwise
     */
    public boolean containsAny(Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(this::contains);
    }

    /**
     * Checks if the preamble of the argumentMultimap object is empty
     *
     * @return true if the preamble is empty
     */
    public boolean isPreambleEmpty() {
        return this.getPreamble().isEmpty();
    }

    /**
     * Checks if the preamble of ArgumentMultimap object is made of 1 part
     *
     * @return true if the preamble is by itself
     */
    public boolean hasOnlyOnePreambleSegment() {
        return (getPreambleSegmentNumber() == 1);
    }
}
