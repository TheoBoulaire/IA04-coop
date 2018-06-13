import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;

public abstract class Agent implements Steppable{ 
		
		private int nStep = 0;
	
		private static final long serialVersionUID = -3914618152121113732L;
		
		public Modele modele;
	
		public static Constants c;
		/*
		public double strength = 0;
		public double aggro = 0;
		public int identite = 0;
<<<<<<< HEAD
		*/
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
			phaseDeplacement();
			phaseRencontre();
//			phaseRencontre();
//			phaseNourriture();
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
		
		public double reactTo(Agent a) {
			return getAggro((int) Math.floor(a.getIdentite())); 
		}
		
		protected void phaseRencontre() {
			Bag b = modele.grille.getObjectsAtLocation(x, y);
			if(b != null && !b.isEmpty()) {
				for(Object o : b) {
					if(o instanceof Agent) {
						Agent a = (Agent) o;
						if(a != this) {
							double reaction = reactTo(a);
							if(reaction > 0.7) {
								attackAgent(a);
							} else if(reaction < 0.3 && a.reactTo(this) < 0.3) {
								join(a);
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
		
		public void attackAgent(Agent ag) {//对手的命值被自己的攻击力减去
			ag.endureAttack(this);
			if(!ag.isDead()) { 
				ag.attackAgent(this);
			}
			
//			if(this instanceof  InsecteCarn && ag instanceof Insecte) {
//				InsecteCarn ic = (InsecteCarn)this;
//				Insecte ins = (Insecte)ag;
//				ic.mange(ins);
//			}
			
		}
		
		public boolean isDead() {
			return dead;
		}
		
}
