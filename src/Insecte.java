
import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Insecte extends Agent{
	
	private static final long serialVersionUID = 31265800453373745L;
	
	private int nStep = 0;
	private Groupe myGroupe = null;
	
	public Insecte(int x, int y, int identite, double aggro, double strength, int energie, int vie) {
		super(x, y, identite, aggro, strength, energie, vie);
	}
	
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
				if(ennemy != null && ennemy.vie > 0) {
					fight(ennemy, m);
					if(vie <= 0 || energie == 0) {
						System.out.println("\n Insecte meurt. \n");
						m.aggroMorts.push(aggro);
						meurt(m, this);
					}
				}else {
					System.out.println("\n Insecte deplace. \n");
					deplacer(m);
				}
				roundDone = true;
			}
			
			nStep++;
			if(nStep > 20000) {
				m.end();
			}
			
		}else {
			System.out.println("Insecte meurt.");
			m.aggroMorts.push(aggro);
			meurt(m, this);
		}
	}
	
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
		if(this.identite > agent.identite) {
			System.out.println("Un insecte rencontre un ennemi '" + agent.getClass() + "' plus faible");
			attack(agent);
		}else if(this.identite == agent.identite){
			join(agent, modele);
		}else {
			System.out.println("Un insecte rencontre un ennemi '" + agent.getClass() + "' plus fort");
			attack(agent);
		}
	}
	
	private void attack(Agent agent) {
		System.out.println("Insecte attaque  strength = " + strength);
//		boolean res = Math.random() > 0.5;
//		this.dead |= !res;
//		agent.dead |= res;
//		this.consommerEnergie();
		
		if(agent instanceof Groupe) {
			Groupe grp = (Groupe) agent;
			if(grp.getInsectes().size() > 1)
				attackGroupe(grp);
			else
				return;
		}
		System.out.println("avant attaque agent.vie = " + agent.vie);
		agent.vie -= strength;
		System.out.println("apres attaque agent.vie = " + agent.vie + "\n");
		if (vie < 0) {
			vie = 0;
		}
		consommerEnergie();//consommer une unite d'energie
	}
	
	private void join(Agent agent, Modele modele) {
		if(myGroupe == null) {
			if(agent instanceof Insecte) {//Deux insectes joignent ensemble.
				System.out.println("Deux insectes joignent ensemble. \n");
				Insecte ins = (Insecte)agent;
				System.out.println("this.energie = " + energie + " ins.enegie = " + ins.energie);
				//dans le constructeur de Groupe, energie doit etre 0, puisque addInsecte() va ajouter leurs enegies
				Groupe groupe = new Groupe(x, y, identite, aggro, strength+ins.strength, 0, 0);
				myGroupe = groupe;
				if(ins.getMyGroupe() == null) {
					ins.setMyGroupe(groupe);
				}
				groupe.addInsecte(this); 
				groupe.addInsecte(ins);
				
				meurt(modele, this);
				meurt(modele, ins);
				
				Stoppable stoppable = modele.schedule.scheduleRepeating(groupe); 
				groupe.stoppable = stoppable;
				modele.grille.setObjectLocation(groupe, x, y);
			}else {//L'insecte se joignt a un groupe
				System.out.println("L'insecte se joignt a un groupe.\n");
				Groupe groupe = (Groupe)agent;
				groupe.addInsecte(this);
				myGroupe = groupe;
				meurt(modele, this);
			}
		}
	}


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
	}
	
	private void mange(){
		if(energie > c.maxEnergy-c.foodEnergy)
			energie = c.maxEnergy;
		else 
			energie += c.foodEnergy;			
	}
	
	
	public Groupe getMyGroupe() {
		return myGroupe;
	}

	public void setMyGroupe(Groupe myGroupe) {
		this.myGroupe = myGroupe;
	}

	@Override
	public void consommerEnergie() {
		energie--;
	}
	
//	public void die(Modele m) {
//		stoppable.stop();
//		m.hearIsDead(this);
//	}
}
