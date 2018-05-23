import java.util.ArrayList;
import java.util.Stack;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;

public class Modele extends SimState {
	
	private static final long serialVersionUID = 2598273691291778150L;
	
	static Constants c = new Constants(20, 20, 10, 400);
	public SparseGrid2D grille = new SparseGrid2D(c.grilleL, c.grilleH);
	public Stack<Double> aggroMorts = new Stack<Double>();

	public Modele(long seed) {
		super(seed);
		Agent.c = c;
		Visualisation.constants = c;
	}
	
	@Override
	public void start() {
		super.start();
		grille.clear();
		//pos agents
		int x, y;
		for(int i = 0; i < c.nInsectes; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			Insecte ins = new Insecte(Math.random(), x, y);
			Stoppable stoppable = schedule.scheduleRepeating(ins); 
			ins.stoppable = stoppable;
			grille.setObjectLocation(ins, x, y);
		}
		
		for(int i = 0; i < 4; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			Groupe groupe = new Groupe(Math.random(), x, y);
			Stoppable stoppable = schedule.scheduleRepeating(groupe); 
			groupe.stoppable = stoppable;
			grille.setObjectLocation(groupe, x, y);
		}
		
		
	}
	
	@Override
	public void finish() {
		System.out.println(this.aggroMorts);
	}
}
