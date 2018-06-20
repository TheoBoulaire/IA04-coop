import java.util.ArrayList;

public class InsecteCarn extends Insecte{

	private static final long serialVersionUID = 3004473789216030680L;

	public InsecteCarn(int x, int y, Modele m, int identite, double aggro, ArrayList<Double> idAggros, double strength,
			int energie, double vie) {
		super(x, y, m, identite, aggro, idAggros, strength, energie, vie);
	}

	public void mange(Insecte ins) {
		energie += ins.getEnergie() / 2;
		if(energie > c.maxEnergy) energie = c.maxEnergy;
	}
	
	@Override
	public void phaseNourriture() {}
}
