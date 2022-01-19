import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RankingPanel extends GridPane {

	private static RankingPanel instance;
	
	private Label title = new Label("CURRENT STANDINGS");
	private Label daysWorked = new Label();
	
	private Label userOneName = new Label();
	private Label userOneWork = new Label();
	private Label userOneCash = new Label();
	
	private Label userTwoName = new Label();
	private Label userTwoWork = new Label();
	private Label userTwoCash = new Label();
	
	private Label receivedCash = new Label();
	
	private RankingPanel() {
		// Set alignment and gap between objects
		this.setAlignment(Pos.CENTER);
		this.setHgap(25);
		this.setVgap(5);
		this.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black;");
		this.setPadding(new Insets(10, 10, 10, 10));
		
		// Creating column constraints
		ColumnConstraints cc = new ColumnConstraints();
		cc.setMinWidth(250);
		cc.setPrefWidth(250);
		cc.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(cc);
		this.getColumnConstraints().add(cc);
		
		this.setupInfo();
	}
	
	public static synchronized RankingPanel getInstance() {
		if (instance == null) {
			instance = new RankingPanel();
		}
		return instance;
	}
	
	private void setupInfo() {
		// Main title
		title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
		title.setTextFill(Color.DARKBLUE);
		
		// Sub title
		daysWorked.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
		daysWorked.setTextFill(Color.BLACK);
		daysWorked.setText(String.format("Days Worked: %s / %s", ReportManager.getInstance().getReports().size(), ReportManager.getInstance().getTotalDays()));
		
		// Title VBox
		VBox titles = new VBox(title, daysWorked);
		titles.setSpacing(2);
		titles.setAlignment(Pos.CENTER);
		this.add(titles,  0,  0, 2, 1);
		
		// User one statistics
		userOneName.setFont(Font.font("Comic Sans MS", FontWeight.MEDIUM, 20));
		userOneName.setTextFill(Color.CADETBLUE);
		userOneName.setText(ReportManager.getInstance().getUserOneName());
		
		userOneWork.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 16));
		userOneWork.setTextFill(Color.BLACK);
		userOneWork.setText(String.format("%.0f %%", ReportManager.getInstance().getUserOneWork()));
		
		userOneCash.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 16));
		userOneCash.setTextFill(Color.DARKOLIVEGREEN);
		userOneCash.setText(String.format("$ %.2f", ReportManager.getInstance().getUserOneCash()));
		
		this.add(userOneName, 0, 3);
		this.add(userOneWork, 0, 4);
		this.add(userOneCash, 0, 5);
		
		// User two statistics
		userTwoName.setFont(Font.font("Comic Sans MS", FontWeight.MEDIUM, 20));
		userTwoName.setTextFill(Color.CADETBLUE);
		userTwoName.setText(ReportManager.getInstance().getUserTwoName());
		
		userTwoWork.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 16));
		userTwoWork.setTextFill(Color.BLACK);
		userTwoWork.setText(String.format("%.0f %%", ReportManager.getInstance().getUserTwoWork()));
		
		userTwoCash.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 16));
		userTwoCash.setTextFill(Color.DARKOLIVEGREEN);
		userTwoCash.setText(String.format("$ %.2f", ReportManager.getInstance().getUserTwoCash()));
		
		this.add(userTwoName, 1, 3);
		this.add(userTwoWork, 1, 4);
		this.add(userTwoCash, 1, 5);
		
		// Total cash label
		receivedCash.setFont(Font.font("Impact", FontWeight.NORMAL, 20));
		receivedCash.setTextFill(Color.ORANGE);
		receivedCash.setText(String.format("$ %.2f / $ %.2f", ReportManager.getInstance().getCurrentAmount(), ReportManager.getInstance().getTotalAmount()));
		
		this.add(receivedCash, 0, 10, 2, 1);
	}
	
	/**
	 * Updates the following information on the ranking panel:
	 *  - User 1's name
	 *  - User 1's contribution amount (%)
	 *  - User 1's cash gain ($)
	 *  - User 2's name
	 *  - User 2's contribution amount(%)
	 *  - User 2's cash gain ($)
	 *  - Total cash amount ($)
	 */
	protected void updateInfo() {
		daysWorked.setText(String.format("Days Worked: %s / %s", ReportManager.getInstance().getReports().size(), ReportManager.getInstance().getTotalDays()));
		userOneName.setText(ReportManager.getInstance().getUserOneName());
		userOneWork.setText(String.format("%.0f %%", ReportManager.getInstance().getUserOneWork()));
		userOneCash.setText(String.format("$ %.2f", ReportManager.getInstance().getUserOneCash()));
		userTwoName.setText(ReportManager.getInstance().getUserTwoName());
		userTwoWork.setText(String.format("%.0f %%", ReportManager.getInstance().getUserTwoWork()));
		userTwoCash.setText(String.format("$ %.2f", ReportManager.getInstance().getUserTwoCash()));
		receivedCash.setText(String.format("$ %.2f / $ %.2f", ReportManager.getInstance().getCurrentAmount(), ReportManager.getInstance().getTotalAmount()));
	}
}
