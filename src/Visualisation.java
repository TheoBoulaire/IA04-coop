import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.JFrame;

import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.SimplePortrayal2D;
import sim.portrayal.grid.DrawPolicy;
import sim.portrayal.grid.SparseGridPortrayal2D;
import sim.portrayal.simple.FacetedPortrayal2D;
import sim.portrayal.simple.HexagonalPortrayal2D;
import sim.portrayal.simple.LabelledPortrayal2D;
import sim.portrayal.simple.OvalPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;
import sim.portrayal.simple.ShapePortrayal2D;

public class Visualisation extends GUIState {

	public Display2D display;
	public JFrame displayFrame;
	public SparseGridPortrayal2D grillePortrayal = new SparseGridPortrayal2D();
	public static Constants constants;
	private SimplePortrayal2D[] portrayals = new SimplePortrayal2D[11];

	public Visualisation(SimState state) {
		super(state);
	}

	public void start() {
		super.start();
		setupPortrayals();

	}

	public void load(SimState state) {

	}

	public void setupPortrayals() {
		Modele modele = (Modele) state;
		grillePortrayal.setField(modele.grille);
		grillePortrayal.setPortrayalForClass(Groupe.class, getGroupePortrayal());
		for(Insecte i : modele.getInsectesVivants()) {
			grillePortrayal.setPortrayalForObject(i, getInsectPortrayal(i));
		}
		grillePortrayal.setPortrayalForClass(Nourriture.class, getNourriturePortrayal());
		display.reset();
		display.setBackdrop(new Color(220, 220, 220));
		display.repaint();
	}

	
	private SimplePortrayal2D getInsectPortrayal(Insecte i) {
		SimplePortrayal2D p = null;
		int n = (int) i.getIdentite();
		switch(n) {
			case 0 : 
				ShapePortrayal2D a = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_BOWTIE, ShapePortrayal2D.Y_POINTS_BOWTIE) {
					public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
						int R = (int) Math.abs(1-(i.getStrength() * 255/27));
						paint = new Color(R, 0, 0);
						super.draw(object, graphics, info);
					}
				});
				p = a;
				break;
			
			case 1 : 
				ShapePortrayal2D b = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_DIAMOND, ShapePortrayal2D.Y_POINTS_DIAMOND) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = b;
				break;
			
			case 2 : 
				ShapePortrayal2D c = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_HEXAGON, ShapePortrayal2D.Y_POINTS_HEXAGON) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = c;
				break;
				
			case 3 : 
				ShapePortrayal2D d = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_HEXAGON_ROTATED,
					ShapePortrayal2D.Y_POINTS_HEXAGON_ROTATED) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = d;
				break;
				
			case 4 :
				ShapePortrayal2D e = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_HOURGLASS,
					ShapePortrayal2D.Y_POINTS_HOURGLASS) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = e;
				break;
				
			case 5 :
				ShapePortrayal2D f = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_OCTAGON, ShapePortrayal2D.Y_POINTS_OCTAGON) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = f;
				break;
				
			case 6 :
				ShapePortrayal2D g = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_SQUARE, ShapePortrayal2D.Y_POINTS_SQUARE) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = g;
				break;
			case 7 :
				ShapePortrayal2D h = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_DOWN,
					ShapePortrayal2D.Y_POINTS_TRIANGLE_DOWN) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = h;
				break;

			case 8 :
				ShapePortrayal2D k = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_LEFT,
					ShapePortrayal2D.Y_POINTS_TRIANGLE_LEFT) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = k;
				break;
			case 9 :
				ShapePortrayal2D l = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_RIGHT,
					ShapePortrayal2D.Y_POINTS_TRIANGLE_RIGHT) {
				public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
					int R = (int) Math.abs(1-(i.getStrength() * 255/27));
					paint = new Color(R, 0, 0);
					super.draw(object, graphics, info);
				}
				});
				p = l;
				break;		
		}
		return p;	
	}

	private LabelledPortrayal2D getGroupePortrayal() {
		SimplePortrayal2D[] portrayals = new SimplePortrayal2D[11];
		portrayals[0] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_BOWTIE, ShapePortrayal2D.Y_POINTS_BOWTIE) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[1] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_DIAMOND, ShapePortrayal2D.Y_POINTS_DIAMOND) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[2] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_HEXAGON, ShapePortrayal2D.Y_POINTS_HEXAGON) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[3] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_HEXAGON_ROTATED,
				ShapePortrayal2D.Y_POINTS_HEXAGON_ROTATED) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[4] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_HOURGLASS,
				ShapePortrayal2D.Y_POINTS_HOURGLASS) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[5] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_OCTAGON, ShapePortrayal2D.Y_POINTS_OCTAGON) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[6] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_SQUARE, ShapePortrayal2D.Y_POINTS_SQUARE) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[7] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_DOWN,
				ShapePortrayal2D.Y_POINTS_TRIANGLE_DOWN) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
				int R = (int) Math.abs(groupe.getAggro() * 255);
				int V = (int) Math.abs(groupe.getAggro() * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[8] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_LEFT,
				ShapePortrayal2D.Y_POINTS_TRIANGLE_LEFT) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
