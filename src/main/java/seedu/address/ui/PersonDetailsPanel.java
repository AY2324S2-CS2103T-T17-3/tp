package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.messages.WeightCommandMessages;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.person.Person;
import seedu.address.model.person.weight.Weight;
import seedu.address.model.tag.Tag;

/**
 * A UI component that displays the details of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {
    public static final String FXML = "PersonDetailsPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(PersonDetailsPanel.class);
    private static final String EXERCISE_ATTR_DESC_STYLE = "-fx-text-fill: white; -fx-font-size: 12px;";
    private static final String EXERCISE_ATTR_VALUE_STYLE =
            "-fx-background-color: #2E2E2E; -fx-padding: 2 5 2 5; -fx-text-fill: white; -fx-font-size: 12px;";

    @FXML
    private VBox detailsPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label weightDate;
    @FXML
    private Label weightValue;
    @FXML
    private Label height;
    @FXML
    private Label note;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView qrcode;
    @FXML
    private TabPane trackableFieldsTabPane;

    private Tab weightTab;
    private Tab exerciseTab;
    private LineChart<String, Number> weightChart;
    private CategoryAxis weightXAxis;
    private NumberAxis weightYAxis;
    private VBox exercisesBox;

    /**
     * The person being displayed.
     */
    private Person person;

    /**
     * Creates a new PersonDetailsPanel and clears all fields.
     * Labels in the {@code .FXML} file contain text with the field name for
     * convenience.
     */
    public PersonDetailsPanel() {
        super(FXML);
        this.clear();
    }

    private Tab getWeightTab() {
        return this.trackableFieldsTabPane.getTabs().get(0);
    }

    private Tab getExerciseTab() {
        return this.trackableFieldsTabPane.getTabs().get(1);
    }

    /**
     * Initializes a new PersonDetailsPanel.
     */
    public void initialize() {
        // Initialize tab pane
        this.trackableFieldsTabPane.setStyle("-fx-open-tab-animation: NONE; -fx-close-tab-animation: NONE;");

        this.initializeWeightChart();
        ScrollPane exerciseScrollPane = this.generateExercisesScrollPane();

        // Add charts and notes scroll pane to respective tabs
        this.weightTab = this.getWeightTab();
        this.weightTab.setContent(this.weightChart);

        this.exerciseTab = this.getExerciseTab();
        this.exerciseTab.setContent(exerciseScrollPane);
    }

    /**
     * Updates the PersonDetailsPanel with the details of the given Person.
     *
     * @param person the Person object containing the information to update the
     *               fields with.
     */
    public void update(Person person) {
        this.person = person;
        this.detailsPane.setVisible(true);

        this.updateLabels();
        this.updateTags();
        this.updateQrCode();
        this.updateTabView();
        this.updateNodeVisibility();

        logger.info("Displayed details of person: " + person);
    }

    /**
     * Clears all fields
     */
    public void clear() {
        this.name.setText("");
        this.phone.setText("");
        this.address.setText("");
        this.email.setText("");
        this.note.setText("");
        this.weightDate.setText("");
        this.weightValue.setText("");
        this.height.setText("");
        this.tags.getChildren().clear();
        this.qrcode.setImage(null);
        this.trackableFieldsTabPane.getTabs().clear();
    }

    /**
     * Generates a weight series for a given person based on their weights.
     * Used for displaying the weight graph.
     */
    private XYChart.Series<String, Number> generateWeightSeries(Person p) {
        Float minWeight = Float.MAX_VALUE;
        Float maxWeight = Float.MIN_VALUE;

        XYChart.Series<String, Number> weightSeries = new XYChart.Series<>();

        for (Map.Entry<LocalDateTime, Weight> entry : p.getWeights().entrySet()) {
            LocalDateTime date = entry.getKey();
            Weight weight = entry.getValue();

            String dateString = date.format(DateTimeUtil.DATE_FORMATTER);
            Number weightNumber = weight.getValue();

            XYChart.Data<String, Number> weightData = new XYChart.Data<>(dateString, weightNumber);
            weightData.setNode(new HoveredThresholdNode(weightNumber.toString(), "", " kg"));
            weightSeries.getData().add(weightData);

            if (weight.getValue() < minWeight) {
                minWeight = weight.getValue();
            }
            if (weight.getValue() > maxWeight) {
                maxWeight = weight.getValue();
            }
        }

        this.weightYAxis.setLowerBound(minWeight - 10);
        this.weightYAxis.setUpperBound(maxWeight + 10);

        return weightSeries;
    }

    /**
     * Represents a custom Node for displaying data thresholds on a chart.
     * This Node displays a label with the data threshold value when hovered over.
     */
    private static class HoveredThresholdNode extends StackPane {
        private final Label label = this.createDataThresholdLabel();
        private final Node point = this.createDataPoint();

        /**
         * Creates a new HoveredThresholdNode.
         */
        public HoveredThresholdNode(String value, String prefix, String postfix) {
            this.setPrefSize(10, 10);

            this.setOnMouseEntered(event -> {
                this.getChildren().setAll(this.point, this.label);
                this.label.setText(String.format("%s%s%s", prefix, value, postfix));
                this.toFront();
            });
            this.setOnMouseExited(event -> {
                this.getChildren().setAll(this.point);
            });

            this.setStyle("-fx-background-color: white; -fx-background-radius: 5px; -fx-padding: 2px;");
            this.getChildren().setAll(this.point);
        }

        private Node createDataPoint() {
            final Circle point = new Circle(5);
            point.setFill(Color.TRANSPARENT);
            point.setStroke(Color.TRANSPARENT);
            point.setStrokeWidth(0);
            return point;
        }

        private Label createDataThresholdLabel() {
            final Label label = new Label();
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }

    /**
     * Set fields with information from the person.
     */
    private void updateLabels() {
        // Set fields with information from the person
        this.name.setText(this.person.getName().toString());
        this.phone.setText(this.person.getPhone().toString());
        this.address.setText(this.person.getAddress().toString());
        this.email.setText(this.person.getEmail().toString());
        this.height.setText(this.person.getHeight().getFormattedHeight());
        this.note.setText(this.person.getNote().toString());
        this.updateWeightLabels();
    }

    private void updateWeightLabels() {
        Optional<Map.Entry<LocalDateTime, Weight>> latestWeight = this.person.getLatestWeight();
        if (latestWeight.isPresent()) {
            LocalDate numericDate = latestWeight.get().getKey().toLocalDate();
            String formattedDate = numericDate.format(DateTimeUtil.DATE_FORMATTER);
            this.weightDate.setText(WeightCommandMessages.WEIGHT_DATE_HEADER + formattedDate);
            this.weightValue.setText(WeightCommandMessages.WEIGHT_VALUE_HEADER
                    + latestWeight.get().getValue().toString() + " kg");
        }
    }

    private void updateTags() {
        // Clear tags and set new ones
        this.tags.getChildren().clear();
        this.person.getTags().stream()
                .sorted(Comparator.comparing(Tag::toString))
                .forEach(tag -> this.tags.getChildren().add(new Label(tag.toString())));
    }

    private void updateQrCode() {
        this.qrcode.setImage(new Image(this.person.getQrCodePath().toUri().toString()));
    }

    private void updateTabView() {
        // Clear tabs
        this.trackableFieldsTabPane.getTabs().clear();

        this.updateWeightTab();
        this.updateExercisesTab();
    }

    private void updateWeightTab() {
        this.initializeWeightChart();

        Optional<Map.Entry<LocalDateTime, Weight>> latestWeight = this.person.getLatestWeight();

        if (latestWeight.isPresent()) {
            this.trackableFieldsTabPane.getTabs().add(0, this.weightTab);
            this.weightTab = this.getWeightTab();
            this.weightTab.setContent(this.weightChart);

            this.weightChart.getData().clear();
            XYChart.Series<String, Number> weightSeries = this.generateWeightSeries(this.person);
            this.weightChart.getData().add(weightSeries);
        }
    }

    private void updateExercisesTab() {
        this.initializeExercisesTab();
        this.populateExercisesTab();
    }

    /**
     * Populates the exercises tab with the given set of exercises.
     */
    private void populateExercisesTab() {
        Set<Exercise> exercises = this.person.getExerciseSet().getValue();
        if (!exercises.isEmpty()) {
            this.trackableFieldsTabPane.getTabs().add(this.exerciseTab);

            List<Exercise> sortedExercises = exercises.stream()
                    .sorted(Comparator.comparing(Exercise::getName))
                    .collect(Collectors.toList());

            for (Exercise exercise : sortedExercises) {
                Label exerciseName = this.generateExerciseNameLabel(exercise);
                HBox exerciseBox = this.generateExerciseHbox(exercise);

                this.exercisesBox.getChildren().addAll(exerciseName, exerciseBox, new Separator());
            }
        }
    }

    /**
     * Initialize the Exercises tab by setting the title label and adding it to the exercises box.
     */
    private void initializeExercisesTab() {
        Label exercisesTitle = new Label("Exercises");
        exercisesTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        exercisesTitle.setMaxWidth(Double.MAX_VALUE);
        exercisesTitle.setAlignment(Pos.CENTER);

        this.exercisesBox.getChildren().clear();
        this.exercisesBox.getChildren().add(exercisesTitle);
    }

    private Label generateExerciseNameLabel(Exercise exercise) {
        Label exerciseName = new Label(exercise.getName());

        exerciseName.setWrapText(true);
        exerciseName.setUnderline(true);
        exerciseName.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
        exerciseName.setPadding(new Insets(10, 0, 0, 0));

        return exerciseName;
    }

    private HBox generateExerciseHbox(Exercise exercise) {
        Label setsLabel = new Label("Sets:");
        setsLabel.setStyle(EXERCISE_ATTR_DESC_STYLE);
        Label setsValue = new Label(String.valueOf(exercise.getSets()));
        setsValue.setStyle(EXERCISE_ATTR_VALUE_STYLE);

        Label repsLabel = new Label("Reps:");
        repsLabel.setStyle(EXERCISE_ATTR_DESC_STYLE);
        Label repsValue = new Label(String.valueOf(exercise.getReps()));
        repsValue.setStyle(EXERCISE_ATTR_VALUE_STYLE);

        Label breakLabel = new Label("Break between sets:");
        breakLabel.setStyle(EXERCISE_ATTR_DESC_STYLE);
        Label breakValue = new Label(exercise.getBreakBetweenSets() + " seconds");
        breakValue.setStyle(EXERCISE_ATTR_VALUE_STYLE);

        HBox setsBox = new HBox(10, setsLabel, setsValue);
        HBox repsBox = new HBox(10, repsLabel, repsValue);
        HBox breakBox = new HBox(10, breakLabel, breakValue);

        setsBox.setPadding(new Insets(10, 0, 10, 0));
        repsBox.setPadding(new Insets(10, 0, 10, 0));
        breakBox.setPadding(new Insets(10, 0, 10, 0));

        setsBox.setPrefWidth(130);
        repsBox.setPrefWidth(130);
        breakBox.setPrefWidth(250);

        return new HBox(setsBox, repsBox, breakBox);
    }

    /**
     * Update the visibility of nodes based on the presence of valid values in the person object.
     */
    private void updateNodeVisibility() {
        Optional<Map.Entry<LocalDateTime, Weight>> latestWeight = this.person.getLatestWeight();

        // Set visibility based on presence of valid values
        this.address.setVisible(!this.person.getAddress().getValue().isEmpty());
        this.email.setVisible(!this.person.getEmail().getValue().isEmpty());
        this.note.setVisible(!this.person.getNote().getValue().isEmpty());
        this.weightDate.setVisible(latestWeight.isPresent());
        this.weightValue.setVisible(latestWeight.isPresent());
        this.height.setVisible(this.person.getHeight().isValid());

        // Bind visibility of nodes to managed properties
        this.address.managedProperty().bind(this.address.visibleProperty());
        this.email.managedProperty().bind(this.email.visibleProperty());
        this.weightDate.managedProperty().bind(this.weightDate.visibleProperty());
        this.weightValue.managedProperty().bind(this.weightValue.visibleProperty());
        this.height.managedProperty().bind(this.height.visibleProperty());
        this.note.managedProperty().bind(this.note.visibleProperty());

    }

    private void initializeWeightChart() {
        // Initialize weight chart
        this.weightXAxis = new CategoryAxis();
        this.weightYAxis = new NumberAxis();

        this.weightXAxis.setAnimated(false); // fixes the collapsed categories bug
        this.weightXAxis.setLabel("Date");
        this.weightXAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");

        this.weightYAxis.setAnimated(false);
        this.weightYAxis.setAutoRanging(false);
        this.weightYAxis.setLabel("Weight (kg)");
        this.weightYAxis.lookup(".axis-label").setStyle("-fx-text-fill: white;");

        this.weightChart = new LineChart<>(this.weightXAxis, this.weightYAxis);
        this.weightChart.setAnimated(false);
        this.weightChart.setHorizontalGridLinesVisible(false);
        this.weightChart.setVerticalGridLinesVisible(false);
        this.weightChart.setTitle("Weight Tracking");
        this.weightChart.setLegendVisible(false);
        this.weightChart.lookup(".chart-title").setStyle("-fx-text-fill: white;");
        this.weightChart.setPrefHeight(200);
        this.weightChart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: white;");
        this.weightChart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: white;");
        this.weightChart.requestLayout();
    }

    private ScrollPane generateExercisesScrollPane() {
        // Initialize exercises box
        this.exercisesBox = new VBox();
        this.exercisesBox.setPadding(new Insets(10, 10, 10, 10));
        this.exercisesBox.setPrefHeight(200);

        // Create a scroll pane and set the exercises box as its content
        ScrollPane exerciseScrollPane = new ScrollPane(this.exercisesBox);
        exerciseScrollPane.setFitToWidth(true);
        exerciseScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        exerciseScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return exerciseScrollPane;
    }
}
