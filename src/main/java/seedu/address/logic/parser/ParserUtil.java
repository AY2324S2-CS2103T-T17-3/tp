package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Weight;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s", "");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(Optional<String> address) throws ParseException {
        requireNonNull(address);

        String trimmedAddress = "";
        if (address.isPresent() && !address.get().isEmpty()) {
            trimmedAddress = address.get().trim();
            if (!Address.isValidAddress(trimmedAddress)) {
                throw new ParseException(Address.MESSAGE_CONSTRAINTS);
            }
        }

        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(Optional<String> email) throws ParseException {
        requireNonNull(email);

        String trimmedEmail = "";
        if (email.isPresent() && !email.get().isEmpty()) {
            trimmedEmail = email.get().trim();
            if (!Email.isValidEmail(trimmedEmail)) {
                throw new ParseException(Email.MESSAGE_CONSTRAINTS);
            }
        }

        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> note} into a {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     * If the {@code Optional} is empty, return a {@code Note} with an empty string.
     */
    public static Note parseNote(Optional<String> note) {
        requireNonNull(note);
        return note.isEmpty() ? new Note("") : new Note(note.get().trim());
    }

    /**
     * Parses a {@code Optional<Float> note} into a {@code Height}.
     * If the {@code Optional} is empty, return a {@code Height} with an uninitialized value of 0f.
     */
    public static Height parseHeight(Optional<Float> height) {
        requireNonNull(height);
        return height.isEmpty() ? new Height(0f) : new Height(height.get());
    }

    /**
     * Parses a {@code Optional<Float> note} into a {@code Weight}.
     * If the {@code Optional} is empty, return a {@code Weight} with an uninitialized value of 0f.
     */
    public static Weight parseWeight(Optional<Float> weight) {
        requireNonNull(weight);
        return weight.isEmpty() ? new Weight(0f) : new Weight(weight.get());
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code searchString} into a string.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param searchString String to search
     *
     * @return The string ready to be used for searching
     */
    public static String parseSearchString(String searchString) {
        requireNonNull(searchString);
        String trimmedAddress = searchString.trim();

        return trimmedAddress;
    }
}
