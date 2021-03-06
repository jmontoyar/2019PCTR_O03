import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	// update with number of group label. See practice statement.
	private final int N_BALL = 3 + 3;
	private Ball[] balls;
	private Thread[] threads = null;

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}

	private void initBalls() {
		// TODO init balls
		balls = new Ball[N_BALL];
		
		for(int i = 0; i < N_BALL; i++) {
			balls[i] = new Ball();
		}
		
		formarTriangulo();
		board.setBalls(balls);
	}
	
	// Establece las coordenadas de partida de las bolas para que formen un triángulo
	private void formarTriangulo() {
		int columna = 0;
		int fila = 0;
		double desplazamiento = 0;
		
		for(int i = 0; i < N_BALL; i++) {
			if(fila > columna) {
				fila = 0;
				columna++;
				desplazamiento -= 0.5;
			}
			
			balls[i].setX(balls[i].getX() - (int)(balls[i].getImage().getWidth(null) * columna));
			balls[i].setY(balls[i].getY() - (int)(balls[i].getImage().getHeight(null) * (fila + desplazamiento)));
			
			fila++;
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent arg0) {
			// Code is executed when start button is pushed
			if(threads == null) {
				threads = new Thread[N_BALL];
				
				for(int i = 0; i < N_BALL; i++) {
					threads[i] = BallManager.getThread(balls[i], board, i);
					threads[i].start();
				}
			}
		}
	}

	private class StopListener implements ActionListener {
		@Override
		public synchronized void actionPerformed(ActionEvent arg0) {
			// TODO Code is executed when stop button is pushed
			if(threads != null) {
				for(int i = 0; i < N_BALL; i++) {
					threads[i].interrupt();
				}
				
				threads = null;
			}
		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}