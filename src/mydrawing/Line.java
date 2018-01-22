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

��ͼ������߶�
�����ܱ�д


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
		//��Ӽ�����
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
							+ String.valueOf(e.getY() ) + "   ����";
				}
				coordText = coordText + "                              "+
						DRAWING_PANEL_WIDTH + "��"+" " + DRAWING_PANEL_HEIGHT
						+"����";
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
//needed!!! ���� ����ʱû�й켣���������ܿ���
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(R, G, B));//�����R G B
		g2d.setStroke(new BasicStroke(mStroke));
		if(InitX > 0 && InitY > 0){
			g2d.drawLine(InitX, InitY, PreX, PreY);
		}
		else
			g2d.drawString("", 1, 1);
	}
}
