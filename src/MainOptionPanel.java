import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainOptionPanel extends BorderPane {

	private static MainOptionPanel instance;
	
	private View view;
	private final Button settingsButton, infoButton;
	private final ImageView settingsOption = new ImageView(new Image(MainOptionPanel.class.getResourceAsStream("settings.png")));
	private final ImageView infoOption = new ImageView(new Image(MainOptionPanel.class.getResourceAsStream("info.png")));
	
	private MainOptionPanel(View view) {
		this.view = view;
		
		// Settings Icon
		settingsOption.setFitWidth(40);
		settingsOption.setPreserveRatio(true);
		
		// Info Icon
		infoOption.setFitWidth(40);
		infoOption.setPreserveRatio(true);
		
		// Settings Button
		settingsButton = new Button();
		settingsButton.setGraphic(settingsOption);
		settingsButton.setId("SettingsButton");
		settingsButton.setPrefSize(45, 45);
		settingsButton.setMinSize(45, 45);
		settingsButton.setStyle("-fx-background-color: #ffffff;");
		this.setLeft(settingsButton);
		
		// Settings Button Handler
		settingsButton.setOnAction(e1 -> {
			this.view.setSettingView();
		});
		
		// Info Button
		infoButton = new Button();
		infoButton.setGraphic(infoOption);
		infoButton.setId("InfoButton");
		infoButton.setPrefSize(45, 45);
		infoButton.setMinSize(45, 45);
		infoButton.setStyle("-fx-background-color: #ffffff;");
		this.setRight(infoButton);
		
		// Info Button Handler
		infoButton.setOnAction(e2 -> {
			this.view.setInfoView();
		});
	}
	
	public static synchronized MainOptionPanel getInstance(View view) {
		if (instance == null) {
			instance = new MainOptionPanel(view);
		}
		return instance;
	}
}
