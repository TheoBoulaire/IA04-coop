import java.util.ArrayList;

public class InsecteCarn extends Insecte{

	private static final long serialVersionUID = 3004473789216030680L;

	public InsecteCarn(int x, int y, Modele m, int identite, double aggro, ArrayList<Double> idAggros, double strength,
			int energie, double vie) {
		super(x, y, m, identite, aggro, idAggros, strength, energie, vie);
	}

	public void mange(Insecte ins) {
		while(energie < c.maxEnergy && ins.getEnergie() > 0) {
			energie += ins.getEnergie();
			if(energie > c.maxEnergy) energie = c.maxEnergy;
			
		}
	}
	
	public void phaseNourriture() {}

	@Override
	public void endureHit(Agent ag) {
		double str = ag.getStrength();
		this.vie -= str;
		if(vie < 0) {
			if(ag instanceof InsecteCarn) {
				((InsecteCarn)ag).mange(this);
			}
			this.die();
		}
	}
	
	
}
