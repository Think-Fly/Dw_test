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
	��ͼ�������������
	���α�д


*/

class RandomShape extends DrawingPanel{
	private int[] x;
	private int[] y;
	private int PreX;
	private int PreY;
	private int count;
	public RandomShape(BufferedImage[] ImageArray, JLabel mLabel){
		super(ImageArray, mLabel);
		x = new int[30];
		y = new int [30];
		PreX = -1;
		PreY = -1;
		count = 0;
		int i;
		for(i = 0; i < 30; i++){
			x[i] = -1;
			y[i] = -1;
		}
		//��Ӽ�����
		addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				if(x[0] < 0 && y[0] < 0){
					x[0] = e.getX();
					y[0] = e.getY();
					count++;
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
				if(x[0] < 0 && y[0] < 0){
					x[0] = e.getX();
					y[0] = e.getY();
					count++;
				}
				else if(x[count] < 0 && y[count] < 0){
					x[count] = e.getX();
					y[count] = e.getY();
					if(Math.abs(x[count] - x[0]) < mStroke
							&& Math.abs(y[count] - y[0]) < mStroke){//���ص������
						drawRandomShape(x, y, count);
						count = 0;
						repaint();
						int i;
						for(i = 0; i < 30; i++){
							x[i] = -1;
							y[i] = -1;
						}
					}
					else{
						count++;
						if(count == 31){
							int i;
							for(i = 0; i < 30; i++){
								x[i] = -1;
								y[i] = -1;
							}
							count = 0;
						}
						else
						repaint();
					}
				}
			}
		});
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(R, G, B));//�����R G B
		g2d.setStroke(new BasicStroke(mStroke));
		if(count != 0){
			if(count == 1 && PreX > 0 && PreY > 0)
				g2d.drawLine(x[0], y[0], PreX, PreY);
			g2d.drawPolyline(x, y, count);
		}
		else{
			g2d.drawString("", 1, 1);
		}
	}
}
