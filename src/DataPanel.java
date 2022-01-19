import java.time.LocalDate;
import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class DataPanel extends GridPane {

	private static DataPanel instance;
	
	private static String missingFieldsError = "Error: Missing Fields; not all fields have been filled out!";
	private static String invalidDateError = "Error: Invalid Date; date already in a report!";
	private static String invalidTotalError = "Error: Invalid Percentages; percentage(s) don't add up to 100!";
	private static String NegativePercentError = "Error: Invalid Percentage; percentage(s) cann't be negative!"; 
	
	private TableView<Report> table;
	private HashMap<String, TableColumn<Report, Integer>> contributionColumns;
	
	private DatePicker datePicker;
	private TextField userOnePercentage, userTwoPercentage;
	private Button insert;
	private Label errorLabel;
	
	private DataPanel() {
		
		// Set alignment and gap between objects
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black;");
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setVgap(5);
		
		this.setupInfo();
	}
	
	public static synchronized DataPanel getInstance() {
		if (instance == null) {
			instance = new DataPanel();
		}
		return instance;
	}
	
	public void setupInfo() {
		// HashMap
		contributionColumns = new HashMap<String, TableColumn<Report, Integer>>();
		
		// Table setup
		table = new TableView<Report>();
		table.setEditable(false);
		table.setMinWidth(550);
		table.setPrefWidth(550);
		
		// Table Column Labels
		TableColumn<Report, LocalDate> day = new TableColumn<Report, LocalDate>("Date");
		day.setMinWidth(199);
		day.setPrefWidth(199);
		day.setResizable(false);
		day.setCellValueFactory(new PropertyValueFactory<Report, LocalDate>("date"));
		
		TableColumn<Report, Integer> userOneContribution = new TableColumn<Report, Integer>(String.format("%s's Contribution", ReportManager.getInstance().getUserOneName()));
		userOneContribution.setMinWidth(174);
		userOneContribution.setPrefWidth(174);
		userOneContribution.setResizable(false);
		userOneContribution.setCellValueFactory(new PropertyValueFactory<Report, Integer>("userOneWork"));
		contributionColumns.put("userOne", userOneContribution);
		
		TableColumn<Report, Integer> userTwoContribution = new TableColumn<Report, Integer>(String.format("%s's Contribution", ReportManager.getInstance().getUserTwoName()));
		userTwoContribution.setMinWidth(175);
		userTwoContribution.setPrefWidth(175);
		userTwoContribution.setResizable(false);
		userTwoContribution.setCellValueFactory(new PropertyValueFactory<Report, Integer>("userTwoWork"));
		contributionColumns.put("userTwo", userTwoContribution);
		
		table.getColumns().add(day);
		table.getColumns().add(userOneContribution);
		table.getColumns().add(userTwoContribution);
		
		// Table data association
		table.setItems(ReportManager.getInstance().getReports());
		
		// Date picker
		datePicker = new DatePicker();
		datePicker.setMinSize(197, 30);
		datePicker.setPrefSize(197, 30);
		datePicker.setPromptText("Date To Report");
		
		// TextFields
		userOnePercentage = new TextField();
		userOnePercentage.setMinSize(169, 30);
		userOnePercentage.setPrefSize(169, 30);
		userOnePercentage.setPromptText(String.format("%s's Work %%", ReportManager.getInstance().getUserOneName()));
		// force the alexPercentage TextField to be numeric only
		userOnePercentage.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            userOnePercentage.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		userTwoPercentage = new TextField();
		userTwoPercentage.setMinSize(172, 30);
		userTwoPercentage.setPrefSize(172, 30);
		userTwoPercentage.setPromptText(String.format("%s's Work %%", ReportManager.getInstance().getUserTwoName()));
		// Force the arthurPercentage TextField to be numeric only
		userTwoPercentage.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            userTwoPercentage.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		// Insertion controls formatting
		HBox input = new HBox(datePicker, userOnePercentage, userTwoPercentage);
		input.setSpacing(6);
		input.setAlignment(Pos.CENTER);
		
		// Insert button
		insert = new Button("Add Report");
		insert.setFont(Font.font("Lucida Sans Unicode", FontWeight.BOLD, 16));
		insert.setMinSize(550, 35);
		insert.setPrefSize(550, 35);
		insert.setOnAction(e -> {
			// Pause before removing error message
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(e1 -> {
				this.errorLabel.setText("");
			});
			
			// Check for empty fields
			if (this.datePicker.getEditor().getText().isBlank() || this.userOnePercentage.getText().isBlank() || this.userTwoPercentage.getText().isBlank()) {
				this.errorLabel.setText(missingFieldsError);
				pause.play();
			} else {
				// Get values for new Report
				LocalDate date = datePicker.getValue();
				String userOneS = userOnePercentage.getText();
				int userOnePercent = Integer.parseInt(userOneS);
				String userTwoS = userTwoPercentage.getText();
				int userTwoPercent = Integer.parseInt(userTwoS);
				
				if (ReportManager.getInstance().getReports().isEmpty()) {
					// Check if percent add up to 100
					if (userOnePercent < 0 || userTwoPercent < 0) {
						this.errorLabel.setText(DataPanel.NegativePercentError);
						pause.play();
					} else if (userOnePercent + userTwoPercent != 100) {
						this.errorLabel.setText(DataPanel.invalidTotalError);
						pause.play();
					} else {
						Report newReport = new Report(date, userOnePercent, userTwoPercent);
						ReportManager.getInstance().addReport(newReport);
						RankingPanel.getInstance().updateInfo();
						ReportManager.getInstance().saveToFile();
					}
				} else {
					// Check if date already used and if percent add up to 100
					if (!(date.isAfter(ReportManager.getInstance().getLastReport().getDate()))) {
						this.errorLabel.setText(DataPanel.invalidDateError);
						pause.play();
					} else if (userOnePercent < 0 || userTwoPercent < 0) {
						this.errorLabel.setText(DataPanel.NegativePercentError);
						pause.play();
					} else if (userOnePercent + userTwoPercent != 100) {
						this.errorLabel.setText(DataPanel.invalidTotalError);
						pause.play();
					} else {
						Report newReport = new Report(date, userOnePercent, userTwoPercent);
						ReportManager.getInstance().addReport(newReport);
						RankingPanel.getInstance().updateInfo();
						ReportManager.getInstance().saveToFile();
						
						this.datePicker.getEditor().clear();
						this.userOnePercentage.clear();
						this.userTwoPercentage.clear();
					}
				}
			}
			this.checkReportLimit();
		});
		
		// Error label
		errorLabel = new Label();
		errorLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 12));
		errorLabel.setTextFill(Color.RED);
		
		// Insertion controls formatting
		VBox insertion = new VBox(insert, errorLabel);
		insertion.setAlignment(Pos.CENTER);
		
		this.add(table, 0, 0);
		this.add(input, 0, 1);
		this.add(insertion, 0, 2);
		
		// Check report limit
		this.checkReportLimit();
	}
	
	/**
	 * Checks to see if there reports are full (day limit reached). 
	 * If reached, disable the ability to add more reports.
	 */
	protected void checkReportLimit() {
		if (ReportManager.getInstance().getReports().size() == ReportManager.getInstance().getTotalDays()) {
			this.insert.setDisable(true);
			this.datePicker.setDisable(true);
			this.userOnePercentage.setDisable(true);
			this.userTwoPercentage.setDisable(true);
		}
	}
	
	/**
	 * Updates the following information on the data panel:
	 *  - Table column names
	 *  - TextField prompt names
	 */
	protected void updateInfo() {
		this.contributionColumns.get("userOne").setText(String.format("%s's Contribution", ReportManager.getInstance().getUserOneName()));
		this.contributionColumns.get("userTwo").setText(String.format("%s's Contribution", ReportManager.getInstance().getUserTwoName()));
		
		this.userOnePercentage.setPromptText(String.format("%s's Work %%", ReportManager.getInstance().getUserOneName()));
		this.userTwoPercentage.setPromptText(String.format("%s's Work %%", ReportManager.getInstance().getUserTwoName()));
	}
}
