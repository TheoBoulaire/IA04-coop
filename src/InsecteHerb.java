import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InsecteHerb extends Insecte{

	private static final long serialVersionUID = -4289802375342488684L;
	
	public InsecteHerb(int x, int y, Modele m, int identite, ArrayList<Double> idAggros, double strength,
			int energie, double vie) {
		super(x, y, m, identite, idAggros, strength, energie, vie);
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
	
	@Override
	protected Insecte createChild() {
		Random r = new Random();
		ArrayList<Double> nAggroTab = modele.createRandAggroTab(r, idAggros);
		InsecteHerb ins = new InsecteHerb(x, y, modele, identite, nAggroTab, strength, (int) Math.floor(c.maxEnergy/2.0), 100);
		return ins;
	}
}
