import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;

public class Modele extends SimState {
	
	private static final long serialVersionUID = 2598273691291778150L;
	
	static Constants c = new Constants(20, 20, 10, 400, 50, 3, 5, 15);
	private IdentiteModele im = null;
	public SparseGrid2D grille = new SparseGrid2D(c.grilleL, c.grilleH);
	public Stack<Double> aggroMorts = new Stack<Double>();
	public ArrayList<Insecte> pileMorts = new ArrayList<Insecte>();
	private ArrayList<Insecte> insectesVivants = new ArrayList<Insecte>();

	public Modele(long seed) {
		super(seed);
		Agent.c = c;
		Visualisation.constants = c;
	}
	
	public Modele(long seed, IdentiteModele im) {
		super(seed);
		this.im = im;
		Insecte.c = c;
		Visualisation.constants = c;
	}
	
	private double createRandAggro(Random r) {
		double modifAggro = r.nextGaussian();
		modifAggro *= 0.05;
		double aggro = 0.5;
		if(im!=null) {
			aggro = im.getAggro();
		}
		aggro += modifAggro;
		if(aggro > 1) 
			aggro = 1;
		else if(aggro < 0.05) 
			aggro = 0.05;
		return aggro;
	}
	
	private ArrayList<Double> createRandAggroTab(Random r) {
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int i = 0; i < 10; i++) {
			double modifAggro = r.nextGaussian();
			modifAggro *= 0.05;
			double aggro = 0.5;
			if(im!=null) {
				aggro = im.getAggro();
			}
			aggro += modifAggro;
			if(aggro > 1) 
				aggro = 1;
			else if(aggro < 0.05) 
				aggro = 0.05;
			ret.add(aggro);
		}
		return ret;
	}
	
	
	@Override
	public void start() {
		super.start();
		grille.clear();
		//pos agents
		int x, y;
		int identite;
		double aggro;
		double strength;
		ArrayList<Double> aggroTab;
		Random r = new Random();
		for(int i = 0; i < c.nInsectes - 2; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			identite = (int) Math.floor(Math.random()*10);
			strength = identite*3;
//			aggro = (double)identite/10;
			
			aggro = createRandAggro(r);
			aggroTab = createRandAggroTab(r);
			/*
			System.out.println("identite = " + identite);
			System.out.println("aggro = " + aggro);
			System.out.println("strength = " + strength + "\n");
			*/
			
			InsecteHerb ins = new InsecteHerb(x, y, this, identite, aggro, aggroTab, strength, c.maxEnergy, 100);
			Stoppable stoppable = schedule.scheduleRepeating(ins); 
			ins.stoppable = stoppable;
			grille.setObjectLocation(ins, x, y);
			this.insectesVivants.add(ins);
		}
		
		for(int i = 0; i < 2; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			identite = (int) Math.floor(Math.random()*10);
			strength = identite*3;
//			aggro = (double)identite/10;
			
			aggro = createRandAggro(r);
			aggroTab = createRandAggroTab(r);
			/*
			System.out.println("identite = " + identite);
			System.out.println("aggro = " + aggro);
			System.out.println("strength = " + strength + "\n");
			*/
			
			InsecteCarn ins = new InsecteCarn(x, y, this, identite, aggro, aggroTab, strength, c.maxEnergy, 100);
			Stoppable stoppable = schedule.scheduleRepeating(ins); 
			ins.stoppable = stoppable;
			grille.setObjectLocation(ins, x, y);
			this.insectesVivants.add(ins);
		}
		
		for(int i = 0; i < c.nNourriture; i++) {
			ajouterNourriture();
		}
	
	}
	
	public void ajouterNourriture() {
		int x, y;
		x = (int) Math.floor(Math.random()*c.grilleL);
		y = (int) Math.floor(Math.random()*c.grilleH);
		Nourriture nourriture = new Nourriture(c.maxFood,x,y);
		grille.setObjectLocation(nourriture, x, y);
	}
	
	
	@Override
	public void finish() {
		super.finish();
		System.out.println(this.aggroMorts);
		this.aggroMorts.clear();
	}
	
	/*
	public void hearIsDead(Agent ag) {
		System.out.println("Un mort.");
		this.grille.remove(ag);
		this.insectesVivants.remove(ag);
		this.pileMorts.add(ag);
		if(insectesVivants.size() == 1) {
			System.out.println("Tentative fin.");
			insectesVivants.get(0).die(this);
			System.out.println("Fin.");
		}
		System.out.println("Est mort.");
	}
	*/
	
	public void hearIsDead(Insecte ins) {
//		System.out.println("Un mort.");
		this.insectesVivants.remove(ins);
		this.pileMorts.add(ins);
		if(insectesVivants.size() == 1) {
			System.out.println("Tentative fin.");
//			insectesVivants.get(0).die(this);
			Insecte lastInsecte = insectesVivants.get(0);
			lastInsecte.die();
			System.out.println("Fin.");
		}
//		System.out.println("Est mort.");
	}
	
	public void end() {
		ArrayList<Insecte> aTuer = (ArrayList<Insecte>) insectesVivants.clone();
		for(Insecte i : aTuer) {
//			i.die(this);
			i.die();
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
