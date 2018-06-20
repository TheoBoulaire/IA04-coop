import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.SparseGrid2D;

public class Modele extends SimState {
	
	private static final long serialVersionUID = 2598273691291778150L;
	
	static Constants c = new Constants(20, 20, 10, 3, 400, 3, 15, 100, 50, 0.5, 0.5);
	private IdentiteModele im = null;
	public SparseGrid2D grille = new SparseGrid2D(c.grilleL, c.grilleH);
	//public Stack<Double> aggroMorts = new Stack<Double>();
	//public ArrayList<Insecte> pileMorts = new ArrayList<Insecte>();
	private ArrayList<Insecte> insectesVivants = new ArrayList<Insecte>();
	//public Stack<Double> aggroNaissances = new Stack<Double>();

	public int nInsectesVivants = c.nInsectesHerb + c.nInsectesCarn;

	public int nInsectesCarn = c.nInsectesCarn;

	public int nInsectesHerb = c.nInsectesHerb;

	public double aggroLastNaissance;

	public double aggroLastNaissanceCarn;

	public double aggroLastNaissanceHerb;

	public double aggroSelfLastNaissance;

	public double aggroSelfLastNaissanceCarn;

	public double aggroSelfLastNaissanceHerb;

	public double aggroOthersLastNaissance;

	public double aggroOthersLastNaissanceCarn;

	private double aggroOthersLastNaissanceHerb;
	
	public double getDiffAggroSelfLastNaissance() {
		return aggroSelfLastNaissance - aggroLastNaissance;
	}
	
	public double getDiffAggroOthersLastNaissance() {
		return aggroOthersLastNaissance - aggroLastNaissance;
	}
	
	private Visualisation gui;

	public int getnInsectesCarn() {
		return nInsectesCarn;
	}

	public void setnInsectesCarn(int nInsectesCarn) {
		this.nInsectesCarn = nInsectesCarn;
	}

	public int getnInsectesHerb() {
		return nInsectesHerb;
	}

	public void setnInsectesHerb(int nInsectesHerb) {
		this.nInsectesHerb = nInsectesHerb;
	}

	public double getAggroLastNaissance() {
		return aggroLastNaissance;
	}

	public int getnInsectesVivants() {
		return nInsectesVivants;
	}

	public void setnInsectesVivants(int nInsectesVivants) {
		this.nInsectesVivants = nInsectesVivants;
	}

	public void setAggroLastNaissance(double aggroLastNaissance) {
		this.aggroLastNaissance = aggroLastNaissance;
	}

	public double getAggroLastNaissanceCarn() {
		return aggroLastNaissanceCarn;
	}

	public void setAggroLastNaissanceCarn(double aggroLastNaissanceCarn) {
		this.aggroLastNaissanceCarn = aggroLastNaissanceCarn;
	}

	public double getAggroLastNaissanceHerb() {
		return aggroLastNaissanceHerb;
	}

	public void setAggroLastNaissanceHerb(double aggroLastNaissanceHerb) {
		this.aggroLastNaissanceHerb = aggroLastNaissanceHerb;
	}

	public double getAggroSelfLastNaissance() {
		return aggroSelfLastNaissance;
	}

	public void setAggroSelfLastNaissance(double aggroSelfLastNaissance) {
		this.aggroSelfLastNaissance = aggroSelfLastNaissance;
	}

	public double getAggroSelfLastNaissanceCarn() {
		return aggroSelfLastNaissanceCarn;
	}

	public void setAggroSelfLastNaissanceCarn(double aggroSelfLastNaissanceCarn) {
		this.aggroSelfLastNaissanceCarn = aggroSelfLastNaissanceCarn;
	}

	public double getAggroSelfLastNaissanceHerb() {
		return aggroSelfLastNaissanceHerb;
	}

	public void setAggroSelfLastNaissanceHerb(double aggroSelfLastNaissanceHerb) {
		this.aggroSelfLastNaissanceHerb = aggroSelfLastNaissanceHerb;
	}

	public double getAggroOthersLastNaissance() {
		return aggroOthersLastNaissance;
	}

