
import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;


public class Insecte extends Agent{
  private int nStep = 0;
	
	private static final long serialVersionUID = 31265800453373745L;
	
	private Groupe myGroupe = null;
	
	public Insecte(int x, int y, int identite, double aggro, double strength) {
		super(x, y, identite, aggro, strength);
	}
	
	private Agent samePlace(Modele m) {
		Bag b = m.grille.getObjectsAtLocation(x, y);
		if(b != null && !b.isEmpty()) {
			for(Object o : b) {
				if(o instanceof Agent) {
					Agent a = (Agent) o;
					if(a != this) {
						return a;
					}
				}
			}
		}
		return null;
	}
	
	
	private void fight(Agent agent, Modele modele) {
		if(agent != null) {
			if(this.identite > agent.identite) {
				System.out.println("Un insecte rencontre un ennemi plus faible");
				attack(agent);
			}else if(this.identite == agent.identite){
				join(agent, modele);
			}else {
				System.out.println("Un insecte rencontre un ennemi plus fort");
				attack(agent);
			}
		}
	}
	
	private void attack(Agent agent) {
		System.out.println("Insecte attaque.\n");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		agent.dead |= res;
	}
	
	private void join(Agent agent, Modele modele) {
		if(myGroupe == null) {
			if(agent instanceof Insecte) {//Deux insectes joignent ensemble.
				System.out.println("Deux insectes joignent ensemble.\n");
				Insecte ins = (Insecte)agent;
				Groupe groupe = new Groupe(x, y, identite, aggro, strength+ins.strength);
				myGroupe = groupe;
				if(ins.getMyGroupe() == null) {
					ins.setMyGroupe(groupe);
				}
				groupe.addInsecte(this);
				groupe.addInsecte(ins);
				Stoppable stoppable = modele.schedule.scheduleRepeating(groupe); 
				groupe.stoppable = stoppable;
				modele.grille.setObjectLocation(groupe, x, y);
			}else {//L'insecte se joignt a un groupe
				System.out.println("L'insecte se joignt a un groupe.\n");
				Groupe groupe = (Groupe)agent;
				groupe.addInsecte(this);
				myGroupe = groupe;
			}
		}
		meurt(modele);
	}
	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		deplacer(m);
		Agent ennemy = samePlace(m);
		fight(ennemy, m);
		if(dead) {
			die(m);
		}
		nStep++;
		if(nStep > 20000) {
			m.end();
		}
	}
	
	
	
	public void die(Modele m) {
		stoppable.stop();
		m.hearIsDead(this);
	}

	public Groupe getMyGroupe() {
		return myGroupe;
	}

	public void setMyGroupe(Groupe myGroupe) {
		this.myGroupe = myGroupe;
	}
	
}
