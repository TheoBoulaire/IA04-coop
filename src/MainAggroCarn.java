import java.util.ArrayList;

import sim.display.Console;
import sim.portrayal.inspector.TimeSeriesChartingPropertyInspector;

public class MainAggroCarn {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		Constants c = new Constants(30, 30, 100, 20, 400, 0.7, 30, 100, 50, 0.2, 0.5);
		Modele model = new Modele(System.currentTimeMillis(), c);
		Visualisation gui = new Visualisation(model);
		model.setVisualisation(gui);
		Console console = new Console(gui);
		console.setVisible(true);
	}
}
