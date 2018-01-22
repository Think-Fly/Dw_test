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
	»æÍ¼×é¼þ£ºÏðÆ¤²Á
	½ªöÎ±àÐ´


*/
class Eraser extends DrawingPanel{
	private int PreX;
	private int PreY;
	private int PointCount;
	private BufferedImage EraserImage;
	private Graphics2D g2d_Eraser;
	private final int MAX_COUNT = 100;
	
	public Eraser(BufferedImage[] ImageArray, JLabel mLabel){//related to Pen
		super(ImageArray, mLabel);
		PreX = -1;
		PreY = -1;
		PointCount = 0;
		EraserImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2d_Eraser = EraserImage.createGraphics();
		g2d_Eraser.fillRect(0, 0, DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT);
		setDrawingConf(g2d_Eraser);
		g2d_Eraser.drawImage(ImageArray[0], 0, 0, null);
		repaint();
		//Ìí¼Ó¼àÌýÆ÷
		addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				if(PointCount == 0){
					g2d_Eraser.setColor(Color.white);
					g2d_Eraser.fillRect(0, 0, DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT);
					setDrawingConf(g2d_Eraser);
					g2d_Eraser.drawImage(ImageArray[0], 0, 0, null);
				}
				PointCount++;
				if(PreX > 0 || PreY > 0){
					clearLine(PreX, PreY, e.getX(), e.getY());
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
				PointCount = MAX_COUNT;
				clearLine(PreX, PreY, e.getX(), e.getY());//compare to pen(drawline)
				repaint();
				PreX = -1;
				PreY = -1;
			}
		});
	}
	
	@Override 
	public void clearLine(int x0, int y0, int x1, int y1){
		Graphics2D g2d = ImageArray[0].createGraphics();
		setDrawingConf(g2d_Eraser);
		if(PointCount == MAX_COUNT){
			g2d_Eraser.setColor(Color.white);
			g2d_Eraser.setStroke(new BasicStroke(mStroke*3.f));
			g2d_Eraser.drawLine(x0, y0, x1, y1);
			BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d_Temp = tempImage.createGraphics();
			int i;
			for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
				ImageArray[i + 1] = ImageArray[i];
			}
			setDrawingConf(g2d_Temp);
			g2d_Temp.drawImage(EraserImage, 0, 0, null);
			ImageArray[0] = tempImage;
			PointCount = 0;
		}
		else{
			g2d_Eraser.setColor(Color.white);
			g2d_Eraser.setStroke(new BasicStroke(mStroke*3.f));
			g2d_Eraser.drawLine(x0, y0, x1, y1);
		}
	}
	
	@Override
	public void paint(Graphics g){
		if(PointCount == 0){
			g.drawString("", 0, 0);
			super.paint(g);
		}
		else{
			if(PreX > 0 && PreY > 0){
				g.drawImage(EraserImage, 0, 0, null);
				g.drawRect(PreX - 2*(int)mStroke, PreY - 2*(int)mStroke,
						4*(int)mStroke, 4*(int)mStroke);//ÏðÆ¤²Á³Ê·½ÐÎ
		
			}
		}
	}
}
