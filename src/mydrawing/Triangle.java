package mydrawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/*
	»æÍ¼×é¼þ£ºÈý½ÇÐÎ
	½ªöÎ±àÐ´


*/
class Triangle extends DrawingPanel{
	private int InitX;
	private int InitY;
	private int SecondX;
	private int SecondY;
	private int PreX;
	private int PreY;
	private int ThirdX;
	private int ThirdY;
	private boolean flag;
	public Triangle(BufferedImage[] ImageArray, JLabel mLabel){
		super(ImageArray, mLabel);
		InitX = -1;
		InitY = -1;
		SecondX = -1;
		SecondY = -1;
		PreX = -1;
		PreY = -1;
		ThirdX = -1;
		ThirdY= -1;
		
		addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				if(InitX < 0 && InitY < 0){
					InitX = e.getX();
					InitY = e.getY();
				}
				PreX = e.getX();
				PreY = e.getY();
				String coordText ;
				coordText = "";
				if(PreX < DRAWING_PANEL_WIDTH && PreX >= 0
						&& PreY >= 0 && PreY < DRAWING_PANEL_HEIGHT){
					coordText = String.valueOf(e.getX()) + ","
							+ String.valueOf(e.getY() ) + "   ÏñËØ";
				}
				coordText = coordText + "                              "+
						DRAWING_PANEL_WIDTH + "¡Á"+" " + DRAWING_PANEL_HEIGHT
						+"ÏñËØ";
				mLabel.setText(coordText);
				repaint();
			}
		});
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
					if(InitX < 0 && InitY < 0){
						InitX = e.getX();
						InitY = e.getY(); 
					}
					else if(InitX > 0 &&InitY > 0 && SecondX < 0 && SecondY < 0){
						SecondX = e.getX();
						SecondY = e.getY();
//						repaint();
					}
					else if(InitX > 0 &&InitY > 0 && SecondX > 0 && SecondY > 0
							&& ThirdX < 0 && ThirdY < 0){
						ThirdX = e.getX();
						ThirdY = e.getY();
						drawTriangle(InitX ,InitY, SecondX, SecondY, ThirdX, ThirdY);
						repaint();
						InitX = -1;
						InitY = -1;
						SecondX = -1;
						SecondY = -1;
						ThirdX = -1;
						ThirdY= -1;
					}
			}	
		});
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(R, G, B));//¸¸ÀàµÄR G B
		g2d.setStroke(new BasicStroke(mStroke));
		if(InitX > 0 && InitY > 0 && ThirdX < 0 && ThirdY < 0){
			g.drawLine(InitX, InitY, PreX, PreY);
		}
		else{
			g.drawString("", 1, 1);
		}
	}
}
