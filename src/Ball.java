import java.awt.Image;
import javax.swing.ImageIcon;
// Transform the code to be used safely in a concurrent context.  
public class Ball {
       //TODO  Find an archive named Ball.png 
	private String Ball = "Ball.png"; 

	private double x,y,dx,dy;
	private double v,fi;
	private Image image;
	private final int IMG_TAM_X,IMG_TAM_Y;

	public Ball() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(Ball));
		image = ii.getImage();
		
		// Depend of image size
		IMG_TAM_X = 36;
		IMG_TAM_Y = 36;

		
		x = Billiards.Width/4-16;
		y = Billiards.Height/2-16;
		v = 5;
		fi =  Math.random() * Math.PI * 2;
		
		checkPostCondition();
	}

	public synchronized void move() {
		v = v*Math.exp(-v/1000);
		dx = v*Math.cos(fi);
		dy = v*Math.sin(fi);
		if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
			dx = 0;
			dy = 0;
		}
		x += dx;   
		y += dy;
		
		reflect();
		
		//TODO Check postcondition
		checkPostCondition();
	}
	
	private void checkPostCondition() {
		assert(x > Board.LEFTBOARD && x < Board.RIGHTBOARD - IMG_TAM_X): "Valor de x fuera de rango (" + Board.LEFTBOARD + ".." + (Board.RIGHTBOARD - IMG_TAM_X) + "). x=" + x;
		assert(y > Board.TOPBOARD && y < Board.BOTTOMBOARD - IMG_TAM_Y): "Valor de y fuera de rango (" + Board.TOPBOARD + ".." + (Board.BOTTOMBOARD - IMG_TAM_Y) + "). y=" + y;
	}

	private void reflect() {
		if (Math.abs(x + IMG_TAM_X - Board.RIGHTBOARD) <  Math.abs(dx)) {
			fi = Math.PI - fi;
		}
		if (Math.abs(y + IMG_TAM_Y - Board.BOTTOMBOARD) <  Math.abs(dy)) {
			fi = - fi;
		}
		if (Math.abs(x - Board.LEFTBOARD) <  Math.abs(dx)) {
			fi = Math.PI - fi;
		}
		if (Math.abs(y - Board.TOPBOARD) <  Math.abs(dy)) {
			fi = - fi;
		}
	}

	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public double getFi() {
		return fi;
	}

	public double getdr() {
		return Math.sqrt(dx*dx+dy*dy);
	}

	public synchronized void setX(double x) {
		this.x = x;
		checkPostCondition();
	}

	public synchronized void setY(double y) {
		this.y = y;
		checkPostCondition();
	}

	public Image getImage() {
		return image;
	}

}

