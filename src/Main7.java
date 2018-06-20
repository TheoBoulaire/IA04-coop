import java.util.ArrayList;

import sim.display.Console;
import sim.portrayal.inspector.TimeSeriesChartingPropertyInspector;

public class Main7 {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		Constants c = new Constants(20, 20, 10, 3, 400, 0.2, 5, 15);
		Modele model = new Modele(System.currentTimeMillis(), c);
		Visualisation gui = new Visualisation(model);
		Console console = new Console(gui);
		console.setVisible(true);
	}
}
