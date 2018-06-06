import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Groupe extends Agent{

	private static final long serialVersionUID = 6107536912823754245L;

	private List<Insecte> insectes;

	public Groupe(int x, int y) {
		super(x, y);
		insectes = new ArrayList<>();
	}

	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		deplacer(m);
		Agent ennemy = samePlace(m);
		fight(ennemy, m);
		if(dead) {
			this.die(m);
			//System.out.println("Je meurs.");
		}
	}

	private Groupe samePlace(Modele m) {
		Bag b = m.grille.getObjectsAtLocation(x, y);
//		System.out.println("Groupe b = " + b);
		if(b != null && !b.isEmpty()) {
			for(Object o : b) {
				if(o instanceof Groupe) {
					Groupe g = (Groupe) o;
					if(g != this) {
						return g;
					}
				}
			}
		}
		return null;
	}
	
	
	private void fight(Agent agent, Modele modele) {
		if(agent != null) {
			if(this.getIdentite() > agent.getIdentite()) {
				System.out.println("Un groupe rencontre un ennemi plus faible");
				attack(agent);
			}else if(this.getIdentite() == agent.getIdentite()){
				join(agent, modele);
			}else {
				System.out.println("Un groupe rencontre un ennemi plus fort");
				attack(agent);
			}
		}
	}
	
	private void attack(Agent agent) {
		System.out.println("Groupe attaque.\n");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		agent.dead |= res;
	}
	
	private void join(Agent agent, Modele modele) {
		if(agent instanceof Groupe) {//Deux groupes joignent ensemble
			System.out.println("Deux groupes joignent ensemble.\n");
			Groupe grp = (Groupe)agent;
			insectes.addAll(grp.getInsectes());
			grp.getInsectes().clear();
			modele.grille.remove(grp);
			grp.stoppable.stop();
		}else {//Un groupe permet a un insecte de joindre a lui meme
			System.out.println("Un groupe permet a un insecte de joindre a lui meme.\n");
			Insecte ins = (Insecte)agent;
			insectes.add(ins);
			modele.grille.remove(ins);
			ins.stoppable.stop();
		}
	}
	
	public void addInsecte(Insecte insecte) {
		if(!insectes.contains(insecte)) {
			insectes.add(insecte);
		}
	}
	
	public void removeFromGroupe(Insecte insecte) {
		if(insectes.contains(insecte)) {
			insectes.remove(insecte);
		}
	}
	
	/*
	private void updateAggro() {
		double sum = 0;
		for(Insecte insecte : insectes) {
			sum += insecte.aggro;
		}
		this.aggro = sum/insectes.size(); 
	}
	*/
	
	public void die(Modele m) {
		super.die(m);
		for(Insecte i : insectes) {
			i.die(m);
		}
	}
	
	public List<Insecte> getInsectes() {
		return insectes;
	}

	
	@Override
	public double getStrength() {
		double str = 0;
		for(Insecte ins : insectes) {
			str += ins.getStrength();
		}
		return str;
	}
	
	@Override
	public double getAggro() {
		double agr = 0;
		for(Insecte ins : insectes) {
			if(ins.getAggro() > agr) agr = ins.getAggro();
		}
		return agr;
	}
	
	@Override
	public double getIdentite() {
		double id = 0;
		int n = insectes.size();
		for(Insecte ins : insectes) {
			id += ins.getIdentite();
		}
		return id/n;
	}

}
