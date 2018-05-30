import java.util.ArrayList;

import sim.display.Console;

public class MainGen {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		Modele model = new Modele(System.currentTimeMillis());
		model.start();
		while(model.schedule.step(model));
		model.finish();
		/*
		Visualisation gui = new Visualisation(model);
		Console console = new Console(gui);
		console.setVisible(true);
		*/
	}
}
