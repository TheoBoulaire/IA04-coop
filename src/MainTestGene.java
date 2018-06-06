import sim.display.Console;

public class MainTestGene {

	public static void main(String[] args) {
		runUI();
	}
	public static void runUI() {
		ModeleFactory mf = new ModeleFactory();
		mf.chainModeles(100);
		for(IdentiteModele im : mf.ids) {
			if(im!=null) System.out.println(im.getAggro());
		}
	}
}
