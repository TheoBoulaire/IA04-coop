
import sim.engine.SimState;
import sim.util.Bag;

public class Insecte extends Agent{
	
	private static final long serialVersionUID = 31265800453373745L;
	
	public Insecte(double aggro, int x, int y) {
		super(aggro, x, y);
	}
	
	private Insecte samePlace(Modele m) {
		Bag b = m.grille.getObjectsAtLocation(x, y);
//		System.out.println("Insecte b = " + b);
		if(b != null && !b.isEmpty()) {
			for(Object o : b) {
				if(o instanceof Insecte) {
					Insecte i = (Insecte) o;
					if(i.aggro != aggro && i.x == x && i.y == y)
						return i;
				}
			}
		}
		return null;
	}
	
	
	private void fight(Insecte ins) {
		if(ins != null && Math.random() < aggro)
			attack(ins);
	}
	
	private void attack(Insecte ins) {
		System.out.println("J'attaque.");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		ins.dead |= res;
	}
	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		deplacer(m);
		Insecte ennemy = samePlace(m);
		fight(ennemy);
		if(dead) {
			m.grille.remove(this);
			m.aggroMorts.push(this.aggro);
			stoppable.stop();
			//System.out.println("Je meurs.");
		}
	}
}
