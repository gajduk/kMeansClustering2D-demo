import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas extends JPanel{

	Color current_color = Color.RED;
	
	private static final long serialVersionUID = 4940216724275666550L;
	public ArrayList<RectDouble> instances;
	Color colors[] = { Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.YELLOW };
    MouseHandler mouseHandler = new MouseHandler();
    public boolean clustering;
    Aggregator aggregator = null;
     
	 public boolean first_part = true;

     public Canvas(){
        this.addMouseListener(mouseHandler);
        this.instances = new ArrayList<RectDouble>();
     } 

     protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if ( clustering ) {
        	for ( RectDouble r : aggregator.instances ) {
        		if ( aggregator.cluster_ids.containsKey(r) ) {
        			r.c = colors[aggregator.cluster_ids.get(r)];
        		}
        		else {
        			r.c = colors[0];
        		}
        		r.paint(g);
        	}
        	
        	for ( RectDouble r : aggregator.clusters ) {
        		r.c = Color.RED;
        		r.paint(g,2);
        	}
        
        }
        else {
        	for ( RectDouble r : instances ) {
        		r.paint(g);
        	}
        }
    }
    
    private class MouseHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        	PointDouble p = new PointDouble(e.getPoint().x,e.getPoint().y);
        	RectDouble to_delete = null;
        	for ( RectDouble rd : instances ) {
        		if ( rd.hitTest(p) ) to_delete = rd;
        	}
        	if ( to_delete != null )
        		instances.remove(to_delete);
        	else 
        		instances.add(new RectDouble(p,current_color));
        	
            repaint();
        }
    }

	public void startClustering(int num_clusters) {
		clustering = true;
		aggregator = new Aggregator(instances, num_clusters);
		aggregator.init();
	}

	public void clear() {
		clustering = false;
		instances.clear();
	}

	public void next() {
		if ( first_part ) {
			aggregator.clusteringIterationFirstPart();
		}
		else {
			aggregator.clusteringIterationSecondPart();
		}
		first_part = first_part?false:true;
	}
	
}