package mydrawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/*
	»æÍ¼×é¼þ£º×Ö·û´®»æÖÆ
	½ªöÎ±àÐ´


*/
class MyString extends DrawingPanel{
	public static String mString;
	private int PreX, PreY;
	public MyString(BufferedImage[] ImageArray, JLabel mLabel){
		super(ImageArray, mLabel);
		PreX = -1;
		PreY = -1;
		addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseMoved(MouseEvent e){
				String coordText ;
				coordText = "";
					coordText = String.valueOf(e.getX()) + ","
							+ String.valueOf(e.getY() ) + "   ÏñËØ";
					coordText = coordText + "                              "+
							DRAWING_PANEL_WIDTH + "¡Á"+" " + DRAWING_PANEL_HEIGHT
							+"ÏñËØ";
				mLabel.setText(coordText);
				PreX = e.getX();
				PreY = e.getY();
				repaint();
			}
		});
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON1){//Êó±ê×ó¼ü
					drawString(mString, e.getX(), e.getY());
				}
				repaint();
				PreX = -1;
				PreY = -1;
				mString = "";
			}
		});
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(R, G, B));//¸¸ÀàµÄR G B
		g2d.setFont(new Font(FontType, Font.PLAIN, 5*(int)mStroke));
		g2d.setStroke(new BasicStroke(mStroke));
		if(PreX > 0 && PreY > 0){
			g2d.drawString(mString, PreX, PreY);
		}
		else
			g2d.drawString("", 1, 1);
	}
}
