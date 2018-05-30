import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;
import sim.portrayal.Inspector;

public class Modele extends SimState {
	
	private IdentiteModele im = null;
	public ArrayList<Insecte> pileMorts = new ArrayList<Insecte>();
	private ArrayList<Insecte> insectesVivants = new ArrayList<Insecte>();
	
	static Constants c = new Constants(20, 20, 50, 400);
	public SparseGrid2D grille = new SparseGrid2D(c.grilleL, c.grilleH);

	public Modele(long seed) {
		super(seed);
		Insecte.c = c;
		Visualisation.constants = c;
	}
	
	public Modele(long seed, IdentiteModele im) {
		super(seed);
		this.im = im;
		Insecte.c = c;
		Visualisation.constants = c;
	}
	
	@Override
	public void start() {
		super.start();
		grille.clear();
		//pos agents
		int x, y;
		Random r = new Random();
		for(int i = 0; i < c.nInsectes; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			double modifAggro = r.nextGaussian();
			modifAggro *= 0.05;
			double aggro = 0.5;
			if(im!=null) {
				aggro = im.getAggro();
			}
			aggro += modifAggro;
			if(aggro > 1) aggro = 1;
			else if(aggro < 0.05) aggro = 0.05;
			Insecte ins = new Insecte(aggro, x, y);
			Stoppable stoppable = schedule.scheduleRepeating(ins); 
			ins.stoppable = stoppable;
			grille.setObjectLocation(ins, x, y);
			this.insectesVivants.add(ins);
		}
	}
	
	@Override
	public void finish() {
		super.finish();
	}
	
	public void hearIsDead(Insecte ins) {
		System.out.println("Un mort.");
		this.grille.remove(ins);
		this.insectesVivants.remove(ins);
		this.pileMorts.add(ins);
		if(insectesVivants.size() == 1) {
			System.out.println("Tentative fin.");
			insectesVivants.get(0).die(this);
			System.out.println("Fin.");
		}
		System.out.println("Est mort.");
	}
	
	public void end() {
		ArrayList<Insecte> aTuer = (ArrayList<Insecte>) insectesVivants.clone();
		for(Insecte i : aTuer) {
			i.die(this);
		}
	}

	public IdentiteModele getIm() {
		return im;
	}

	public void setIm(IdentiteModele im) {
		this.im = im;
	}

	public ArrayList<Insecte> getPileMorts() {
		return pileMorts;
	}

	public void setPileMorts(ArrayList<Insecte> pileMorts) {
		this.pileMorts = pileMorts;
	}

	public static Constants getC() {
		return c;
	}

	public static void setC(Constants c) {
		Modele.c = c;
	}

	public SparseGrid2D getGrille() {
		return grille;
	}

	public void setGrille(SparseGrid2D grille) {
		this.grille = grille;
	}
}
