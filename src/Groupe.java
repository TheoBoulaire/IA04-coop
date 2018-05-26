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
		Groupe ennemy = samePlace(m);
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
//					if(g.aggro != aggro && g.x == x && g.y == y)
//						return g;
				}
			}
		}
		return null;
	}
	
	
	private void fight(Groupe groupe, Modele modele) {
//		if(groupe != null && Math.random() < aggro)
//			attack(groupe);
		
		if(groupe != null) {
			if(this.identite > groupe.identite) {
				System.out.println("enemy groupe plus faible");
				attack(groupe);
			}else if(this.identite == groupe.identite){
				join(groupe, modele);
			}else {
				System.out.println("enemy groupe plus fort");
				attack(groupe);
			}
		}
	}
	
	private void attack(Groupe groupe) {
		System.out.println("Groupe attaque.");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		groupe.dead |= res;
	}
	
	private void join(Groupe grp, Modele modele) {
		System.out.println("Deux groupes joignent ensemble.");
		insectes.addAll(grp.getInsectes());
		strength += grp.strength;
		modele.grille.remove(grp);
		grp.stoppable.stop();
	}
	
	public void rejoindreGroupe(Insecte insecte) {
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
