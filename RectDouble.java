import java.awt.Color;
import java.awt.Graphics;


public class RectDouble {
	
	PointDouble p;
	Color c;

	public static final int size = 10;
	
	public RectDouble ( PointDouble p , Color c ) {
		this.p = p;
		this.c = c;
	}
	
	public RectDouble ( PointDouble p ) {
		this.p = p;
	}
	
	public RectDouble ( ) {
		this.p = new PointDouble(0,0);
		this.c = Color.BLACK;
	}
	
	public void paint( Graphics g ) {
		g.setColor(c);
    	g.fillRect((int)p.x-size,(int)p.y-size,size*2, size*2);
	}
	
	public void paint( Graphics g , int size) {
		g.setColor(c);
    	g.fillRect((int)p.x-size,(int)p.y-size,size*2, size*2);
	}
	
	public boolean hitTest ( PointDouble po ) {
		return p.x-size<po.x&&p.x+size>po.x&&p.y-size<po.y&&p.y+size>po.y;
	}
	
	public RectDouble add( RectDouble r ) {
		return new RectDouble(new PointDouble(r.p.x+p.x,r.p.y+p.y));
	}
	
	public RectDouble divide( int d ) {
		return new RectDouble(new PointDouble(p.x/d,p.y/d));
	}
	
	public double distance( RectDouble r ) {
		double dx = p.x-r.p.x;
		double dy = p.y-r.p.y;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	@Override
	public boolean equals(Object o) {
		RectDouble r = (RectDouble) o;
		return r.p.x==p.x&&r.p.y==p.y;
	}
	
	@Override
	public String toString() {
		return "("+p.x+","+p.y+")";
	}
}
