import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;

public abstract class Agent implements Steppable{ 
		
		private int nStep = 0;
	
		private static final long serialVersionUID = -3914618152121113732L;
		
		public Modele modele;
	
		public static Constants c;
		public int x, y;
		public boolean dead = false;
		public Stoppable stoppable;
		

		public Agent(int x, int y, Modele m) {
			modele = m;
			this.x = x;
			this.y = y;
		}

		@Override
		public void step(SimState ss) {
			nStep++;
			if(nStep > 20000) {
				modele.end();
			}

			phaseNourriture();
			phaseReproduction();
			phaseDeplacement();
			if(!dead)
				phaseRencontre();
			phaseFin();
		}
		
		public void moveTo(int x, int y) {
			this.x = x;
			this.y = y;
			modele.grille.setObjectLocation(this, x, y);
		}
		
		public void deplacer() {
			int direction = (int) Math.floor(Math.random()*9);
			pas(direction);
		}
			
		private void pas(int direction) {
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
			this.moveTo(newX, newY);
		}
		

//		public double getAggro() {
//			return aggro;
//		}
//		
//		public double getStrength() {
//			return strength;
//		}

		public void removeFromSchedule() {
			stoppable.stop();
			modele.grille.remove(this);
		}
		
		public abstract double getStrength();
		public abstract double getAggro();
		public abstract double getAggro(int identite);
		public abstract double getIdentite();
		
		public char reactTo(Agent a) {
			char ret = 'i'; //ignore
			double aggro = getAggro((int) Math.floor(a.getIdentite()));
			double react = Math.random();
			if(react < aggro) ret = 'a';
			else if(react > (aggro + (1-aggro)/2)) ret = 'j';
			return ret; 
		}
		
		protected abstract void phaseReproduction();
		protected abstract void phaseFin();
		
		protected void phaseRencontre() {
			Bag b = modele.grille.getObjectsAtLocation(x, y);
			if(b != null && !b.isEmpty()) {
				for(Object o : b) {
					if(o instanceof Agent) {
						Agent a = (Agent) o;
						if(a != this) {
							char reaction = reactTo(a);
							switch(reaction) {
								case 'a':
									attackAgent(a);
									break;
								case 'j':
									join(a);
									break;
							}
							break;
						}
					}
				}
			}
		}
		
		public Nourriture foodHere() {
			Bag b = modele.grille.getObjectsAtLocation(x, y);
			if(b != null && !b.isEmpty()) {
				for(Object o : b) {
					if(o instanceof Nourriture) {
						Nourriture n = (Nourriture) o;
						return n;
					}
				}
			}
			return null;
		}
		

		
		private Agent samePlace() {
			Bag b = modele.grille.getObjectsAtLocation(x, y);
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
		
		protected void phaseDeplacement() {
			deplacer();
		}
		protected abstract void phaseNourriture();
		public abstract void join(Agent a);
		
		public abstract void endureAttack(Agent ag);
		
		public void attackAgent(Agent ag) {
			ag.endureAttack(this);
			if(!ag.isDead()) { 
				this.endureAttack(ag);
			}			
		}
		
		public boolean isDead() {
			return dead;
		}
		
}
