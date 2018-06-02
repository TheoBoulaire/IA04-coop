
public class Nourriture{
	
	static Constants c;
	int quantite;
	private int x;
	private int y;
	
	
	public Nourriture(int quantite, int x, int y) {
		this.x = x;
		this.y = y;
		this.quantite = quantite;
	}
	
	public void croc(Modele m){
		quantite -= 1;
		if(quantite == 0){
			m.grille.remove(this);
			m.ajouterNourriture();
		}
		
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

}
