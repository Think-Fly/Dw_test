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
 * ���еĻ�ͼ������ͨ���ø������
 * ��ͼ���� �߿�����ɫ��Ϊ����ĳ���
 * ÿ����ͼʱ������Ҫ������ɫ�߿����
 *�ɽ��α�д
 */
class DrawingPanel extends JPanel{
	public static int DRAWING_PANEL_WIDTH = 1200;
	public static int DRAWING_PANEL_HEIGHT = 500;
	protected BufferedImage[] ImageArray;
	private JLabel mLabel;
	//��ͼȫ�ֱ���
	public static float mStroke = 5.f;//Ĭ���������
	public static int R = 0, G = 0, B = 0;
	public static String FontType = "����";
	
	public DrawingPanel(BufferedImage[] ImageArray, JLabel mLabel){
		super();
		setBackground(Color.white);
		setPreferredSize(new Dimension(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT));
		this.ImageArray = ImageArray;
		repaint();
		this.mLabel = mLabel;
		addMouseMotionListener(new MouseMotionAdapter(){//����˶��������ƣ�
			@Override
			public void mouseMoved(MouseEvent e){
				String coordText ;
				coordText = "";
					coordText = String.valueOf(e.getX()) + ","
							+ String.valueOf(e.getY() ) + "   ����";
				coordText = coordText + "                              "+
							DRAWING_PANEL_WIDTH + "��"+" " + DRAWING_PANEL_HEIGHT
							+"����";
				mLabel.setText(coordText);
			
			}
		});
	}
	
	public void setDrawingConf(Graphics2D g2d){
		g2d.setStroke(new BasicStroke(mStroke));//�����������
		g2d.setColor(new Color(R, G, B));//���û�����ɫ
	}
	
    /*******�Զ��廭string�ķ�������Ҫ��������Graphics2D.drawString����ɵ�******************************/	
	public void drawString(String str, int x, int y){
		BufferedImage tempImage = new BufferedImage(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d_Temp = tempImage.createGraphics();
		g2d_Temp.drawImage(ImageArray[0], 0, 0, null);//refer to definition
		int i;
		for(i = MyUI.ImageArraySize - 2; i >= 0; i--){
			ImageArray[i + 1] = ImageArray[i];
		}                                        //ջ������********************************************************
		setDrawingConf(g2d_Temp);//��Ĭ�ϵ��߿��color
		g2d_Temp.setFont(new Font(FontType, Font.PLAIN, 5*(int)mStroke));
		g2d_Temp.drawString(str, x, y);//����Graphics2D��String�ķ���
		ImageArray[0] = tempImage;
	}
	//���ƾ��Σ�����
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
	//���������Σ�����
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
				Math.abs(x1 - x0), Math.abs(x1 - x0));//�ԱȾ��λ���������߶�������ͬ����������
		ImageArray[0] = tempImage;
	}
	//����Բ������
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
				Math.abs(x1 - x0), Math.abs(x1 - x0));//oval-��Բ���߶�=���--Բ
		ImageArray[0] = tempImage;
	}
	//������Բ��ellipse,����
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
	//Բ�Ǿ��� ����
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
	//����
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
	//��Ƥ�߹��ܣ��ð�ɫ���ڸ�ԭ������
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
	//����Բ�������Σ�����
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
	//ʵ�ľ���
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
	//ʵ��������
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
	//ʵ��Բ
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
	//ʵ����Բ
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
	//ʵ��Բ�Ǿ���
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
	//ʵ��Բ�Ƿ���
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
	//����������
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
		g2d_Temp.drawPolygon(x, y, count);//���ƶ����
		ImageArray[0] = tempImage;
	}

	@Override
	public void paint(Graphics g){          
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;//ת��gΪ2D����
		BufferedImage imagePaint = ImageArray[0];
		g2d.drawImage(imagePaint, 0, 0, null);
	}
}