//				int R = (int) Math.abs(groupe.aggro * 255);
//				int V = (int) Math.abs(groupe.aggro * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[9] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_RIGHT,
				ShapePortrayal2D.Y_POINTS_TRIANGLE_RIGHT) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
//				int R = (int) Math.abs(groupe.aggro * 255);
//				int V = (int) Math.abs(groupe.aggro * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		portrayals[10] = (new ShapePortrayal2D(ShapePortrayal2D.X_POINTS_TRIANGLE_UP,
				ShapePortrayal2D.Y_POINTS_TRIANGLE_UP) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
//				int R = (int) Math.abs(groupe.aggro * 255);
//				int V = (int) Math.abs(groupe.aggro * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		});
		FacetedPortrayal2D fPortayal = new FacetedPortrayal2D(portrayals) {
			public int getChildIndex(Object object, int numIndices) {
				Groupe groupe = (Groupe) object;
				return (int) groupe.getInsectes().get(0).getIdentite();
			}
		};
		LabelledPortrayal2D r = new LabelledPortrayal2D(fPortayal, null) {
			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
				Groupe groupe = (Groupe) object;
//				int R = (int) Math.abs(groupe.aggro * 255);
//				int V = (int) Math.abs(groupe.aggro * 255);
				paint = new Color(0, 0, 255);
				super.draw(object, graphics, info);
			}
		};
		return r;
	}
	
	

//	private RectanglePortrayal2D getNourriturePortrayal() {
//		RectanglePortrayal2D r = new RectanglePortrayal2D() {
//			public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
//				Nourriture nou = (Nourriture) object;
//				Color c;
//				switch(nou.quantite) {
//				case 0 :
//					c = new Color (255,255,255);
//					break;
//				case 1 :
//					c = new Color ( 234, 250, 241 );
//					break;
//				case 2 :
//					c = new Color ( 213, 245, 227 );
//					break;
//				case 3 :
//					c = new Color ( 171, 235, 198 );
//					break;
//				case 4 :
//					c = new Color ( 130, 224, 170 );
//					break;
//				case 5 :
//					c = new Color ( 88, 214, 141 );
//					break;
//				case 6 :
//					c = new Color ( 46, 204, 113 );
//					break;
//				case 7 :
//					c = new Color ( 40, 180, 99 );
//					break;
//				case 8 :
//					c = new Color ( 35, 155, 86 );
//					break;
//				case 9 :
//					c = new Color ( 29, 131, 72 );
//					break;
//				default:
//					c = new Color ( 24, 106, 59 );
//					break;					
//				}
//				paint = c;
//				super.draw(object, graphics, info);
//			}
//		};
//		return r;
//	}
	
	private FacetedPortrayal2D getNourriturePortrayal() {
	SimplePortrayal2D[] portrayals = new SimplePortrayal2D[11];
	portrayals[0] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color (255,255,255);
			super.draw(object, graphics, info);
		}
	};
	portrayals[1] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color ( 234, 250, 241 );
			super.draw(object, graphics, info);
		}
	};
	portrayals[2] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color (213, 245, 227);
			super.draw(object, graphics, info);
		}
	};
	portrayals[3] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color ( 171, 235, 198 );
			super.draw(object, graphics, info);
		}
	};
	portrayals[4] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color (130, 224, 170 );
			super.draw(object, graphics, info);
		}
	};
	portrayals[5] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color ( 88, 214, 141);
			super.draw(object, graphics, info);
		}
	};
	portrayals[6] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color ( 46, 204, 113 );
			super.draw(object, graphics, info);
		}
	};
	portrayals[7] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color ( 40, 180, 99 );
			super.draw(object, graphics, info);
		}
	};
	portrayals[8] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color (35, 155, 86);
			super.draw(object, graphics, info);
		}
	};
	portrayals[9] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color (29, 131, 72);
			super.draw(object, graphics, info);
		}
	};
	portrayals[10] = new RectanglePortrayal2D() {
		public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
			paint = new Color ( 24, 106, 59);
			super.draw(object, graphics, info);
		}
	};
	FacetedPortrayal2D fPortrayal = new FacetedPortrayal2D(portrayals) {
		@Override
		public int getChildIndex(Object object, int numIndices) {
			Nourriture nou = (Nourriture) object;
			if(nou.quantite <10) return nou.quantite;
			else return 10;
		}
	};
	return fPortrayal;
	}
	

	public void init(Controller c) {
		super.init(c);
		display = new Display2D(constants.frameSize, constants.frameSize, this);
		display.setClipping(false);
		displayFrame = display.createFrame();
		displayFrame.setTitle("La vie des insectes");
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		display.attach(grillePortrayal, "Grille");
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