	public void setAggroOthersLastNaissance(double aggroOthersLastNaissance) {
		this.aggroOthersLastNaissance = aggroOthersLastNaissance;
	}

	public double getAggroOthersLastNaissanceCarn() {
		return aggroOthersLastNaissanceCarn;
	}

	public void setAggroOthersLastNaissanceCarn(double aggroOthersLastNaissanceCarn) {
		this.aggroOthersLastNaissanceCarn = aggroOthersLastNaissanceCarn;
	}

	public double getAggroOthersLastNaissanceHerb() {
		return aggroOthersLastNaissanceHerb;
	}

	public void setAggroOthersLastNaissanceHerb(double aggroOthersLastNaissanceHerb) {
		this.aggroOthersLastNaissanceHerb = aggroOthersLastNaissanceHerb;
	}
	
	public void setVisualisation(Visualisation g) {
		this.gui = g;
	}

	public Modele(long seed) {
		super(seed);
		Agent.c = c;
		Visualisation.constants = c;
		nInsectesVivants = c.nInsectesHerb + c.nInsectesCarn;
		nInsectesCarn = c.nInsectesCarn;
		nInsectesHerb = c.nInsectesHerb;
	}
	
	public Modele(long seed, Constants c) {
		super(seed);
		this.c= c;
		Agent.c = c;
		Visualisation.constants = c;
		nInsectesVivants = c.nInsectesHerb + c.nInsectesCarn;
		nInsectesCarn = c.nInsectesCarn;
		nInsectesHerb = c.nInsectesHerb;
	}
	
	public Modele(long seed, IdentiteModele im) {
		super(seed);
		this.im = im;
		Insecte.c = c;
		Visualisation.constants = c;
		nInsectesVivants = c.nInsectesHerb + c.nInsectesCarn;
		nInsectesCarn = c.nInsectesCarn;
		nInsectesHerb = c.nInsectesHerb;
	}
	
