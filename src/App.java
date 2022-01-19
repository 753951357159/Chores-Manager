import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	ReportManager model;
	View view;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set title, icon, default size, and options of window
		primaryStage.setTitle("Chores Manager");
		primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("logo.png")));
		primaryStage.setAlwaysOnTop(false);
		
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(800);
		
		// Create Model and View
		this.model = ReportManager.getInstance();
		if (!(model.emptyReportFile())) {
			this.model.readFromFile();
		}
		this.view = View.getInstance(primaryStage, model);
	}
	
	public static void main(String[] args) {
		// Start the application
		Application.launch();
	}
}
