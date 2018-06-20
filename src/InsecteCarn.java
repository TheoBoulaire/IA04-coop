import java.util.ArrayList;
import java.util.Random;

public class InsecteCarn extends Insecte{

	private static final long serialVersionUID = 3004473789216030680L;

	public InsecteCarn(int x, int y, Modele m, int identite, ArrayList<Double> idAggros, double strength,
			int energie, double vie) {
		super(x, y, m, identite, idAggros, strength, energie, vie);
		isCarn = true;
	}

	public void mange(Insecte ins) {
		energie += ins.getEnergie();
		if(energie > c.maxEnergy) energie = c.maxEnergy;
	}
	
	@Override
	public void phaseNourriture() {}
	
	@Override
	protected Insecte createChild() {
		Random r = new Random();
		ArrayList<Double> nAggroTab = modele.createRandAggroTab(r, idAggros);
		InsecteCarn ins = new InsecteCarn(x, y, modele, identite, nAggroTab, strength, (int) Math.floor(c.maxEnergy/2.0), 100);
		return ins;
	}
}
