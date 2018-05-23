import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

public abstract class Agent implements Steppable{ 
		
		private static final long serialVersionUID = -3914618152121113732L;
	
		public static Constants c;
		public double strength = 0;
		public double aggro = 0;
		public int x, y;
		public boolean dead = false;
		public Stoppable stoppable;
		
		
		public Agent(double a, int x, int y) {
			this.x = x;
			this.y = y;
			//this.strength = s;
			this.aggro = a;
		}

		@Override
		public abstract void step(SimState ss);
		
		
		public void deplacer(Modele m) {
			int direction = (int) Math.floor(Math.random()*9);
			pas(m,direction);
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
		
		
//		public double getAggro() {
//			return aggro;
//		}
//		
//		public double getStrength() {
//			return strength;
//		}
}
