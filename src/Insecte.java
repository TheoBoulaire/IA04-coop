
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;

public abstract class Insecte extends Agent {
	
	public int identite;
	public double aggro;
	public double strength;
	public double energie;
	public double vie;
	private static final long serialVersionUID = 31265800453373745L;
	public Groupe myGroupe = null;
	public boolean isCarn = false;
	private ArrayList<Double> idAggros;
	
	public double getAggroToOtherId() {
		double ret = 0;
		for(int i = 0; i < idAggros.size(); i++) {
			if(i != identite) {
				ret += idAggros.get(i);
			}
		}
		return ret/(idAggros.size()-1);
	}
	
	public double getAggroToSelf() {
		return idAggros.get(identite);
	}
	
	public Insecte(int x, int y, Modele m, int identite, double aggro, ArrayList<Double> idAggros, double strength, double energie, double vie) {
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
		consommerEnergie();
	}
	
	public void consommerEnergie() {
		energie--;
		if(energie < 1) {
			this.die();
			System.out.println("Epuisé.");
		}
	}
	
	public void die() {
		this.dead = true;
		removeFromSchedule();
		if(myGroupe != null) myGroupe.removeFromGroupe(this);
		modele.hearIsDead(this);
	}

	public Groupe getMyGroupe() {
		return myGroupe;
	}

	public void setMyGroupe(Groupe myGroupe) {
		this.myGroupe = myGroupe;
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
	
	public double getEnergie() {
		return energie;
	}
	
	@Override
	public void endureAttack(Agent ag) {
		endureHit(ag);
	}
	
	
	public void endureHit(Agent ag) {
		double str = ag.getStrength();
		this.vie -= str;
		if(vie <= 0){
			endureEat(ag);
			this.die();
			System.out.println("Tué !");
		}
	}
	
	private void endureEat(Agent ag) {
		int i = 0;
		if(ag instanceof Groupe) {
			Groupe groupe = (Groupe)ag;
			List<InsecteCarn> listCarns = groupe.getInsecteCarns();
			if(listCarns.size() > 1) {//insecteHerb est mangee par un groupe
				double rest = energie;
				while(rest > 0 && i < listCarns.size() && listCarns.get(i).energie + rest >= c.maxEnergy) {
					rest = rest - (c.maxEnergy - listCarns.get(i).energie);
					listCarns.get(i).mange(this);
					i++;
				}
				System.out.println("Insecte mangé.");
			}else if(listCarns.size() == 1) {
				listCarns.get(0).mange(this);
				System.out.println("Insecte mangé.");
			}
		} else if(ag instanceof InsecteCarn) {
			((InsecteCarn)ag).mange(this);
			System.out.println("Insecte mangé.");
		}
	}

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

	@Override
	public double getAggro(int identite) {
		return idAggros.get(identite);
	}
	
	@Override
	protected void phaseReproduction() {
		if(energie == c.maxEnergy) {
			Insecte i = createChild();
			modele.addInsecte(i);
			energie -= (int) Math.floor(c.maxEnergy/2.0);
		}
	}
	
	@Override
	protected void phaseFin() {
		
	}
	
	protected Insecte createChild() {
		Random r = new Random();
		double nAggro = modele.createRandAggro(r, aggro);
		ArrayList<Double> nAggroTab = modele.createRandAggroTab(r, idAggros);
		InsecteHerb ins = new InsecteHerb(x, y, modele, identite, nAggro, nAggroTab, strength, (int) Math.floor(c.maxEnergy/2.0), 100);
		return ins;
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
}
