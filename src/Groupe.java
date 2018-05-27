import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Groupe extends Agent{

	private static final long serialVersionUID = 6107536912823754245L;

	private List<Insecte> insectes;

	public Groupe(int x, int y, int identite, double aggro, double strength) {
		super(x, y, identite, aggro, strength);
		insectes = new ArrayList<>();
	}

	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		deplacer(m);
		Agent ennemy = samePlace(m);
		fight(ennemy, m);
		if(dead) {
			m.grille.remove(this);
			m.aggroMorts.push(aggro);
			stoppable.stop();
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
			if(this.identite > agent.identite) {
				System.out.println("Un groupe rencontre un ennemi plus faible");
				attack(agent);
			}else if(this.identite == agent.identite){
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
			strength += grp.strength;
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
	
	public void quitterGroupe(Insecte insecte) {
		if(insectes.contains(insecte)) {
			insectes.remove(insecte);
		}
	}
	
	private void updateAggro() {
		double sum = 0;
		for(Insecte insecte : insectes) {
			sum += insecte.aggro;
		}
		this.aggro = sum/insectes.size(); 
	}
	
	
	public List<Insecte> getInsectes() {
		return insectes;
	}

}
