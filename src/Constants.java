
public class Constants {
	public int grilleH;
	public int grilleL;
	public int nInsectesHerb;
	public int nInsectesCarn;
	public int frameSize;
	public double foodEnergy;
	public int maxEnergy;
	public int forceCarn;
	public int forceHerb;
	public double groupProba;
	public double baseAggroCarn;
	public Constants(int h, int l, int nih, int nic, int fs, double fe, int me, int fc, int fh, double gp, double bac) {
		grilleH = h;
		grilleL = l;
		nInsectesHerb = nih;
		nInsectesCarn = nic;
		frameSize = fs;
		foodEnergy = fe;
		maxEnergy = me;
		forceCarn = fc;
		forceHerb = fh;
		groupProba = gp;
		baseAggroCarn = bac;
	}
}
