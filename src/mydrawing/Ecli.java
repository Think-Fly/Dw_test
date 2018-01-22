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
»æÍ¼×é¼þ ÍÖÔ²
Áõ¿¡½Ü±àÐ´


*/
class Ecli extends DrawingPanel{
	private int InitX;
	private int InitY;
	private int PreX;
	private int PreY;
	
	public Ecli(BufferedImage[] ImageArray, JLabel mLabel){
		super(ImageArray, mLabel);
		InitX = -1;
		InitY = -1;
		//Ìí¼Ó¼àÌýÆ÷
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
				drawEcli(InitX, InitY, e.getX(), e.getY());
				InitX = -1;
				InitY = -1;
			}
		});
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(R, G, B));//¸¸ÀàµÄR G B
		g2d.setStroke(new BasicStroke(mStroke));
		if(InitX > 0 && InitY > 0){
			g2d.drawOval(Math.min(InitX, PreX), Math.min(InitY, PreY),
					Math.abs(InitX - PreX), Math.abs(InitY - PreY));
		}
		else
			g2d.drawString("", 1, 1);
	}
}
