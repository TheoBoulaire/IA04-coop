import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.HexagonalPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;

public class Visualisation extends GUIState{
	
	public Display2D display;
	public JFrame displayFrame;
	public SparseGridPortrayal2D grillePortrayal = new SparseGridPortrayal2D();
	public static Constants constants;

	public Visualisation(SimState state) {
		super(state);
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
		super.start();  setupPortrayals();
		
	}
	
	public void load(SimState state) {
		
	}
	public void setupPortrayals() {
		Modele modele = (Modele) state;
		grillePortrayal.setField(modele.grille);
		grillePortrayal.setPortrayalForClass(Insecte.class, getInsectePortrayal());
		display.reset();
		display.setBackdrop(new Color(220,220,220));
		display.repaint();
	}
	
	private RectanglePortrayal2D getInsectePortrayal() {
		RectanglePortrayal2D r = new RectanglePortrayal2D()
			{
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
				{
				Insecte ins = (Insecte)object;
				int R = (int) Math.abs(ins.getAggro()*255);
				int V = (int) Math.abs(ins.getStrength()*255);
				paint = new Color(R, V, 0);
				super.draw(object, graphics, info);
				}
			};
		return r;
	}
	
	public void init(Controller c) {
		super.init(c);
		display = new Display2D(constants.frameSize, constants.frameSize,this);
		display.setClipping(false);
		displayFrame = display.createFrame();
		displayFrame.setTitle("La vie des insectes");
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.attach(grillePortrayal, "Grille" );
	}
	
	public Object getSimulationInspectedObject() {
		return state; 
	}
	
	public Inspector getInspector() {
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}

}
