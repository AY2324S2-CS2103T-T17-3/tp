package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        this.descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing
     * {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        this.descriptor = new EditPersonDescriptor();
        this.descriptor.setName(person.getName());
        this.descriptor.setPhone(person.getPhone());
        this.descriptor.setEmail(person.getEmail());
        this.descriptor.setAddress(person.getAddress());
        this.descriptor.setWeight(person.getLatestWeight().get().getValue());
        this.descriptor.setHeight(person.getHeight());
        this.descriptor.setNote(person.getNote());
        this.descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        this.descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        this.descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        this.descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        this.descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withWeight(Float weight) {
        this.descriptor.setWeight(new Weight(weight));
        return this;
    }

    /**
     * Sets the {@code Height} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withHeight(Float height) {
        this.descriptor.setHeight(new Height(height));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withNote(String note) {
        this.descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the
     * {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        this.descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return this.descriptor;
    }
}
