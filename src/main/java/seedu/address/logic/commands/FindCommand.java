package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_CLIENTS_FOUND_OVERVIEW;
import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_NO_CLIENTS_FOUND;
import static seedu.address.logic.messages.FindCommandMessages.MESSAGE_ONE_CLIENT_FOUND;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.predicates.CombinedPredicates;

/**
 * Finds and lists all persons in address book whose name contains any of the
 * argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {
    /** Represents a regular expression to test for a valid search range input. */
    public static final String VALIDATION_REGEX_RANGE = "^\\d+(\\.\\d+)?,\\s*\\d+(\\.\\d+)?$";
    private final CombinedPredicates predicates;

    /**
     * Constructs a FindCommand object with the predicates we want
     * to use in the search
     *
     * @param predicates the search information
     */
    public FindCommand(CombinedPredicates predicates) {
        this.predicates = predicates;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidRange(String test) {
        return test.matches(VALIDATION_REGEX_RANGE);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(this.predicates);
        int listSize = model.getFilteredPersonList().size();

        if (listSize == 0) {
            return new CommandResult(MESSAGE_NO_CLIENTS_FOUND);
        }

        if (listSize == 1) {
            return new CommandResult(MESSAGE_ONE_CLIENT_FOUND);
        }

        return new CommandResult(
                String.format(MESSAGE_CLIENTS_FOUND_OVERVIEW, listSize));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return this.predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicates", this.predicates)
                .toString();
    }
}
