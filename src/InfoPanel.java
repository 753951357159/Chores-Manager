import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class InfoPanel extends GridPane {

	private static InfoPanel instance;
	
	private final ImageView logoImg = new ImageView(new Image(InfoPanel.class.getResourceAsStream("logo.png")));
	private final Text appName = new Text("Dish Washing Manager");
	private final Text ver = new Text("Version 0.1.0");
	private final Text dev = new Text("Created using Java\nDeveloped by Alexander Feng");
	
	private InfoPanel() {
		// Set alignment and gap between cells
		this.setAlignment(Pos.TOP_LEFT);
		this.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black;");
		this.setHgap(10);
		this.setPadding(new Insets(10, 10, 10, 10));
		
		this.setupInfo();
	}
	
	public static synchronized InfoPanel getInstance() {
		if (instance == null) {
			instance = new InfoPanel();
		}
		return instance;
	}
	
	private void setupInfo() {
		// Icon
		logoImg.setFitWidth(125);
		logoImg.setPreserveRatio(true);
		this.add(logoImg,  0,  0, 1, 3);
		
		// Application Name
		appName.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 26));
		appName.setFill(Color.CADETBLUE);
		this.add(appName, 1, 0);
		
		// Version
		ver.setFont(Font.font("Helvetica", FontWeight.NORMAL, 20));
		ver.setFill(Color.BLACK);
		this.add(ver,  1,  1);
		
		// Development
		dev.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
		dev.setFill(Color.BLACK);
		this.add(dev,  1,  2);
	}
}
