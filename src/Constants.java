
public class Constants {
	public int grilleH;
	public int grilleL;
	public int nInsectesHerb;
	public int nInsectesCarn;
	public int frameSize;
	public double foodEnergy;
	public int maxFood;
	public int maxEnergy;
	public int forceCarn;
	public int forceHerb;
	public double groupProba;
	public Constants(int h, int l, int nih, int nic, int fs, double fe, int mf, int me, int fc, int fh, double gp) {
		grilleH = h;
		grilleL = l;
		nInsectesHerb = nih;
		nInsectesCarn = nic;
		frameSize = fs;
		foodEnergy = fe;
		maxFood = mf;
		maxEnergy = me;
		forceCarn = fc;
		forceHerb = fh;
		groupProba = gp;
	}
}
