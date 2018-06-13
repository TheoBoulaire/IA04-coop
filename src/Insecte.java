
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;

public abstract class Insecte extends Agent{
	
	public int identite;
	public double aggro;
	public double strength;
	public int energie;
	public double vie;
	private static final long serialVersionUID = 31265800453373745L;
	public Groupe myGroupe = null;
	private ArrayList<Double> idAggros;
	
	public Insecte(int x, int y, Modele m, int identite, double aggro, ArrayList<Double> idAggros, double strength, int energie, double vie) {
		super(x, y, m);
		this.identite = identite;
		this.aggro = aggro;
		this.strength = strength;
		this.vie = vie;
		this.energie = energie;
		this.idAggros = idAggros;
	}
	
	@Override
	public void deplacer() {
		super.deplacer();
		energie--;
		if(energie < 1) {
			this.die();
		}
	}
	
	/*
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		boolean roundDone = false;
		
		if(vie > 0 && energie > 0) {
			if(energie < c.maxEnergy) {
				roundDone = mangersource(m);
			}
			
			if(!roundDone) {
				Agent ennemy = samePlace(m);
				if(ennemy != null) {
					fight(ennemy, m);
					if(vie <= 0 || energie == 0) {
						System.out.println("\n Insecte meurt. \n");
						m.aggroMorts.push(aggro);
						die();
					}
				}else {
					System.out.println("\n Insecte deplace. \n");
					deplacer(m);
				}
				roundDone = true;
			}
			
		}else {
			System.out.println("Insecte meurt.");
			m.aggroMorts.push(aggro);
			die();
		}
	}*/
	
//	@Override
//	public void step(SimState ss) {
//		Modele m = (Modele) ss;
//		deplacer(m);
//		Agent ennemy = samePlace(m);
//		fight(ennemy, m);
//		if(dead) {
//			die(m);
//		}
//		nStep++;
//		if(nStep > 20000) {
//			m.end();
//		}
//	}
	
	/*
	private void fight(Agent agent, Modele modele) {
		if(this.identite > agent.getIdentite()) {
			System.out.println("Un insecte rencontre un ennemi '" + agent.getClass() + "' plus faible");
			attack(agent);
		}else if(this.identite == agent.getIdentite()){
			join(agent, modele);
		}else {
			System.out.println("Un insecte rencontre un ennemi '" + agent.getClass() + "' plus fort");
			attack(agent);
		}
	}
	*/
	/*
	private void join(Agent agent, Modele modele) {
		if(myGroupe == null) {
			if(agent instanceof Insecte) {//Deux insectes joignent ensemble.
				System.out.println("Deux insectes joignent ensemble. \n");
				Insecte ins = (Insecte)agent;
				System.out.println("this.energie = " + energie + " ins.enegie = " + ins.energie);
				//dans le constructeur de Groupe, energie doit etre 0, puisque addInsecte() va ajouter leurs enegies
				Groupe groupe = new Groupe(x, y);
				myGroupe = groupe;
				if(ins.getMyGroupe() == null) {
					ins.setMyGroupe(groupe);
				}
				groupe.addInsecte(this); 
				groupe.addInsecte(ins);
				
				die();
				ins.die();
				
				Stoppable stoppable = modele.schedule.scheduleRepeating(groupe); 
				groupe.stoppable = stoppable;
				modele.grille.setObjectLocation(groupe, x, y);
		}
	}*/

	/*
	private boolean mangersource(Modele m){
		for(int i = x-1; i<x+2; i++){
			for(int j = y-1; j<y+2; j++){
				if(m.grille.getObjectsAtLocation(i, j)!=null && m.grille.getObjectsAtLocation(i, j).get(0).getClass() == Nourriture.class){
					Nourriture nourriture = (Nourriture) m.grille.getObjectsAtLocation(i, j).get(0);
					nourriture.croc(m);
					mange();
					System.out.println("Un insecte mange la nourriture. \n");
					return true;
				}
			}
		}
		return false;
	}*/
	
	
	
//	protected void mange(Nourriture n) {
//		while(energie < c.maxEnergy && n.getQuantite() > 0) {
//			energie += c.foodEnergy;
//			if(energie > c.maxEnergy) energie = c.maxEnergy;
//			n.croc(modele);
//		}
//	}
	
	public void die() {
		this.dead = true;
		removeFromSchedule();
		if(myGroupe != null) myGroupe.removeFromGroupe(this);
		modele.hearIsDead(this);
		System.out.println("Vie : " + vie + ", energie : " + energie);
	}

	public Groupe getMyGroupe() {
		return myGroupe;
	}

	public void setMyGroupe(Groupe myGroupe) {
		this.myGroupe = myGroupe;
	}

	public void consommerEnergie() {
		energie--;
	}
	
	@Override
	public double getAggro() {
		return aggro;
	}
	@Override
	public double getStrength() {
		return strength;
	}
	@Override
	public double getIdentite() {
		return (double) identite;
	}
	
	public double getVie() {
		return vie;
	}
	
	public int getEnergie() {
		return energie;
	}
	
	@Override
	public void endureAttack(Agent ag) {
		endureHit(ag);
	}
	
	
	public abstract void endureHit(Agent ag);
	
//	public void die(Modele m) {
//		stoppable.stop();
//		m.hearIsDead(this);
//	}

	@Override
	public void join(Agent a) {
		if(a instanceof Groupe) {
			removeFromSchedule();
			Groupe g = (Groupe) a;
			myGroupe = g;
			g.addInsecte(this);
		} else if(a instanceof Insecte) {
			Groupe g = new Groupe(x, y, modele);
			Insecte i = (Insecte) a;
			removeFromSchedule(); 
			g.addInsecte(this);
			i.removeFromSchedule();
			g.addInsecte(i);
			Stoppable stoppable = modele.schedule.scheduleRepeating(g); 
			g.stoppable = stoppable;
			modele.grille.setObjectLocation(g, x, y);
		}
	}

	/*
	@Override
	protected void phaseDeplacement() {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public double getAggro(int identite) {
		return idAggros.get(identite);
	}
	
	protected void phaseReproduction() {
		if(energie == c.maxEnergy) {
			Insecte i = createChild();
			modele.addInsecte(i);
			energie -= (int) Math.floor(c.maxEnergy/2.0);
		}
	}
	
	protected Insecte createChild() {
		Random r = new Random();
		double nAggro = modele.createRandAggro(r, aggro);
		ArrayList<Double> nAggroTab = modele.createRandAggroTab(r, idAggros);
		Insecte ins = new Insecte(x, y, modele, identite, nAggro, nAggroTab, strength, (int) Math.floor(c.maxEnergy/2.0), 100);
		return ins;
	}

	@Override
	protected void phaseNourriture() {}

}
