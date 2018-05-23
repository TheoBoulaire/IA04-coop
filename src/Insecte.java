import java.util.ArrayList;
import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;
import sim.util.IntBag;

public class Insecte implements Steppable {
	//private double strength = 0;
	private double aggro = 0;
	private int x, y;
	static public Constants c;
	private boolean dead = false;
	public Stoppable stoppable;
	
	
	public Insecte(double a, int x, int y) {
		this.x = x;
		this.y = y;
		//this.strength = s;
		this.aggro = a;
	}

	
	public double getAggro() {
		return aggro;
	}
	public double getStrength() {
		return 0;
	}
	
	private void deplacer(Modele m){
		int direction = (int) Math.floor(Math.random()*9);
		pas(m,direction);
		//System.out.println("Je me déplace aléatoirement.");
	}
	
	private Insecte samePlace(Modele m) {
		Bag b = m.grille.getObjectsAtLocation(x, y);
		for(Object o : b) {
			Insecte i = (Insecte) o;
			if(i.aggro != aggro && i.x == x && i.y == y)
				return i;
		}
		return null;
	}
	
	private void pas(Modele m, int direction) {
		int newX = 0, newY = 0;
		switch(direction) {
		case 0:
			newX = x -1; newY = y -1;
			break;
		case 1:
			newX = x; newY = y -1;
			break;
		case 2:
			newX = x +1; newY = y -1;
			break;
		case 3:
			newX = x -1; newY = y;
			break;
		case 4:
			newX = x; newY = y;
			break;
		case 5:
			newX = x+1; newY = y;
			break;
		case 6:
			newX = x-1; newY = y + 1;
			break;
		case 7:
			newX = x; newY = y + 1;
			break;
		case 8:
			newX = x+1; newY = y + 1;
			break;
		}
		newX = (newX+c.grilleL) % c.grilleL;
		newY = (newY+c.grilleH) % c.grilleH;
		m.grille.setObjectLocation(this, newX, newY);
		x = newX;
		y = newY;
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
