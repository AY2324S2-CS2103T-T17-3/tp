package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.FitAddCommandMessages;
import seedu.address.model.Model;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseSet;
import seedu.address.model.person.Person;

/**
 * Adds a new exercise or overwrites an existing exercise of a person in the address book.
 */
public class FitAddCommand extends Command {
    public static final Set<Exercise> DEFAULT_ARM_EXERCISES = new HashSet<>(Arrays.asList(
        new Exercise("bicep curls", 3, 10, 60),
        new Exercise("tricep dips", 3, 12, 60),
        new Exercise("push-ups", 3, 15, 90)
    ));

    public static final Set<Exercise> DEFAULT_LEG_EXERCISES = new HashSet<>(Arrays.asList(
        new Exercise("squats", 4, 15, 90),
        new Exercise("lunges", 3, 12, 60),
        new Exercise("calf raises", 3, 20, 60)
    ));

    public static final Set<Exercise> DEFAULT_CHEST_EXERCISES = new HashSet<>(Arrays.asList(
        new Exercise("bench press", 4, 8, 120),
        new Exercise("push-ups", 3, 15, 90),
        new Exercise("chest fly", 3, 10, 90)
    ));

    public static final Set<Exercise> DEFAULT_BACK_EXERCISES = new HashSet<>(Arrays.asList(
        new Exercise("pull-ups", 3, 8, 120),
        new Exercise("bent-over rows", 3, 10, 90),
        new Exercise("lat pull-downs", 3, 12, 60)
    ));

    public static final Set<Exercise> DEFAULT_SHOULDER_EXERCISES = new HashSet<>(Arrays.asList(
        new Exercise("shoulder press", 3, 10, 90),
        new Exercise("lateral raises", 3, 12, 60),
        new Exercise("front raises", 3, 10, 60)
    ));

    public static final Set<Exercise> DEFAULT_ABS_EXERCISES = new HashSet<>(Arrays.asList(
        new Exercise("crunches", 3, 20, 60),
        new Exercise("plank", 3, 60, 90),
        new Exercise("russian twists", 3, 15, 60)
    ));
    private final Index index;
    private final Set<Exercise> exercisesToAdd;

    /**
     * Constructs a new FitAddCommand instance.
     *
     * @param index          The index of the person in the filtered person list to add the exercise to
     * @param exercisesToAdd The set of exercises to be added to the person
     */
    public FitAddCommand(Index index, Set<Exercise> exercisesToAdd) {
        requireNonNull(index);
        requireNonNull(exercisesToAdd);

        this.index = index;
        this.exercisesToAdd = exercisesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (this.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(FitAddCommandMessages.MESSAGE_INVALID_INDEX_FITADD);
        }

        Person personToEdit = lastShownList.get(this.index.getZeroBased());

        Set<Exercise> updatedExercises = new HashSet<>(personToEdit.getExerciseSet().getValue());

        for (Exercise exerciseToAdd : this.exercisesToAdd) {
            if (updatedExercises.contains(exerciseToAdd)) {
                for (Exercise e : updatedExercises) {
                    if (e.equals(exerciseToAdd)) {
                        String name = exerciseToAdd.getName();
                        Integer sets =
                            exerciseToAdd.getSets() != Exercise.DEFAULT_SETS ? exerciseToAdd.getSets() : e.getSets();
                        Integer reps =
                            exerciseToAdd.getReps() != Exercise.DEFAULT_REPS ? exerciseToAdd.getReps() : e.getReps();
                        Integer breakBetweenSets = exerciseToAdd.getBreakBetweenSets() != Exercise.DEFAULT_BREAK
                            ? exerciseToAdd.getBreakBetweenSets() : e.getBreakBetweenSets();

                        exerciseToAdd = new Exercise(name, sets, reps, breakBetweenSets);
                        break;
                    }
                }
            }

            updatedExercises.remove(exerciseToAdd);
            updatedExercises.add(exerciseToAdd);
        }

        ExerciseSet updatedExerciseSet = new ExerciseSet(updatedExercises);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
            personToEdit.getAddress(), personToEdit.getWeights(), personToEdit.getHeight(),
            personToEdit.getNote(), personToEdit.getTags(), updatedExerciseSet);

        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(FitAddCommandMessages.MESSAGE_ADD_EXERCISE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FitAddCommand)) {
            return false;
        }

        FitAddCommand otherFitAddCommand = (FitAddCommand) other;
        return this.index.equals(otherFitAddCommand.index)
            && this.exercisesToAdd.equals(otherFitAddCommand.exercisesToAdd);
    }
}
