import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;


public class WindowFrame extends JFrame {

	private final int WINDOW_THICKNESS = 22;
	
	private final int WINDOW_WIDTH = 1000;
	private final int WINDOW_HEIGHT = 1000 + WINDOW_THICKNESS;
	
	public WindowFrame() {
		initUI();
	}
	
	private void initUI() {
		final ComplexFunctionGrapher canvas = new ComplexFunctionGrapher();
		this.add(canvas);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Timer timer = canvas.getTimer();
				timer.stop();
			}
		});
		
		this.setTitle("Fractal Generator");
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				WindowFrame f = new WindowFrame();
				f.setVisible(true);	
			}
		});	
	}
}
