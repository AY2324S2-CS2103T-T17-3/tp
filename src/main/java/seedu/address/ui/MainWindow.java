package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {
    private static MainWindow instance = null;

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private PersonDetailsPanel personDetailsPanel;
    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private StackPane personDetailsPanelPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    private MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        this.helpWindow = new HelpWindow();
    }

    /**
     * Initializes the MainWindow instance with the provided primary stage and logic.
     */
    public static void init(Stage primaryStage, Logic logic) {
        if (instance == null) {
            instance = new MainWindow(primaryStage, logic);
        }
    }


    /**
     * Returns the singleton instance of the MainWindow class.
     */
    public static MainWindow getInstance() {
        assert instance != null : "MainWindow must be initialized before getting instance.";
        return instance;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(this.helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        this.personListPanel = new PersonListPanel(this.logic.getFilteredPersonList());

        // Update the details panel when a Person in the list is selected.
        this.personListPanel.addListener((observable, oldValue, newValue) -> {
            // newValue can be null if the selection in the personListView is cleared
            if (newValue == null) {
                this.personDetailsPanel.clear();
            } else {
                this.personDetailsPanel.update(newValue);
            }
        });

        // Update the details panel when the currently displayed Person is updated.
        this.logic.getFilteredPersonList().addListener((ListChangeListener<? super Person>) c -> {
            while (c.next()) {
                // adding and editing of clients
                if (c.wasAdded()) {
                    // When the address book is initialized, the AddedSubList will contain all clients.
                    // Do not update the details panel in that case.
                    // In all other cases when a person was added/edited, the list should be of size 1.
                    if (c.getAddedSize() == 1) {
                        Person p = c.getAddedSubList().get(0);
                        this.personDetailsPanel.update(p);

                        // Select the person in the person list to ensure the details displayed is always the details
                        // of the person selected in the person list.
                        this.personListPanel.getFxmlObject().getSelectionModel().select(p);
                    }
                }
            }
        });

        this.personListPanelPlaceholder.getChildren().add(this.personListPanel.getRoot());

        this.resultDisplay = new ResultDisplay();
        this.resultDisplayPlaceholder.getChildren().add(this.resultDisplay.getRoot());

        this.personDetailsPanel = new PersonDetailsPanel();
        this.personDetailsPanelPlaceholder.getChildren().add(this.personDetailsPanel.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(this.logic.getAddressBookFilePath());
        this.statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        this.commandBox = new CommandBox(this::executeCommand);
        this.commandBoxPlaceholder.getChildren().add(this.commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        this.primaryStage.setHeight(guiSettings.getWindowHeight());
        this.primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            this.primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            this.primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!this.helpWindow.isShowing()) {
            this.helpWindow.show();
        } else {
            this.helpWindow.focus();
        }
    }

    void show() {
        this.primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(this.primaryStage.getWidth(), this.primaryStage.getHeight(),
                (int) this.primaryStage.getX(), (int) this.primaryStage.getY());

        this.commandBox.freezeCommandBox();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), exitEvent -> {
            this.logic.setGuiSettings(guiSettings);
            this.helpWindow.hide();
            this.primaryStage.hide();
        }));

        timeline.play();
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = this.logic.execute(commandText);
            this.logger.info("Result: " + commandResult.getFeedbackToUser());
            this.resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            this.logger.info("An error occurred while executing command: " + commandText);
            this.resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Sets the text of the command box.
     */
    public void setCommandBoxText(String text) {
        this.commandBox.setText(text);
    }

    /**
     * Moves the cursor of the command box to the end.
     */
    public void moveCommandBoxCursorToEnd() {
        this.commandBox.moveCursorToEnd();
    }

    public PersonDetailsPanel getPersonDetailsPanel() {
        return this.personDetailsPanel;
    }

    public void switchToExerciseTab() {
        this.personDetailsPanel.selectExerciseTab();
    }
}
