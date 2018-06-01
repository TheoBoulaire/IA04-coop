import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Groupe extends Agent{

	private static final long serialVersionUID = 6107536912823754245L;

	private List<Insecte> insectes;

	public Groupe(int x, int y, int identite, double aggro, double strength, int energie) {
		super(x, y, identite, aggro, strength, energie);
		insectes = new ArrayList<>();
	}

	
	@Override
	public void step(SimState ss) {
		Modele m = (Modele) ss;
		boolean roundDone = false;
		
		if(!dead && energie > 0 && insectes.size() > 0) {
			System.out.println("Le nombre de membre dans le groupe = " + insectes.size() + ". L'energie de groupe = " + energie);		
			for(Insecte ins : insectes) {
				System.out.println("ins.energie = " + ins.energie);
			}
			//TODO consederer le cas quand le groupe reste une seule insecte
			if(mangersource(m)) {
				roundDone = true;
			}
			if(!roundDone) {
				Agent ennemy = samePlace(m);
				if(ennemy != null) {
					fight(ennemy, m);
					if(dead || energie == 0) {
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
		}else {
			m.aggroMorts.push(aggro);
			meurt(m, this);
		}
	}

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
	
	
	private void fight(Agent agent, Modele modele) {
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
	
	private void attack(Agent agent) {
		System.out.println("Groupe attaque.");
		boolean res = Math.random() > 0.5;
		this.dead |= !res;
		agent.dead |= res;
		System.out.println("Groupe consommerEnergie.\n");
		this.consommerEnergie();
		//agent.consommerEnergie();
		
	}
	
	private void join(Agent agent, Modele modele) {
		if(agent instanceof Groupe) {//Deux groupes joignent ensemble
			System.out.println("Deux groupes joignent ensemble.\n");
			Groupe grp = (Groupe)agent;
			insectes.addAll(grp.getInsectes());
			strength += grp.strength;
			energie += grp.energie;
			grp.getInsectes().clear();
			meurt(modele, this);
		}else {//Un groupe permet a un insecte de joindre a lui meme
			System.out.println("Un groupe permet a un insecte de joindre a lui meme.\n");
			Insecte ins = (Insecte)agent;
			ins.setMyGroupe(this);
			addInsecte(ins);
			meurt(modele, ins);
		}
	}
	
	public void addInsecte(Insecte insecte) {
		if(!insectes.contains(insecte)) {
			insectes.add(insecte);
			energie += insecte.energie;
			System.out.println("addInsecte : l'enegie de groupe est " + energie + ".\n");
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
	
	private boolean mangersource(Modele m){
		for(int i = x-1; i<x+2; i++){
			for(int j = y-1; j<y+2; j++){
				if(m.grille.getObjectsAtLocation(i, j)!=null && m.grille.getObjectsAtLocation(i, j).get(0).getClass() == Nourriture.class){
					Nourriture nourriture = (Nourriture) m.grille.getObjectsAtLocation(i, j).get(0);
					
					if(mange()) {
						nourriture.croc(m);
						return true;
					}else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private boolean mange(){
		boolean b = false;
		Collections.sort(insectes, new Comparator<Insecte>() {
		 @Override  
            public int compare(Insecte ins1, Insecte ins2) {  
                return ins1.energie - ins2.energie;  
            }  
		});
	
		if(insectes.get(0).energie < c.maxEnergy) {
			System.out.println("Un groupe mange la nourriture.");
			
			if(insectes.get(0).energie <= (c.maxEnergy - c.foodEnergy)) {
				//la nourriture n'est pas suffisant pour partager avec les autres membres
				System.out.println("la nourriture n'est pas suffisant pour partager.  Energie = " + insectes.get(0).energie + "\n");
				insectes.get(0).energie += c.foodEnergy;
				energie += c.foodEnergy;
			}else {//il y a nourriture reste
				System.out.println("il y a nourriture reste.  Energie = " + insectes.get(0).energie + "\n");
				int rest = c.foodEnergy - (c.maxEnergy - insectes.get(0).energie);
				energie = energie + (c.maxEnergy - insectes.get(0).energie);
				insectes.get(0).energie = c.maxEnergy;
				int i = 1;
				while(rest > 0 && i < insectes.size() && (insectes.get(i).energie + rest >= c.maxEnergy)) {
					System.out.println("while.  rest = " + rest + "\n");
					rest = rest - (c.maxEnergy - insectes.get(i).energie);
					energie = energie + (c.maxEnergy - insectes.get(i).energie);
					insectes.get(i).energie = c.maxEnergy;
					i++;
				}
				
				if(i == insectes.size()-1 && rest > 0) {//Le cas : 13, 13
					energie = energie + rest;
					insectes.get(i).energie += rest;
				}
				
				for(Insecte ins : insectes) {
					System.out.println("apres manger ins.energie = " + ins.energie);
				}
			}
			
			b = true;
		}else {//tous les insectes ont pleine energie
			System.out.println("tous les insectes ont pleine energie. \n");
		}
		return b;
	}
	
	@Override
	public void consommerEnergie() {
		List<Insecte> toDelete = new ArrayList<>();
		for(Insecte ins : insectes) {
			if(ins.energie > 1){
				System.out.println("ins.energie > 1.");
				ins.energie--;
				energie--;
				System.out.println("apres deplace ins.energie = " + ins.energie);
			}else if(ins.energie == 1) {
				System.out.println("ins.energie == 1.");
				toDelete.add(ins);
				ins.energie--;
				energie--;
				System.out.println("apres deplace ins.energie = " + ins.energie);
			}else if(ins.energie == 0) {
				System.out.println("ins.energie == 0. \n");
				toDelete.add(ins);
			}
		}
		insectes.removeAll(toDelete);
	
	}
	
	
	public List<Insecte> getInsectes() {
		return insectes;
	}

}
