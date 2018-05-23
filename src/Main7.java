import java.util.ArrayList;

import sim.display.Console;
import sim.portrayal.inspector.TimeSeriesChartingPropertyInspector;

public class Main7 {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		Modele model = new Modele(System.currentTimeMillis());
		Visualisation gui = new Visualisation(model);
		Console console = new Console(gui);
		console.setVisible(true);
	}
}
