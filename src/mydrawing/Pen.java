package mydrawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/*
	绘图组件：铅笔
	刘俊杰编写


*/

class Pen extends DrawingPanel{
	private int PreX;
	private int PreY;
	private int PointCount;
	private BufferedImage PenImage;
	private Graphics2D g2d_Pen;
	private final int MAX_COUNT = 100;
	
	public Pen(BufferedImage[] ImageArray, JLabel mLabel){
		super(ImageArray, mLabel);
		PreX = -1;
		PreY = -1;
		PointCount = 0;
		PenImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d_Pen = PenImage.createGraphics();//通过实例化BufferedImage获得Graphics2D
		g2d_Pen.fillRect(0, 0, DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT);
		setDrawingConf(g2d_Pen);
		g2d_Pen.drawImage(ImageArray[0], 0, 0, null);//
		repaint();
		
		//添加监听器//public abstract class MouseMotionAdapter implements MouseMotionListener
		addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				if(PointCount == 0){
					g2d_Pen.setColor(Color.white);
					g2d_Pen.fillRect(0, 0, DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT);
					setDrawingConf(g2d_Pen);
					g2d_Pen.drawImage(ImageArray[0], 0, 0, null);
				}
				PointCount++;
			/**************************************************************************/
				if(PreX > 0 || PreY > 0){
					drawLine(PreX, PreY, e.getX(), e.getY());
				}
				PreX = e.getX();//画线起点的xy坐标值，
				PreY = e.getY();
				String coordText ;
				coordText = "";
				if(PreX < DRAWING_PANEL_WIDTH && PreX >= 0
						&& PreY >= 0 && PreY < DRAWING_PANEL_HEIGHT){
					coordText = String.valueOf(e.getX()) + ","
							+ String.valueOf(e.getY() ) + "   像素";//横、纵坐标 转换为字符串
				}
				coordText = coordText + "                              "+
						DRAWING_PANEL_WIDTH + "×"+" " + DRAWING_PANEL_HEIGHT
						+"像素";//1200*500
					mLabel.setText(coordText);
				repaint();
			}
		});
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				
				PointCount = MAX_COUNT;
				drawLine(PreX, PreY, e.getX(), e.getY());//连接起始点
				repaint();
				PreX = -1;
				PreY = -1;
			}
		});
	}
	
	@Override
	public void drawLine(int x0, int y0, int x1, int y1){
		setDrawingConf(g2d_Pen);
		if(PointCount == MAX_COUNT){//refer to mouseReleased
			g2d_Pen.drawLine(x0, y0, x1, y1);
			BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d_Temp = tempImage.createGraphics();
			int i;
			for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
				ImageArray[i + 1] = ImageArray[i];
			}
			setDrawingConf(g2d_Temp);
			g2d_Temp.drawImage(PenImage, 0, 0, null);
			ImageArray[0] = tempImage;
			PointCount = 0;
		}
		else{
			g2d_Pen.drawLine(x0, y0, x1, y1);
		}
	}
	
	//铅笔图标
	@Override
	public void paint(Graphics g){
		if(PointCount == 0){
			g.drawString("", 0, 0);
			super.paint(g);
		}
		else{
			g.drawImage(PenImage, 0, 0, null);
		}
		
	}
}
