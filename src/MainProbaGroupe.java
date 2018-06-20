import java.util.ArrayList;

import sim.display.Console;
import sim.portrayal.inspector.TimeSeriesChartingPropertyInspector;

public class MainProbaGroupe {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		Constants c = new Constants(30, 30, 100, 20, 400, 0.7, 100, 100, 50, 0.8, 0.8);
		Modele model = new Modele(System.currentTimeMillis(), c);
		Visualisation gui = new Visualisation(model);
		model.setVisualisation(gui);
		Console console = new Console(gui);
		console.setVisible(true);
	}
}
