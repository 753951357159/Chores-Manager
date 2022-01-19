import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class SecondaryOptionPanel extends BorderPane {

	private static SecondaryOptionPanel instance;
	
	private View view;
	private final Button backButton;
	private final ImageView backOption = new ImageView(new Image(SecondaryOptionPanel.class.getResourceAsStream("back.png")));
	
	private SecondaryOptionPanel(View view) {
		this.view = view;
		
		// Back Icon
		backOption.setFitWidth(40);
		backOption.setPreserveRatio(true);
		
		// Back Button
		backButton = new Button();
		backButton.setGraphic(backOption);
		backButton.setId("BackButton");
		backButton.setPrefSize(45, 45);
		backButton.setMinSize(45, 45);
		backButton.setStyle("-fx-background-color: #ffffff;");
		this.setLeft(backButton);
		
		// Back Button Handler
		backButton.setOnAction(e -> {
			this.view.setMainView();
		});
	}
	
	public static synchronized SecondaryOptionPanel getInstance(View view) {
		if (instance == null) {
			instance = new SecondaryOptionPanel(view);
		}
		return instance;
	}
}
