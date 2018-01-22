package mydrawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * 所有的绘图操作都通过该父类完成
 * 绘图属性 线宽与颜色皆为父类的常量
 * 每次作图时，都需要配置颜色线宽参数
 *由姜鑫编写
 */
class DrawingPanel extends JPanel{
	public static int DRAWING_PANEL_WIDTH = 1200;
	public static int DRAWING_PANEL_HEIGHT = 500;
	protected BufferedImage[] ImageArray;
	private JLabel mLabel;
	//绘图全局变量
	public static float mStroke = 5.f;//默认线条宽度
	public static int R = 0, G = 0, B = 0;
	public static String FontType = "宋体";
	
	public DrawingPanel(BufferedImage[] ImageArray, JLabel mLabel){
		super();
		setBackground(Color.white);
		setPreferredSize(new Dimension(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT));
		this.ImageArray = ImageArray;
		repaint();
		this.mLabel = mLabel;
		addMouseMotionListener(new MouseMotionAdapter(){//鼠标运动监听机制，
			@Override
			public void mouseMoved(MouseEvent e){
				String coordText ;
				coordText = "";
					coordText = String.valueOf(e.getX()) + ","
							+ String.valueOf(e.getY() ) + "   像素";
				coordText = coordText + "                              "+
							DRAWING_PANEL_WIDTH + "×"+" " + DRAWING_PANEL_HEIGHT
							+"像素";
				mLabel.setText(coordText);
			
			}
		});
	}
	
	public void setDrawingConf(Graphics2D g2d){
		g2d.setStroke(new BasicStroke(mStroke));//设置线条宽度
		g2d.setColor(new Color(R, G, B));//设置画笔颜色
	}
	
    /*******自定义画string的方法，主要任务还是由Graphics2D.drawString来完成的******************************/	
	public void drawString(String str, int x, int y){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);//refer to definition
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}                                        //栈，撤销********************************************************
		setDrawingConf(g2d_Temp);//即默认的线宽和color
		g2d_Temp.setFont(new Font(FontType, Font.PLAIN, 5*(int)mStroke));
		g2d_Temp.drawString(str, x, y);//调用Graphics2D画String的方法
		ImageArray[0] = tempImage;
	}
	//绘制矩形，空心
	public void drawRect(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(y1 - y0));
		ImageArray[0] = tempImage;
	}
	//绘制正方形，空心
	public void drawSqu(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(x1 - x0));//对比矩形画法，这里高度与宽度相同，即正方形
		ImageArray[0] = tempImage;
	}
	//绘制圆，空心
	public void drawCircle(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawOval(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(x1 - x0));//oval-椭圆，高度=宽度--圆
		ImageArray[0] = tempImage;
	}
	//绘制椭圆，ellipse,空心
	public void drawEcli(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawOval(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(y1 - y0));
		ImageArray[0] = tempImage;
	}
	//圆角矩形 空心
	public void drawRoundRect(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawRoundRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(y1 - y0), 25, 25);
		ImageArray[0] = tempImage;
	}
	//画线
	public void drawLine(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawLine(x0, y0, x1, y1);
		ImageArray[0] = tempImage;
	}
	//橡皮檫功能，用白色线掩盖原来的线
	public void clearLine(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		g2d_Temp.setStroke(new BasicStroke(mStroke*3.f));
		g2d_Temp.setColor(Color.WHITE);
		g2d_Temp.drawLine(x0, y0, x1, y1);
		ImageArray[0] = tempImage;
	}
	//绘制圆角正方形，空心
	public void drawRoundSqu(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawRoundRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(x1 - x0), 25, 25);
		ImageArray[0] = tempImage;
	}
	//实心矩形
	public void drawFillRect(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.fillRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(y1 - y0));
		ImageArray[0] = tempImage;
	}
	//实心正方形
	public void drawFillSqu(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.fillRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(x1 - x0));
		ImageArray[0] = tempImage;
	}
	//实心圆
	public void drawFillCircle(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.fillOval(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(x1 - x0));
		ImageArray[0] = tempImage;
	}
	//实心椭圆
	public void drawFillEcli(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.fillOval(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(y1 - y0));
		ImageArray[0] = tempImage;
	}
	//实心圆角矩形
	public void drawFillRoundRect(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.fillRoundRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(y1 - y0), 25, 25);
		ImageArray[0] = tempImage;
	}
	//实心圆角方形
	public void drawFillRoundSqu(int x0, int y0, int x1, int y1){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.fillRoundRect(Math.min(x0, x1), Math.min(y0, y1),
				Math.abs(x1 - x0), Math.abs(x1 - x0), 25, 25);
		ImageArray[0] = tempImage;
	}
	//空心三角形
	public void drawTriangle(int x0, int y0, int x1, int y1, int x2, int y2){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawLine(x0, y0, x1, y1);
		g2d_Temp.drawLine(x0, y0, x2, y2);
		g2d_Temp.drawLine(x1, y1, x2, y2);
		ImageArray[0] = tempImage;
	}
	
	public void drawRandomShape(int[] x, int[] y, int count){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}
		setDrawingConf(g2d_Temp);
		g2d_Temp.drawPolygon(x, y, count);//绘制多边形
		ImageArray[0] = tempImage;
	}

	@Override
	public void paint(Graphics g){          
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;//转换g为2D对象
		BufferedImage imagePaint = ImageArray[0];
		g2d.drawImage(imagePaint, 0, 0, null);
	}
}
