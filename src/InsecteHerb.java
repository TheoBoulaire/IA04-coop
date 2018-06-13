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
	
	
	public void phaseNourriture() {
		Nourriture n = foodHere();
		if(n != null) {
			mange(n);
		}
	}

	@Override
	public void endureHit(Agent ag) {
		double str = ag.getStrength();
		this.vie -= str;
		if(vie < 0){
			endureEat(ag);
			this.die();
		}
	}
	
	private void endureEat(Agent ag) {
		int i = 0;
		if(ag instanceof Groupe) {
			Groupe groupe = (Groupe)ag;
			List<InsecteCarn> listCarns = groupe.getInsecteCarns();
			if(listCarns.size() > 1) {//insecteHerb est mangee par un groupe
				int rest = energie;
				while(rest > 0 && i < listCarns.size() && listCarns.get(i).energie + rest >= c.maxEnergy) {
					rest = rest - (c.maxEnergy - listCarns.get(i).energie);
					listCarns.get(i).mange(this);
					i++;
				}
			}else if(listCarns.size() == 1) {
				listCarns.get(0).mange(this);
			}
		}else {
			if(ag instanceof InsecteCarn) {
				((InsecteCarn)ag).mange(this);
			}
		}
		
	}

}
