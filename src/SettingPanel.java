import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SettingPanel extends GridPane {

	private static SettingPanel instance;
	
	private static String successfulSave = "Changes have been successfully saved...";
	private static String noChangesError = "No changes have been made...";
	
	private Label title = new Label("SETTINGS");
	
	private Label userOne = new Label("User 1 Name:");
	private TextField newOne = new TextField();
	
	private Label userTwo = new Label("User 2 Name:");
	private TextField newTwo = new TextField();
	
	private Label days = new Label("Set New Total Work Days:");
	private TextField newDaysAmount = new TextField();
	
	private Label amount = new Label("Set New Total Amount:");
	private Label dollar = new Label("$");
	private TextField newCashAmount = new TextField();
	
	private Label reset = new Label("Reset All Data");
	private CheckBox resetData = new CheckBox();
	
	private Button apply = new Button("Apply");
	private Label messages = new Label();
	
	private SettingPanel() {		
		// Set alignment and gap between cells
		this.setAlignment(Pos.TOP_CENTER);
		this.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black;");
		this.setHgap(25);
		this.setVgap(50);
		this.setPadding(new Insets(10, 10, 10, 10));
		
		// Creating column constraints
		ColumnConstraints cc = new ColumnConstraints();
		cc.setMinWidth(400);
		cc.setPrefWidth(400);
		cc.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(cc);
		
		this.setupInfo();
	}
	
	public static synchronized SettingPanel getInstance() {
		if (instance == null) {
			instance = new SettingPanel();
		}
		return instance;
	}
	
	private void setupInfo() {
		// Title Label
		title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
		title.setTextFill(Color.DARKBLUE);
		this.add(title, 0, 0);
		
		// User one Label
		userOne.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
		userOne.setTextFill(Color.BLACK);
		
		// User one TextField
		newOne.setPrefSize(160, 25);
		newOne.setMinSize(160, 25);
		newOne.setPromptText("New User 1 Name");
		
		// User two Label
		userTwo.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
		userTwo.setTextFill(Color.BLACK);
		
		// User two TextField
		newTwo.setPrefSize(160, 25);
		newTwo.setMinSize(160, 25);
		newTwo.setPromptText("New User 2 Name");
		
		// New Days Label
		days.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
		days.setTextFill(Color.BLACK);
		
		// New Days TextField
		newDaysAmount.setPrefSize(160, 25);
		newDaysAmount.setMinSize(160, 25);
		newDaysAmount.setPromptText("New Work Days Amount");
		// Force the newDaysAmount TextField to be numeric only
				newDaysAmount.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
				    @Override
				    public void changed(ObservableValue<? extends String> observable, String oldValue, 
				        String newValue) {
				        if (!newValue.matches("\\d*")) {
				            newDaysAmount.setText(newValue.replaceAll("[^\\d]", ""));
				        }
					}
				});
		
		// New Amount Label
		amount.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
		amount.setTextFill(Color.BLACK);
		
		// Dollar Label
		dollar.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));
		dollar.setTextFill(Color.BLACK);
		
		// New Amount TextField
		newCashAmount.setPrefSize(150, 25);
		newCashAmount.setMinSize(150, 25);
		newCashAmount.setPromptText("New Cash Amount");
		// Force the newCashAmount TextField to be numeric only
		newCashAmount.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            newCashAmount.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
		// AmountAll HBox
		HBox amountAll = new HBox(dollar, newCashAmount);
		amountAll.setSpacing(5);
		
		// CheckBox Label
		reset.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
		reset.setTextFill(Color.BLACK);
		
		// CheckBox
		resetData.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
		resetData.setPrefHeight(20);
		
		// Options GridPane
		GridPane options = new GridPane();
		options.setAlignment(Pos.BOTTOM_LEFT);
		options.setVgap(10);
		options.setHgap(25);
		
		options.add(userOne, 0, 0);
		options.add(newOne, 1, 0);
		options.add(userTwo, 0, 1);
		options.add(newTwo, 1, 1);	
		options.add(days, 0, 2);
		options.add(newDaysAmount, 1, 2);
		options.add(amount, 0, 3);
		options.add(amountAll, 1, 3);
		options.add(reset, 0, 7);
		options.add(resetData, 1, 7);

		
		this.add(options, 0, 1);
		
		// Apply Button
		apply.setPrefSize(400, 30);
		apply.setMinSize(400, 30);
		
		// Apply Button Handler
		apply.setOnAction(e -> {
			PauseTransition pause = new PauseTransition(Duration.seconds(2));
			pause.setOnFinished(e1 -> {
				this.messages.setText("");
			});
			
			if (!this.resetData.isSelected() && this.newDaysAmount.getText().isBlank() && this.newCashAmount.getText().isBlank() && this.newOne.getText().isBlank() && this.newTwo.getText().isBlank()) {
				this.messages.setTextFill(Color.RED);
				this.messages.setText(noChangesError);
				pause.play();
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
				alert.setHeaderText("Are you sure you want to apply the selected changes?");
				alert.setTitle("Confirmation");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image(SettingPanel.class.getResourceAsStream("logo.png")));
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.YES) {
					
					// Check for reset
					if (resetData.isSelected()) {
						ReportManager.getInstance().setDefault();
						ReportManager.getInstance().saveToFile();
						RankingPanel.getInstance().updateInfo();
						DataPanel.getInstance().updateInfo();
					}
					
					// Check for day change
					if (!newDaysAmount.getText().isBlank()) {
						ReportManager.getInstance().setTotalDays(Integer.parseInt(newDaysAmount.getText()));
						ReportManager.getInstance().updateStatistics();
						ReportManager.getInstance().saveToFile();
						RankingPanel.getInstance().updateInfo();
						DataPanel.getInstance().checkReportLimit();
					}
					
					// Check for amount change
					if (!newCashAmount.getText().isBlank()) {
						ReportManager.getInstance().setTotalAmount(Float.parseFloat(newCashAmount.getText()));
						ReportManager.getInstance().updateStatistics();
						ReportManager.getInstance().saveToFile();
						RankingPanel.getInstance().updateInfo();
					}
					
					// Check for user 1 name change
					if (!newOne.getText().isBlank()) {
						ReportManager.getInstance().setUserOneName(newOne.getText());
						ReportManager.getInstance().saveToFile();
						RankingPanel.getInstance().updateInfo();
						DataPanel.getInstance().updateInfo();
					}
					
					// Check for user 2 name change
					if (!newTwo.getText().isBlank()) {
						ReportManager.getInstance().setUserTwoName(newTwo.getText());
						ReportManager.getInstance().saveToFile();
						RankingPanel.getInstance().updateInfo();
						DataPanel.getInstance().updateInfo();
					}
					
					// Reset options
					this.newDaysAmount.clear();
					this.newCashAmount.clear();
					this.newOne.clear();
					this.newTwo.clear();
					this.resetData.setSelected(false);
					
					// Success message
					this.messages.setTextFill(Color.GREEN);
					this.messages.setText(successfulSave);
					pause.play();
				}
			}
		});
		
		// Messages Label
		messages.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 12));
		
		// Apply VBox
		VBox applyBox = new VBox(apply, messages);
		applyBox.setAlignment(Pos.CENTER);
		
		this.add(applyBox, 0, 8);
	}
}
