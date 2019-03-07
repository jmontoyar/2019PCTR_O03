public class BallManager implements Runnable {
	private int numero_bola;
	private Board board;
	private Ball ball;
	
	private BallManager(Ball ball, Board board, int numero_bola) {
		this.ball = ball;
		this.board = board;
		this.numero_bola = numero_bola;
	}

	@Override
	public void run() {
		System.out.println("Iniciado un nuevo hilo " + Thread.currentThread().getName() + " que controla la bola " + numero_bola);
		try {
			do {
				ball.move();
				board.repaint();
				Thread.sleep(10);
			} while(ball.getdr() > 0);
			System.out.println("La bola " + numero_bola + " ya no se mueve, por lo que finalizamos el hilo " + Thread.currentThread().getName());
		}
		catch (InterruptedException e) {
			System.out.println("Interrumpido el hilo " + Thread.currentThread().getName() + " que controlaba la bola " + numero_bola);
			return;
		}
	}
	
	public static Thread getThread(Ball ball, Board board, int numero_bola) {
		return new Thread(new BallManager(ball, board, numero_bola));
	}
}
