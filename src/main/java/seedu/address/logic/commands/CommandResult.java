package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.logic.messages.FitDeleteCommandMessages;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    public String getFeedbackToUser() {
        return this.feedbackToUser;
    }

    public boolean isShowHelp() {
        return this.showHelp;
    }

    public boolean isExit() {
        return this.exit;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return this.feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && this.showHelp == otherCommandResult.showHelp
                && this.exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.feedbackToUser, this.showHelp, this.exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", this.feedbackToUser)
                .add("showHelp", this.showHelp)
                .add("exit", this.exit)
                .toString();
    }

    /**
     * Check if the feedback to the user contains the success message for adding an exercise.
     */
    public boolean isFromFitAddCommand() {
        return this.getFeedbackToUser().contains(FitAddCommandMessages.MESSAGE_ADD_EXERCISE_SUCCESS);
    }

    /**
     * Determines if the feedback to the user indicates a FitDeleteCommand action.
     */
    public boolean isFromFitDeleteCommand() {
        boolean isDeleteAll =
                this.getFeedbackToUser().contains(FitDeleteCommandMessages.MESSAGE_DELETE_ALL_EXERCISES_SUCCESS);

        String startOfDeleteExerciseSuccess = FitDeleteCommandMessages.MESSAGE_DELETE_EXERCISE_SUCCESS.substring(0, 29);
        boolean isDelete = this.getFeedbackToUser().startsWith(startOfDeleteExerciseSuccess);

        return isDelete || isDeleteAll;
    }
}
