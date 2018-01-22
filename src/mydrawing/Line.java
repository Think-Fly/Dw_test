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

绘图组件：线段
刘俊杰编写


*/
class Line extends DrawingPanel{
	private int InitX;
	private int InitY;
	private int PreX;
	private int PreY;
	
	public Line(BufferedImage[] ImageArray, JLabel mLabel){
		super(ImageArray, mLabel);
		InitX = -1;
		InitY = -1;
		//添加监听器
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
							+ String.valueOf(e.getY() ) + "   像素";
				}
				coordText = coordText + "                              "+
						DRAWING_PANEL_WIDTH + "×"+" " + DRAWING_PANEL_HEIGHT
						+"像素";
					mLabel.setText(coordText);
				repaint();
			}
		});
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				drawLine(InitX, InitY, e.getX(), e.getY());
				InitX = -1;
				InitY = -1;
			}
		});
	}
//needed!!! 否则 画线时没有轨迹，画完后才能看到
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(R, G, B));//父类的R G B
		g2d.setStroke(new BasicStroke(mStroke));
		if(InitX > 0 && InitY > 0){
			g2d.drawLine(InitX, InitY, PreX, PreY);
		}
		else
			g2d.drawString("", 1, 1);
	}
}