	public double createRandAggro(Random r) {
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
	
	public double createRandAggro(Random r, double aggro) {
		double modifAggro = r.nextGaussian();
		modifAggro *= 0.05;
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
	
	public ArrayList<Double> createRandAggroTab(Random r, ArrayList<Double> aggroTab) {
		ArrayList<Double> ret = new ArrayList<Double>();
		for(int i = 0; i < 10; i++) {
			double modifAggro = r.nextGaussian();
			modifAggro *= 0.05;
			double aggro = aggroTab.get(i);
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
		for(int i = 0; i < c.grilleL; i++) {
			for(int j = 0; j < c.grilleL; j++) {
				Nourriture nourriture = new Nourriture(3,i,j);
				Stoppable stoppable = schedule.scheduleRepeating(nourriture); 
				nourriture.stoppable = stoppable;
				grille.setObjectLocation(nourriture, i, j);
			}
		}
		//pos agents
		int x, y;
		int identite;
		double aggro;
		double strength;
		ArrayList<Double> aggroTab;
		Random r = new Random();
		for(int i = 0; i < c.nInsectesHerb; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			identite = (int) Math.floor(Math.random()*10);
			strength = c.forceHerb;
//			aggro = (double)identite/10;
			
			aggroTab = createRandAggroTab(r);
			/*
			System.out.println("identite = " + identite);
			System.out.println("aggro = " + aggro);
			System.out.println("strength = " + strength + "\n");
			*/
			
			InsecteHerb ins = new InsecteHerb(x, y, this, identite, aggroTab, strength, c.maxEnergy, 100);
			Stoppable stoppable = schedule.scheduleRepeating(ins); 
			ins.stoppable = stoppable;
			grille.setObjectLocation(ins, x, y);
			this.insectesVivants.add(ins);
		}
		
		for(int i = 0; i < c.nInsectesCarn; i++) {
			x = (int) Math.floor(Math.random()*c.grilleL);
			y = (int) Math.floor(Math.random()*c.grilleH);
			identite = (int) Math.floor(Math.random()*10);
			strength = c.forceCarn;
//			aggro = (double)identite/10;
			ArrayList<Double> basesAggro = new ArrayList<Double>();
			for(int j = 0; j < 10; j++)
				basesAggro.add(c.baseAggroCarn);
			aggroTab = createRandAggroTab(r, basesAggro);
			/*
			System.out.println("identite = " + identite);
			System.out.println("aggro = " + aggro);
			System.out.println("strength = " + strength + "\n");
			*/
			
			InsecteCarn ins = new InsecteCarn(x, y, this, identite, aggroTab, strength, c.maxEnergy, 100);
			Stoppable stoppable = schedule.scheduleRepeating(ins); 
			ins.stoppable = stoppable;
			grille.setObjectLocation(ins, x, y);
			this.insectesVivants.add(ins);
		}
		
	
	}
	
	public void addInsecte(Insecte ins) {
		Stoppable stoppable = schedule.scheduleRepeating(ins); 
		ins.stoppable = stoppable;
		grille.setObjectLocation(ins, ins.x, ins.y);
		gui.grillePortrayal.setPortrayalForObject(ins, gui.getInsectPortrayal(ins));
		nInsectesVivants++;
		if(ins instanceof InsecteCarn)
			nInsectesCarn++;
		else
			nInsectesHerb++;
		aggroLastNaissance = ins.getAggro();
		if(ins instanceof InsecteCarn)
			aggroLastNaissanceCarn = ins.getAggro();
		else
			aggroLastNaissanceHerb = ins.getAggro();
		aggroSelfLastNaissance = ins.getAggroToSelf();
		if(ins instanceof InsecteCarn)
			aggroSelfLastNaissanceCarn = ins.getAggroToSelf();
		else
			aggroSelfLastNaissanceHerb = ins.getAggroToSelf();
		aggroOthersLastNaissance = ins.getAggroToOtherId();
		if(ins instanceof InsecteCarn)
			aggroOthersLastNaissanceCarn = ins.getAggroToOtherId();
		else
			aggroOthersLastNaissanceHerb = ins.getAggroToOtherId();
		this.insectesVivants.add(ins);
		//aggroNaissances.push(ins.getAggro());
	}
	
	
	
	public void setInsectesVivants(ArrayList<Insecte> insectesVivants) {
		this.insectesVivants = insectesVivants;
	}

	public void ajouterNourriture() {
		int x, y;
		x = (int) Math.floor(Math.random()*c.grilleL);
		y = (int) Math.floor(Math.random()*c.grilleH);
		Nourriture nourriture = new Nourriture(5,x,y);
		grille.setObjectLocation(nourriture, x, y);
	}
	
	
	@Override
	public void finish() {
		super.finish();
		//System.out.println(this.aggroMorts);
		//this.aggroMorts.clear();
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
		boolean res = this.insectesVivants.remove(ins);
		if(res) {
			nInsectesVivants--;
			if(ins instanceof InsecteCarn)
				nInsectesCarn--;
			else
				nInsectesHerb--;
			//this.pileMorts.add(ins);
			if(insectesVivants.size() == 1) {
				System.out.println("Tentative fin.");
	//			insectesVivants.get(0).die(this);
				Insecte lastInsecte = insectesVivants.get(0);
				nInsectesVivants--;
				if(lastInsecte instanceof InsecteCarn)
					nInsectesCarn--;
				else
					nInsectesHerb--;
				lastInsecte.die();
				System.out.println("Fin.");
				this.end();
			}
	//		System.out.println("Est mort.");
		}
	}
	
	public void end() {
		ArrayList<Insecte> aTuer = (ArrayList<Insecte>) insectesVivants.clone();
		for(Insecte i : aTuer) {
			i.die();
		}
		for(int y = 0; y < c.grilleH; y++) {
			for(int x = 0; x < c.grilleL; x++) {
				
			}
		}
	}

	public IdentiteModele getIm() {
		return im;
	}

	public void setIm(IdentiteModele im) {
		this.im = im;
	}

	/*
	public ArrayList<Insecte> getPileMorts() {
		return pileMorts;
	}

	public void setPileMorts(ArrayList<Insecte> pileMorts) {
		this.pileMorts = pileMorts;
	}
	*/

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
	public ArrayList<Insecte> getInsectesVivants(){
		return this.insectesVivants;
	}
	

	
}
