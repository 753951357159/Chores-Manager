import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {

	private static View instance;
	
	Stage stage;
	ReportManager model;
	BorderPane borderpane;
	
	DataPanel dataPanel;							// Displays table filled with reports and can add reports
	RankingPanel rankingPanel;						// Displays each person's achievements so far
	MainOptionPanel mainOptionPanel;				// Provides options to view settings and info pages
	SecondaryOptionPanel secondaryOptionPanel;		// Provides option to go back to main page
	InfoPanel infoPanel;							// Displays application information
	SettingPanel settingpanel;						// Displays settings the user can tweak
	
	private View(Stage stage, ReportManager model) {
		this.model = model;
		this.stage = stage;
		this.initializeUI();
	}
	
	public static synchronized View getInstance(Stage stage, ReportManager model) {
		if (instance == null) {
			instance = new View(stage, model);
		}
		return instance;
	}
	
	private void initializeUI() {
		borderpane = new BorderPane();
		borderpane.setId("BorderPane");
		borderpane.setStyle("-fx-background-color: #ffffff;");
		
		// Create panels
		this.dataPanel = DataPanel.getInstance();
		this.rankingPanel = RankingPanel.getInstance();
		this.mainOptionPanel = MainOptionPanel.getInstance(this);
		this.secondaryOptionPanel = SecondaryOptionPanel.getInstance(this);
		this.infoPanel = InfoPanel.getInstance();
		this.settingpanel = SettingPanel.getInstance();
		
		// Update Information
		this.rankingPanel.updateInfo();
		this.dataPanel.updateInfo();

		// Set the main application window
		this.setMainView();
		
		// Create scene, show in window
		var scene = new Scene(borderpane);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Displays the main page.
	 */
	public void setMainView() {
		this.borderpane.setTop(rankingPanel);
		this.borderpane.setCenter(dataPanel);
		this.borderpane.setBottom(mainOptionPanel);
	}
	
	/**
	 * Displays the application information page.
	 */
	public void setInfoView() {
		this.borderpane.setTop(null);
		this.borderpane.setCenter(infoPanel);
		this.borderpane.setBottom(secondaryOptionPanel);
	}
	
	/**
	 * Displays the settings page.
	 */
	public void setSettingView() {
		this.borderpane.setTop(null);
		this.borderpane.setCenter(settingpanel);
		this.borderpane.setBottom(secondaryOptionPanel);
	}
}
