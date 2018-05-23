import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.util.Bag;

public class Groupe extends Agent{

	private static final long serialVersionUID = 6107536912823754245L;

	private List<Insecte> insectes;

	public Groupe(double aggro, int x, int y) {
		super(aggro, x, y);
		insectes = new ArrayList<>();
	}

	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		deplacer(m);
		Groupe ennemy = samePlace(m);
		fight(ennemy);
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
					if(g.aggro != aggro && g.x == x && g.y == y)
						return g;
				}
			}
		}
		return null;
	}
	
	
	private void fight(Groupe groupe) {
		if(groupe != null && Math.random() < aggro)
			attack(groupe);
	}
	
	private void attack(Groupe groupe) {
		System.out.println("Groupe attaque.");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		groupe.dead |= res;
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
