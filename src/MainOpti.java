import java.util.ArrayList;

import sim.display.Console;
import sim.portrayal.inspector.TimeSeriesChartingPropertyInspector;

public class MainOpti {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		Modele model = new Modele(System.currentTimeMillis(), 1000);
		ArrayList<Integer> ti = new ArrayList<Integer>();
		ti.add(3);
		ti.add(4);
		ti.add(1);
		model.setTypeInsectes(ti);
		Visualisation gui = new Visualisation(model);
		Console console = new Console(gui);
		console.setVisible(true);
	}
}
