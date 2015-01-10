import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;


@SuppressWarnings("deprecation")
public class kMeansClustering2D extends JFrame {
	
	JMenuBar menuBar;
	JMenu run_submenu;
	JMenuItem color_picker,start,clear,next_step,about;
	Canvas canvas;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3027483139467459351L;
	
	public kMeansClustering2D() {
		super("kMeansClustering2D");
		canvas = new Canvas();
		menuBar = new JMenuBar();
		
		color_picker = new JMenuItem("Change color");
		color_picker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.current_color = JColorChooser.showDialog(
						kMeansClustering2D.this,
		                "Choose Background Color",
		                canvas.current_color);
			}
		});
		color_picker.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		menuBar.add(color_picker);
		
		clear = new JMenuItem("Clear");
		clear.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		clear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				next_step.disable();
				canvas.clear();
				repaint();
			}
		});
		menuBar.add(clear);

		start = new JMenuItem("Start clustering");
		start.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = JOptionPane.showInputDialog(kMeansClustering2D.this, "Number of clusters:",2);
				try {
					int num_clusters = Integer.parseInt(result);
					if ( num_clusters < 2 || num_clusters > 5 ) {
						throw new ArrayIndexOutOfBoundsException();
					}
					if ( canvas.instances.size() < num_clusters )
						throw new ArrayIndexOutOfBoundsException();

					next_step.enable();
					canvas.startClustering(num_clusters);
					repaint();
				}
				catch ( Exception ee ) {
					next_step.disable();
					JOptionPane.showMessageDialog(kMeansClustering2D.this,
						    "Num of clusters must be between 2 and 5.\nThere must be at least as manu squares as clusters.",
						    "Error.",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		menuBar.add(start);
		
		next_step = new JMenuItem("Next Step");
		next_step.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		next_step.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.next();
				repaint();
			}
		});
		menuBar.add(next_step);
		
		about = new JMenuItem("Hot to use?");

		about.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(kMeansClustering2D.this,
					    "Click on the canvas to add new instances (rectangles).\n"+
						"Change the color for the next recangle by clicking on change color.\n"+
					    "You can delete individual rectangles by clicking on them, \n"+
					    "or you can delete all rectangels by clikcing on the Clear button.\n"+
					    "When you are sattisfied with you dataset click Start clustering.\n"+
					    "Contine clicking next step until the clustering converges.\n"+
					    "Have fun!\n"+
					    "Made by Andrej Gajduk.",
					    "How to use?",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuBar.add(about);
		
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setSize(600,600);
		setResizable(false);
		add(canvas);
	}

	public static void main(String[] args) {
        new kMeansClustering2D();
	}
	

}
