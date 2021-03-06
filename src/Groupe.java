import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import java.util.Random;

public class Groupe extends Agent {

	private static final long serialVersionUID = 6107536912823754245L;

	/**
	 * Insectes rang�s par aggressivit�.
	 */
	private ArrayList<Insecte> insectes;

	public Groupe(int x, int y, Modele m) {
		super(x, y, m);
		insectes = new ArrayList<Insecte>();
		m.addGroupe(this);
	}
	
	@Override
	public void deplacer() {
		super.deplacer();
		ArrayList<Insecte> copieIns = (ArrayList<Insecte>) insectes.clone();
		for(Insecte i : copieIns) {
			i.consommerEnergie();
		}
	}

	@Override
	public void moveTo(int x, int y) {
		super.moveTo(x, y);
		for(Insecte i : insectes) {
			i.x = x;
			i.y = y;
		}
	}
	
	/**
	 * M�thode visant � refaire des insectes � partir d'un groupe et � supprimer ce groupe.
	 */
	public void endGroup() {
		modele.removeGroupe(this);
		dead = true;
		if(insectes != null) {
			for(Insecte ins : insectes) {
				ins.setMyGroupe(null);
				Stoppable stoppable = modele.schedule.scheduleRepeating(ins); 
				ins.stoppable = stoppable;
				modele.grille.setObjectLocation(ins, x, y);
			}
		}
		removeFromSchedule();
	}
	
	/*
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		
			/*
			//supprimer les insectes dont leur vie est negative
			for(Insecte ins : insectes) {
				System.out.println("ins.vie = " + ins.getVie() + " et ins.energie = " + ins.getEnergie());
				if(ins.getVie() <= 0) {
					deadInsectes.add(ins);
					//la vie du groupe est deja reduit dans la methode attackGroupe()
					//l'energie du groupe est aussi verifie dans la methode consommerEnergie()
				}
			}
			
			System.out.println("Le nombre de membre dans le groupe = " + insectes.size() + ". L'energie de groupe = " + energie);
			
			//traiter le cas lorsque le nombre de membre du groupe est egale 1, ce groupe doit devenir un insecte
			if(insectes.size() == 1) {
				System.out.println("le nombre de membre du groupe est egale 1.  Groupe disparait!!!");
				endGroup();
			} else {
				Agent ennemy = samePlace(m);
				if(ennemy != null && ennemy.vie > 0) {
					fight(ennemy, m);
					if(vie <= 0 || energie <= 0) {
						System.out.println("Groupe meurt.");
						m.aggroMorts.push(aggro);
						meurt(m, this);
					}
				}else {
					System.out.println("Groupe deplace.");
					deplacer(m);
				}
				roundDone = true;
			}
					if(mangersource(m)) {
						roundDone = true;
					}
				}
			}
		}
	}*/

	private Groupe samePlace(Modele m) {
		Bag b = m.grille.getObjectsAtLocation(x, y);
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
	
	/*
	private void phaseCombat() {
		Agent ennemy = samePlace(m);
		if(ennemy != null && ennemy.vie > 0) {
			fight(ennemy, m);
			if(vie <= 0 || energie <= 0) {
				System.out.println("Groupe meurt.");
				m.aggroMorts.push(aggro);
				meurt(m, this);
			}
		}
	}*/
	
	/*
	private void fight(Agent agent, Modele modele) {
<<<<<<< HEAD
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
=======
		if(this.identite > agent.identite) {
			System.out.println("Un groupe rencontre un ennemi '" + agent.getClass() + "' plus faible");
			attack(agent);
		}else if(this.identite == agent.identite){
			join(agent, modele);
		}else {
			System.out.println("Un groupe rencontre un ennemi '" + agent.getClass() + "' plus fort");
			attack(agent);
>>>>>>> 3826539a190ccdaac1d3b5ee89514b6fb19a8b1f
		}
	}*/
	
	/*
	private void attack(Agent agent) {
		System.out.println("Groupe attaque. strength = " + strength);
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
		System.out.println("apres attaque agent.vie = " + agent.vie);
		if (vie < 0) {
			vie = 0;
		}
		consommerEnergie();
	}*/
	
	@Override
	public void join(Agent agent) {
		if(agent instanceof Groupe) {//Deux groupes joignent ensemble
			System.out.println("Deux groupes joignent ensemble.\n");
			Groupe grp = (Groupe) agent;
			for(Insecte ins : grp.getInsectes()) {
				ins.setMyGroupe(this);
			}
			insectes.addAll(grp.getInsectes());
			grp.insectes = null;
			grp.endGroup();
		} else {//Un groupe permet a un insecte de joindre a lui meme
			System.out.println("Un groupe permet a un insecte de joindre a lui meme.\n");
			Insecte ins = (Insecte) agent;
			ins.join(this);
		}
	}
	
	public void addInsecte(Insecte insecte) {
		if(!insectes.contains(insecte)) {
			insecte.setMyGroupe(this);
			int i = 0;
			boolean insert = false;
			double aggro = insecte.getAggro();
			while(i < insectes.size() && !insert) {
				if(aggro > insectes.get(i).getAggro()) {
					insectes.add(i, insecte);
					insert = true;
				}
				i++;
			}
			if(!insert) {
				insectes.add(insecte);
			}
		}
	}
	
	public void removeFromGroupe(Insecte insecte) {
		if(insectes.contains(insecte)) {
			insecte.setMyGroupe(null);
			insectes.remove(insecte);
		}
		if(insectes.isEmpty()) {
			endGroup();
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


	@Override
	public void endureAttack(Agent ag) {
		double str = ag.getStrength();
		int index = 0;
		ArrayList<Insecte> insCopy = (ArrayList<Insecte>) insectes.clone();
		while(str > 0 && !dead && index < insCopy.size()) {
			Insecte ins = insCopy.get(index);
			double vie = ins.getVie();
			ins.endureHit(ag);
			str -= vie;//la force reste est disribue a attacker le prochain insecte 
			index++;
		}
	}
	/*
	@Override
	protected void phaseDeplacement() {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public double getAggro(int identite) {
		double agr = 0;
		for(Insecte ins : insectes) {
			if(ins.getAggro(identite) > agr) agr = ins.getAggro(identite);
		}
		return agr;
	}

	@Override
	protected void phaseNourriture() {
		Nourriture n = foodHere();
		if(n != null) {
			ListIterator<Insecte> it = insectes.listIterator();
			while(n.getQuantite() > 0 && it.hasNext()) {
				Insecte ins = it.next();
				if(ins instanceof InsecteHerb) {
					((InsecteHerb)ins).mange(n);
				}
			}
		}
	}
	
	public List<InsecteCarn> getInsecteCarns() {
		List<InsecteCarn> list = new ArrayList<>();
		for(Insecte ins : insectes) {
			if(ins instanceof InsecteCarn) {
				list.add((InsecteCarn)ins);
			}
		}
		return list;
	}
	
	public String toString() {
		return String.valueOf(insectes.size());		
	}

	@Override
	protected void phaseReproduction() {
		for(Insecte i : insectes) {
			i.phaseReproduction();
		}
	}
	@Override
	protected void phaseFin() {
		if(insectes.size() == 1)
			endGroup();
	}
}
