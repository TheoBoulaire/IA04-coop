
import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Insecte extends Agent{
	
	private static final long serialVersionUID = 31265800453373745L;
	
	private Groupe myGroupe = null;
	
	public Insecte(int x, int y, int identite, double aggro, double strength) {
		super(x, y, identite, aggro, strength);
	}
	
	private Insecte samePlace(Modele m) {
		Bag b = m.grille.getObjectsAtLocation(x, y);
//		System.out.println("Insecte b = " + b);
		if(b != null && !b.isEmpty()) {
			for(Object o : b) {
				if(o instanceof Insecte) {
					Insecte i = (Insecte) o;
					if(i != this) {
						return i;
					}
//					if(i.aggro != aggro && i.x == x && i.y == y)
//						return i;
				}
			}
		}
		return null;
	}
	
	
	private void fight(Insecte ins, Modele modele) {
//		if(ins != null && Math.random() < aggro)
//			attack(ins);
		if(ins != null) {
			if(this.identite > ins.identite) {
				System.out.println("enemy insecte plus faible");
				attack(ins);
			}else if(this.identite == ins.identite){
				join(ins, modele);
			}else {
				System.out.println("enemy insecte plus fort");
				attack(ins);
			}
		}
	}
	
	private void attack(Insecte ins) {
		System.out.println("J'attaque.");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		ins.dead |= res;
	}
	
	private void join(Insecte ins, Modele modele) {
		System.out.println("Deux insectes joignent ensemble.");
		if(myGroupe == null) {
			Groupe groupe = new Groupe(x, y, identite, aggro, strength+ins.strength);
			myGroupe = groupe;
			if(ins.getMyGroupe() == null) {
				ins.setMyGroupe(groupe);
			}
			groupe.rejoindreGroupe(this);
			groupe.rejoindreGroupe(ins);
			Stoppable stoppable = modele.schedule.scheduleRepeating(groupe); 
			groupe.stoppable = stoppable;
			modele.grille.setObjectLocation(groupe, x, y);
		}
		meurt(modele);
	}
	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		deplacer(m);
		Insecte ennemy = samePlace(m);
		fight(ennemy, m);
		if(dead) {
			m.grille.remove(this);
			m.aggroMorts.push(this.aggro);
			stoppable.stop();
			//System.out.println("Je meurs.");
		}
	}

	public Groupe getMyGroupe() {
		return myGroupe;
	}

	public void setMyGroupe(Groupe myGroupe) {
		this.myGroupe = myGroupe;
	}
	
}
