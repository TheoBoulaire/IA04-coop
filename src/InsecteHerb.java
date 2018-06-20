import java.util.ArrayList;
import java.util.List;

public class InsecteHerb extends Insecte{

	private static final long serialVersionUID = -4289802375342488684L;
	
	public InsecteHerb(int x, int y, Modele m, int identite, double aggro, ArrayList<Double> idAggros, double strength,
			int energie, double vie) {
		super(x, y, m, identite, aggro, idAggros, strength, energie, vie);
	}

	public void mange(Nourriture n) {
		while(energie < c.maxEnergy && n.getQuantite() > 0) {
			energie += c.foodEnergy;
			if(energie > c.maxEnergy) energie = c.maxEnergy;
			n.croc(modele);
		}
	}
	
	@Override
	public void phaseNourriture() {
		Nourriture n = foodHere();
		if(n != null) {
			mange(n);
		}
	}
}
