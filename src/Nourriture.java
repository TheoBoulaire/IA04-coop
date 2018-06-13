import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

public class Nourriture implements Steppable{
	
	static Constants c;
	int quantite;
	public Stoppable stoppable;
	private int x;
	private int y;
	
	
	public Nourriture(int quantite, int x, int y) {
		this.x = x;
		this.y = y;
		this.quantite = quantite;
	}
	
	public void croc(Modele m){
		quantite -= 1;
	

	}
	
	public int getQuantite() {
		return quantite;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean estDisponible() {
		return quantite > 0;
	}
	

	@Override
	public void step(SimState arg0) {
		quantite++;
		
	}

}
