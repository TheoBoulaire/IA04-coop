import java.util.ArrayList;
import java.util.Stack;

import sim.display.Console;
import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;
import sim.portrayal.Inspector;

public class ModeleFactory {
	
	public ArrayList<IdentiteModele> ids = new ArrayList<IdentiteModele>();
	
	public Modele createModele() {
		Modele model = new Modele(System.currentTimeMillis());
		return model;
	}
	
	public Modele createModele(Modele pere) {
		IdentiteModele im = newModelId(pere);
		Modele model = new Modele(System.currentTimeMillis(), im);
		return model;
	}
	
	public IdentiteModele chainModeles(int n) {
		System.out.println("Itération n°0");
		Modele model0 = createModele();
		model0.start();
		while(model0.schedule.step(model0));
		for(int i = 1; i < n; i++) {
			System.out.println("Itération n°" + i);
			Modele model1 = createModele(model0);
			ids.add(model0.getIm());
			model0.finish();
			model1.start();
			while(model1.schedule.step(model1));
			model0 = model1;
		}
		IdentiteModele im = model0.getIm();
		ids.add(im);
		model0.finish();
		return im;
	}
	
	private IdentiteModele newModelId(Modele m) {
		int t = m.pileMorts.size();
		double sAggro = 0;
		int i = 1;
		for(Insecte ins : m.pileMorts) {
			sAggro += (ins.getAggro()*i)/t;
			i++;
		}
		double moyAggro = sAggro / ((t+1)/2.0);
		IdentiteModele im = new IdentiteModele(moyAggro);
		return im;
	}
}
