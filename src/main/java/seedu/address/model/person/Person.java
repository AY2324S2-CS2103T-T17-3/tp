package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.messages.PersonMessages.MESSAGE_CANT_DELETE_QR;
import static seedu.address.model.person.messages.PersonMessages.MESSAGE_DELETED_QR;
import static seedu.address.model.person.messages.PersonMessages.MESSAGE_DOES_NOT_EXIST;
import static seedu.address.model.person.messages.PersonMessages.MESSAGE_GENERATED_QR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import com.google.zxing.WriterException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.QrCodeGenerator;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.exceptions.AttributeNotFoundException;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.person.weight.WeightMap;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagSet;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Person {
    private static final Logger logger = LogsCenter.getLogger(Person.class);
    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    // Data fields
    private final Address address;
    private final WeightMap weights;
    private final Height height;
    private final Note note;
    private final TagSet tags;
    private final ExerciseSet exerciseSet;

    /**
     * Every field must be present and not null.
     * @param name The name of the person to be created.
     * @param phone The phone number of the person to be created.
     * @param email The email of the person to be created.
     * @param address The address of the person to be created.
     * @param weights The weight value of the person to be created.
     * @param height The height value of the person to be created.
     * @param note Any note associated with the person to be created.
     * @param tags Any tags associated with the person to be created.
     * @param exerciseSet The exercise(s) of the person to be created.
     */
    public Person(Name name, Phone phone, Email email, Address address, NavigableMap<LocalDateTime, Weight> weights,
            Height height, Note note, Set<Tag> tags, ExerciseSet exerciseSet) {
        requireAllNonNull(name, phone, email, address, weights, height, note, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.weights = new WeightMap(weights);
        this.height = height;
        this.note = note;
        Set<Tag> tagSet = new HashSet<>();
        tagSet.addAll(tags);
        this.tags = new TagSet(tagSet);
        this.exerciseSet = exerciseSet;
    }

    /**
     * Gets the value of the specified attribute.
     *
     * @param attribute Attribute to retrieve
     * @return Value of the specified attribute
     */
    public Attribute<? extends Object> getAttribute(PersonAttribute attribute) {
        switch (attribute) {
        case NAME:
            return this.name;
        case PHONE:
            return this.phone;
        case EMAIL:
            return this.email;
        case ADDRESS:
            return this.address;
        case NOTE:
            return this.note;
        case WEIGHT:
            return this.weights;
        case HEIGHT:
            return this.height;
        case TAGS:
            return this.tags;
        case EXERCISES:
            return this.exerciseSet;

        default:
            throw new AttributeNotFoundException();
        }
    }

    public Name getName() {
        return this.name;
    }

    public Phone getPhone() {
        return this.phone;
    }

    public Email getEmail() {
        return this.email;
    }

    public Address getAddress() {
        return this.address;
    }

    public Optional<Map.Entry<LocalDateTime, Weight>> getLatestWeight() {
        return Optional.ofNullable(this.weights.getValue().lastEntry());
    }

    /**
     * Returns an immutable navigable map, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     *
     * @return A {@code NavigableMap} containing the date and weight key-value entries.
     */
    public NavigableMap<LocalDateTime, Weight> getWeights() {
        return this.weights.getValue();
    }

    public Height getHeight() {
        return this.height;
    }

    public Note getNote() {
        return this.note;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return this.tags.getValue();
    }

    public ExerciseSet getExerciseSet() {
        return this.exerciseSet;
    }

    /**
     * Retrieves the QR code path.
     *
     * @return the path of the generated QR code
     */
    public Path getQrCodePath() {
        return QrCodeGenerator.getQrCodePath(this);
    }

    /**
     * Generates a QR code for the person.
     */
    public void generateQrCode() {
        try {
            QrCodeGenerator.generateQrCode(this);
            logger.info(MESSAGE_GENERATED_QR + this);
        } catch (WriterException | IOException e) {
            logger.warning("Unable to generate QR code for " + this);
        }
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getAttribute(PersonAttribute.NAME).equals(getAttribute(PersonAttribute.NAME))
                && otherPerson.getAttribute(PersonAttribute.PHONE).equals(getAttribute(PersonAttribute.PHONE));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return this.name.equals(otherPerson.name)
                && this.phone.equals(otherPerson.phone)
                && this.email.equals(otherPerson.email)
                && this.address.equals(otherPerson.address)
                && this.tags.equals(otherPerson.tags)
                && this.note.equals(otherPerson.note)
                && this.exerciseSet.equals(otherPerson.exerciseSet);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.name, this.phone, this.email, this.address, this.tags, this.exerciseSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", this.name)
                .add("phone", this.phone)
                .add("email", this.email)
                .add("address", this.address)
                .add("note", this.note)
                .add("tags", this.tags)
                .toString();
    }

    /**
     * Deletes the QR code for the person.
     *
     * @return true if a QR code was deleted, false otherwise
     */
    public boolean deleteQrCode() {
        try {
            boolean result = Files.deleteIfExists(this.getQrCodePath());
            if (result) {
                logger.info(MESSAGE_DELETED_QR + this);
            } else {
                logger.info(String.format(MESSAGE_DOES_NOT_EXIST, this));
            }
            return result;
        } catch (IOException e) {
            logger.warning(String.format(MESSAGE_CANT_DELETE_QR, this));
            return false;
        }
    }

    /**
     * Generates a formatted message for the Person.
     * Only fields with values are included.
     *
     * @return A formatting message containing the details of this instance of Person.
     */
    public String getFormattedMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(this.name);
        sb.append(" | Phone: ").append(this.phone);

        if (!this.email.getValue().isEmpty()) {
            sb.append(" | Email: ").append(this.email);
        }

        if (!this.address.getValue().isEmpty()) {
            sb.append("\nAddress: ").append(this.address);
        }

        if (!this.note.getValue().isEmpty()) {
            sb.append(" | Note: ").append(this.note);
        }

        if (!this.weights.getValue().isEmpty()) {
            sb.append(" | Latest Weight: ").append(this.getLatestWeight().get().getValue().toString());
        }

        if (!(this.height.isZero())) {
            sb.append(" | Height: ").append(this.height);
        }

        if (!this.getTags().isEmpty()) {
            sb.append(" | Tags: ").append(this.tags);
        }

        return sb.toString();
    }

    /**
     * Attributes of a Person
     */
    public enum PersonAttribute {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        WEIGHT,
        HEIGHT,
        NOTE,
        TAGS,
        EXERCISES
    }
}
