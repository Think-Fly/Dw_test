package mydrawing;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
*  该类为整个程序的主类，所有的UI界面配置，按键功能的实现
*由该类完成。其中 init()函数为UI界面的设置，由吕绪明编写
*其他为按键的监听器实现及其他为姜鑫编写
*整体软件的搭建由姜鑫完成
**/
class MyUI {
	//窗口的大小
	private final int FRAME_WIDTH = 1400;
	private final int FRAME_HEIGHT = 700;
	private final int MENUBAR_WIDTH = FRAME_WIDTH;
	private final int MENUBAR_HEIGHT = FRAME_HEIGHT/20;//文件栏（菜单栏）
	private final int PANEL_TOOL_WIDTH = FRAME_WIDTH;
	private final int PANEL_TOOL_HEIGHT = FRAME_HEIGHT*3/20;//工具栏
	private final int PANEL_DRAWING_WIDTH = FRAME_WIDTH;
	private final int PANEL_DRAWING_HEIGHT = FRAME_HEIGHT - 
			MENUBAR_HEIGHT - PANEL_TOOL_HEIGHT;
	//三个区域相对坐标
	private final int MENUBAR_X = 0;
	private final int MENUBAR_Y = 0;
	private final int PANEL_TOOL_X = 0;
	private final int PANEL_TOOL_Y =MENUBAR_Y + MENUBAR_HEIGHT;
	private final int PANEL_DRAWING_X = 0;
	private final int PANEL_DRAWING_Y = PANEL_TOOL_Y + PANEL_TOOL_HEIGHT;
	
	//工具栏每个区域的大小，对该区域进行分割，高度相同，宽度分割
	private final int PANEL_TOOL_TOOL_WIDTH = 2*PANEL_TOOL_WIDTH/10;
	private final int PANEL_TOOL_TOOL_HEIGHT = PANEL_TOOL_HEIGHT;
	private final int PANEL_TOOL_SHAPE_WIDTH = 3*PANEL_TOOL_WIDTH/10;
	private final int PANEL_TOOL_SHAPE_HEIGHT = PANEL_TOOL_HEIGHT;
	private final int PANEL_TOOL_SETSTROKE_WIDTH = 1*PANEL_TOOL_WIDTH/10;
	private final int PANEL_TOOL_SETSTROKE_HEIGHT = PANEL_TOOL_HEIGHT;
	private final int PANEL_TOOL_SELECTCOLOR_WIDTH = 2*PANEL_TOOL_WIDTH/10;
	private final int PANEL_TOOL_SELECTCOLOR_HEIGHT = PANEL_TOOL_HEIGHT;
	private final int PANEL_TOOL_TRANSFORM_WIDTH = 2*PANEL_TOOL_WIDTH/10;
	private final int PANEL_TOOL_TRANSFORM_HEIGHT = PANEL_TOOL_HEIGHT;
	
	//工具栏中区域相对的位置，定了大小后定位置，相对
	private final int PANEL_TOOL_TOOL_X = 0;
	private final int PANEL_TOOL_TOOL_Y = 0;
	private final int PANEL_TOOL_SHAPE_X = PANEL_TOOL_TOOL_X + PANEL_TOOL_TOOL_WIDTH;
	private final int PANEL_TOOL_SHAPE_Y = 0;
	private final int PANEL_TOOL_SETSTROKE_X = PANEL_TOOL_SHAPE_X + PANEL_TOOL_SHAPE_WIDTH;
	private final int PANEL_TOOL_SETSTROKE_Y = 0;
	private final int PANEL_TOOL_SELECTCOLOR_X = PANEL_TOOL_SETSTROKE_X + PANEL_TOOL_SETSTROKE_WIDTH;
	private final int PANEL_TOOL_SELECTCOLOR_Y = 0;
	private final int PANEL_TOOL_TRANSFORM_X = PANEL_TOOL_SELECTCOLOR_X + PANEL_TOOL_SELECTCOLOR_WIDTH;
	private final int PANEL_TOOL_TRANSFORM_Y = 0;
	
	//绘图区
	
	private final int PANEL_DRAWING_DRAWING_X = 10;
	private final int PANEL_DRAWING_DRAWING_Y = 10;
	private final int PANEL_DRAWING_LABEL_X = 0;
	private final int PANEL_DRAWING_LABEL_Y = PANEL_DRAWING_HEIGHT*19/20;
	private final int SCROLLPANE_MAX_WIDTH = 1300;
	private final int SCROLLPANE_MAX_HEIGHT = 530;
	
	//窗口框架:BorderLayout
	JFrame jf;
	
	//菜单类:方位_NORTH
	JMenuBar mMenuBar;
	//文件菜单
	JMenu mMenu_File;
	JMenuItem mMenuItem_New;
	JMenuItem mMenuItem_Save;
	JMenuItem mMenuItem_Open;
	JMenuItem mMenuItem_Exit;
	//查看菜单
	JMenu mMenu_View;
	
	//工具栏:方位_CENTER
	JPanel mPanel_ToolBar;
	JPanel mPanel_ToolBar_Tool; 
	JPanel mPanel_ToolBar_Shape;
	JScrollPane mPanel_ToolBar_Shape_ScrollPane;
	JPanel mPanel_Shape_ScrollPane;
	JPanel mPanel_ToolBar_setStroke;
	JPanel mPanel_ToolBar_selectColor;
	JPanel mPanel_ToolBar_Transform;
	
	//绘图区：方位_SOUTH + 坐标显示文字区
	JPanel mPanel_Draw;
	DrawingPanel mPanel_Draw_DrawingPanel;
	JPanel mPanel_Draw_TextView;
	JScrollPane mScrollPane;
	JPanel mPanel;
	
	//工具按钮_工具栏	mButton
	JButton mButton_Pen;
	JButton mButton_String;
	JButton mButton_Eraser;
	JButton mButton_Redo;
	//工具按钮_形状栏	
	JButton mButton_Line;
	JButton mButton_Rect;
	JButton mButton_Squ;
	JButton mButton_Circle;
	JButton mButton_Ecli;
	JButton mButton_RoundRect;
	JButton mButton_RoundSqu;
	JButton mButton_RandomShape;
	JButton mButton_Triangle;
	JButton mButton_FillRect;
	JButton mButton_FillSqu;
	JButton mButton_FillCircle;
	JButton mButton_FillEcli;
	JButton mButton_FillRoundRect;
	JButton mButton_FillRoundSqu;
	//工具按钮_线框栏
	JButton mButton_setStroke;
	JButton mButton_selectColor;
	//工具按钮_变换栏
	JButton mButton_Smaller;
	JButton mButton_Bigger;
	
	//文字栏
	JPanel mPanel_mTextArea_Tool;
	JLabel mTextArea_Tool;
	JPanel mPanel_mTextArea_Shape;
	JLabel mTextArea_Shape;
	JPanel mPanel_mTextArea_Stroke;
	JLabel mTextArea_Stroke;
	JPanel mPanel_mTextArea_Color;
	JLabel mTextArea_Color;
	JPanel mPanel_mTextArea_Transform;
	JLabel mTextArea_Transform;
	JPanel mPanel_mTextArea_Coord;
	JLabel mTextArea_Coord;
	
	//绘图数组
	public static BufferedImage[] mImageArray;
	public static int ImageArraySize = 20;
	
	//绘图相关  使用DrawingPanel全局变量Stroke && RGB
	Graphics2D g2d;

	//字体
	Font font;
	
	//对话框类
	
	//文件保存，打开类
	JFileChooser mFileChooser;
	private File OpenFile, SaveFile;
	
	//光标类
	private  Cursor Cursor_Pen;
	//private Cursor Cursor_Eraser;
	private Toolkit mToolkit;
	
	public MyUI(){//构造方法,一定会执行的
		init();
	}

	private void init(){
		//设置字体
		Font font = new Font("楷体", Font.PLAIN, 15);
		//设置窗口及框架
		ImageIcon Icon_Paint = new ImageIcon("image/Paint.png");
		jf = new JFrame("Drawing");
		jf.setLayout(null);
		jf.setSize(FRAME_WIDTH+20, FRAME_HEIGHT+20);//设置窗口大小
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		jf.setIconImage(Icon_Paint.getImage());
		jf.setLayout(null);
		
		//设置菜单栏
		mMenuBar = new JMenuBar();
		mMenuBar.setBounds(MENUBAR_X, MENUBAR_Y, MENUBAR_WIDTH, MENUBAR_HEIGHT);
		mMenuBar.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		//设置菜单栏选项
		mMenu_File = new JMenu("文件");
		//设置具体选项_新建：快捷键ctrl+n
		mMenuItem_New = new JMenuItem("新建");
		mMenuItem_New.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK ));
		//保存：快捷键ctrl+s
		mMenuItem_Save = new JMenuItem("保存");
		mMenuItem_Save.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK ));
		//打开：快捷键ctrl+o
		mMenuItem_Open = new JMenuItem("打开");
		mMenuItem_Open.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK ));
		//退出：快捷键ctrl+e
		mMenuItem_Exit = new JMenuItem("退出");
		mMenuItem_Exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK ));
		//将菜单项加入菜单中
		mMenu_File.add(mMenuItem_New);
		mMenu_File.add(mMenuItem_Save);
		mMenu_File.add(mMenuItem_Open);
		mMenu_File.addSeparator();//相当于加根横线隔开
		mMenu_File.add(mMenuItem_Exit);
		mMenuBar.add(mMenu_File);//菜单条中加入菜单
		
		//设置工具栏
		mPanel_ToolBar = new JPanel();//使用默认的浮动布局
		mPanel_ToolBar.setLayout(null);//设置布局管理器为空，自定义布局
		mPanel_ToolBar.setBounds(PANEL_TOOL_X ,PANEL_TOOL_Y, PANEL_TOOL_WIDTH,
				PANEL_TOOL_HEIGHT);
		mPanel_ToolBar.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));//即边框,否则看起来空白的
		
		//设置工具栏：工具选项
		mPanel_ToolBar_Tool = new JPanel(new BorderLayout());//容器的的布局分为五个位置
		mPanel_ToolBar_Tool.setBounds(PANEL_TOOL_TOOL_X, PANEL_TOOL_TOOL_Y, PANEL_TOOL_TOOL_WIDTH,
				PANEL_TOOL_TOOL_HEIGHT);
		mPanel_ToolBar_Tool.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		mPanel_ToolBar_Tool.setBackground(new Color(245, 246, 247));
		mPanel_ToolBar_Tool.setLayout(null);
		mPanel_mTextArea_Tool = new JPanel();//文字栏
		mPanel_mTextArea_Tool.setBounds(2, PANEL_TOOL_TOOL_HEIGHT*3/4, PANEL_TOOL_TOOL_WIDTH -4,
				PANEL_TOOL_TOOL_HEIGHT*1/4 - 2);
		mPanel_mTextArea_Tool.setBackground(new Color(245, 246, 247));
		mTextArea_Tool = new JLabel("工具");//JLabel mTextArea_Tool;
		mTextArea_Tool.setFont(font);
		mPanel_mTextArea_Tool.add(mTextArea_Tool);
		mPanel_ToolBar_Tool.add(mPanel_mTextArea_Tool);
		//PenButton
		ImageIcon Icon_Pen = new ImageIcon(MyUI.class.getResource("/image/Pen.png"));
		mButton_Pen = new JButton("", Icon_Pen);
		mButton_Pen.setActionCommand("Pen");//为这个按钮设置一个属性字符串值，以之后判断哪个按钮触发了事件
		mButton_Pen.setBounds(PANEL_TOOL_TOOL_WIDTH/5 - 20, PANEL_TOOL_TOOL_HEIGHT/2 - 16,
				40, 32);
		mButton_Pen.setToolTipText("铅笔：用选定的线宽画任意的图形");
		mPanel_ToolBar_Tool.add(mButton_Pen);
		//EraserButton
		ImageIcon Icon_Eraser = new ImageIcon(MyUI.class.getResource("/image/Eraser.png"));
		mButton_Eraser = new JButton("", Icon_Eraser);
		mButton_Eraser.setActionCommand("Eraser");
		mButton_Eraser.setBounds(PANEL_TOOL_TOOL_WIDTH*2/5 - 20, PANEL_TOOL_TOOL_HEIGHT/2 - 16,
				40, 32);
		mButton_Eraser.setToolTipText("橡皮：涂改之前的图形");
		mPanel_ToolBar_Tool.add(mButton_Eraser);
		//StringButton
		ImageIcon Icon_String = new ImageIcon(MyUI.class.getResource("/image/String.png"));
		mButton_String = new JButton("", Icon_String);
		mButton_String.setActionCommand("String");
		mButton_String.setBounds(PANEL_TOOL_TOOL_WIDTH*3/5 - 20, PANEL_TOOL_TOOL_HEIGHT/2 - 16,
				40, 32);
		mButton_String.setToolTipText("输入字符串");
		mPanel_ToolBar_Tool.add(mButton_String);
		//RedoButton
		ImageIcon Icon_Redo = new ImageIcon(MyUI.class.getResource("/image/Redo.png"));
		mButton_Redo = new JButton("", Icon_Redo);
		mButton_Redo.setActionCommand("Redo");
		mButton_Redo.setBounds(PANEL_TOOL_TOOL_WIDTH*4/5 - 20, PANEL_TOOL_TOOL_HEIGHT/2 - 16,
				40, 32);
		mButton_Redo.setToolTipText("撤销本次操作");
		mPanel_ToolBar_Tool.add(mButton_Redo);
		
		
		//设置工具栏：形状选项
		mPanel_ToolBar_Shape = new JPanel(new BorderLayout());
		mPanel_ToolBar_Shape.setLayout(null);
		mPanel_ToolBar_Shape.setBounds(PANEL_TOOL_SHAPE_X, PANEL_TOOL_SHAPE_Y, PANEL_TOOL_SHAPE_WIDTH,
				PANEL_TOOL_SHAPE_HEIGHT);
		mPanel_ToolBar_Shape.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		mPanel_ToolBar_Shape.setBackground(new Color(245, 246, 247));
		
		mPanel_mTextArea_Shape = new JPanel();
		mPanel_mTextArea_Shape.setBounds(2, PANEL_TOOL_SHAPE_HEIGHT*3/4, PANEL_TOOL_SHAPE_WIDTH - 4,
				PANEL_TOOL_SHAPE_HEIGHT*1/4 - 2);
		mPanel_mTextArea_Shape.setBackground(new Color(245, 246, 247));
		mTextArea_Shape = new JLabel("形状");
		mTextArea_Shape.setFont(font);
		mPanel_mTextArea_Shape.add(mTextArea_Shape);//先将标签添加到定义的文本框中，再将文本框添加到JPanel容器
		mPanel_ToolBar_Shape.add(mPanel_mTextArea_Shape);
		
		//形状选项：加入形状图标,其中组件包含在mPanel_Shape_ScrollPane中
		mPanel_Shape_ScrollPane = new JPanel();
		mPanel_Shape_ScrollPane.setBackground(Color.white);
		mPanel_Shape_ScrollPane.setLayout(new GridLayout(5, 3));
		//开始加入形状按键：LineButton
		ImageIcon Icon_Line = new ImageIcon(MyUI.class.getResource("/image/Line.png"));
		mButton_Line = new JButton("", Icon_Line);
		mButton_Line.setActionCommand("Line");
		mButton_Line.setToolTipText("线");
		mPanel_Shape_ScrollPane.add(mButton_Line);
		//RectButton
		ImageIcon Icon_Rect = new ImageIcon(MyUI.class.getResource("/image/Rect.png"));
		mButton_Rect = new JButton("", Icon_Rect);
		mButton_Rect.setActionCommand("Rect");
		mButton_Rect.setToolTipText("矩形");
		mPanel_Shape_ScrollPane.add(mButton_Rect);
		//SquButton
		ImageIcon Icon_Squ = new ImageIcon(MyUI.class.getResource("/image/Squ.png"));
		mButton_Squ = new JButton("", Icon_Squ);
		mButton_Squ.setActionCommand("Squ");
		mButton_Squ.setToolTipText("正方形");
		mPanel_Shape_ScrollPane.add(mButton_Squ);
		//CircleButton
		ImageIcon Icon_Circle = new ImageIcon(MyUI.class.getResource("/image/Circle.png"));
		mButton_Circle = new JButton("", Icon_Circle);
		mButton_Circle.setActionCommand("Circle");
		mButton_Circle.setToolTipText("圆");
		mPanel_Shape_ScrollPane.add(mButton_Circle);
		//EcliButton
		ImageIcon Icon_Ecli = new ImageIcon(MyUI.class.getResource("/image/Ecli.png"));
		mButton_Ecli = new JButton("", Icon_Ecli);
		mButton_Ecli.setActionCommand("Ecli");
		mButton_Ecli.setToolTipText("椭圆");
		mPanel_Shape_ScrollPane.add(mButton_Ecli);
		//RoundRectButton
		ImageIcon Icon_RoundRect = new ImageIcon(MyUI.class.getResource("/image/RoundRect.png"));
		mButton_RoundRect = new JButton("", Icon_RoundRect);
		mButton_RoundRect.setActionCommand("RoundRect");
		mButton_RoundRect.setToolTipText("圆角矩形");
		mPanel_Shape_ScrollPane.add(mButton_RoundRect);
		//RoundSquButton
		ImageIcon Icon_RoundSqu = new ImageIcon(MyUI.class.getResource("/image/RoundSqu.png"));
		mButton_RoundSqu = new JButton("", Icon_RoundSqu);
		mButton_RoundSqu.setActionCommand("RoundSqu");
		mButton_RoundSqu.setToolTipText("圆角正方形");
		mPanel_Shape_ScrollPane.add(mButton_RoundSqu);
		//TriangleButton
		ImageIcon Icon_Triangle = new ImageIcon(MyUI.class.getResource("/image/Triangle.png"));
		mButton_Triangle = new JButton("", Icon_Triangle);
		mButton_Triangle.setActionCommand("Triangle");
		mButton_Triangle.setToolTipText("三角形");
		mPanel_Shape_ScrollPane.add(mButton_Triangle);
		//RandomShapeButton
		ImageIcon Icon_RandomShape = new ImageIcon(MyUI.class.getResource("/image/RandomShape.png"));
		mButton_RandomShape = new JButton("", Icon_RandomShape);
		mButton_RandomShape.setActionCommand("RandomShape");
		mButton_RandomShape.setToolTipText("任意多边形(最多十边形)");
		mPanel_Shape_ScrollPane.add(mButton_RandomShape);
		//FillRectButton
		ImageIcon Icon_FillRect = new ImageIcon(MyUI.class.getResource("/image/FillRect.png"));
		mButton_FillRect = new JButton("", Icon_FillRect);
		mButton_FillRect.setActionCommand("FillRect");
		mButton_FillRect.setToolTipText("填充矩形");
		mPanel_Shape_ScrollPane.add(mButton_FillRect);
		//FillSquButton
		ImageIcon Icon_FillSqu = new ImageIcon(MyUI.class.getResource("/image/FillSqu.png"));
		mButton_FillSqu = new JButton("", Icon_FillSqu);
		mButton_FillSqu.setActionCommand("FillSqu");
		mButton_FillSqu.setToolTipText("填充正方形");
		mPanel_Shape_ScrollPane.add(mButton_FillSqu);
		//FillCircleButton
		ImageIcon Icon_FillCircle = new ImageIcon(MyUI.class.getResource("/image/FillCircle.png"));
		mButton_FillCircle = new JButton("", Icon_FillCircle);
		mButton_FillCircle.setActionCommand("FillCircle");
		mButton_FillCircle.setToolTipText("填充圆");
		mPanel_Shape_ScrollPane.add(mButton_FillCircle);
		//FillEcliButton
		ImageIcon Icon_FillEcli = new ImageIcon(MyUI.class.getResource("/image/FillEcli.png"));
		mButton_FillEcli = new JButton("", Icon_FillEcli);
		mButton_FillEcli.setActionCommand("FillEcli");
		mButton_FillEcli.setToolTipText("填充椭圆");
		mPanel_Shape_ScrollPane.add(mButton_FillEcli);
		//FillRoundRect
		ImageIcon Icon_FillRoundRect = new ImageIcon(MyUI.class.getResource("/image/FillRoundRect.png"));
		mButton_FillRoundRect = new JButton("", Icon_FillRoundRect);
		mButton_FillRoundRect.setActionCommand("FillRoundRect");
		mButton_FillRoundRect.setToolTipText("填充圆角矩形");
		mPanel_Shape_ScrollPane.add(mButton_FillRoundRect);
		//FillRoundSqu
		ImageIcon Icon_FillRoundSqu = new ImageIcon(MyUI.class.getResource("/image/FillRoundSqu.png"));
		mButton_FillRoundSqu = new JButton("", Icon_FillRoundSqu);
		mButton_FillRoundSqu.setActionCommand("FillRoundSqu");
		mButton_FillRoundSqu.setToolTipText("填充圆角正方形");
		mPanel_Shape_ScrollPane.add(mButton_FillRoundSqu);
		
		
		//设置滑动条
		mPanel_ToolBar_Shape_ScrollPane = new JScrollPane(mPanel_Shape_ScrollPane);//JScrollPane滚动窗格
		mPanel_ToolBar_Shape_ScrollPane.setBounds(4, 4,
				PANEL_TOOL_SHAPE_WIDTH - 8,PANEL_TOOL_SHAPE_HEIGHT*3/4 - 4);
		mPanel_ToolBar_Shape.add(mPanel_ToolBar_Shape_ScrollPane);
/*
 * 1. JScrollPane(Component com),创建JScrollPane对象，参数com是要放置于JScrollPane对象的组件对象。
 * 为JScrollPane对象指定了显示对象之后，再用add()方法将JScrollPane对象放置于窗口中。	
 */	
		//设置工具栏：设置宽度
		mPanel_ToolBar_setStroke = new JPanel(new BorderLayout());
		mPanel_ToolBar_setStroke.setBounds(PANEL_TOOL_SETSTROKE_X, PANEL_TOOL_SETSTROKE_Y,
				PANEL_TOOL_SETSTROKE_WIDTH, PANEL_TOOL_SETSTROKE_HEIGHT);
		mPanel_ToolBar_setStroke.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		mPanel_ToolBar_setStroke.setBackground(new Color(245, 246, 247));
		mPanel_ToolBar_setStroke.setLayout(null);
		mPanel_mTextArea_Stroke = new JPanel();
		mPanel_mTextArea_Stroke.setBounds(2, PANEL_TOOL_SETSTROKE_HEIGHT*3/4, PANEL_TOOL_SETSTROKE_WIDTH -4,
				PANEL_TOOL_SETSTROKE_HEIGHT*1/4 - 2);
		mPanel_mTextArea_Stroke.setBackground(new Color(245, 246, 247));
		mTextArea_Stroke = new JLabel("设置宽度");
		mTextArea_Stroke.setFont(font);
		mPanel_mTextArea_Stroke.add(mTextArea_Stroke);
		mPanel_ToolBar_setStroke.add(mPanel_mTextArea_Stroke);
		//setStrokeButton
		ImageIcon Icon_Stroke = new ImageIcon(MyUI.class.getResource("/image/Stroke.png"));
		mButton_setStroke = new JButton("", Icon_Stroke);
		mButton_setStroke.setActionCommand("setStroke");
		mButton_setStroke.setBounds(5, PANEL_TOOL_TOOL_HEIGHT/2 - 20,
				100, 40);
		mButton_setStroke.setToolTipText("设置粗细");
		mPanel_ToolBar_setStroke.add(mButton_setStroke);
		

		
		//设置工具栏：选择颜色
		mPanel_ToolBar_selectColor = new JPanel(new BorderLayout());
		mPanel_ToolBar_selectColor.setBounds(PANEL_TOOL_SELECTCOLOR_X, PANEL_TOOL_SELECTCOLOR_Y,
				PANEL_TOOL_SELECTCOLOR_WIDTH, PANEL_TOOL_SELECTCOLOR_HEIGHT);
		mPanel_ToolBar_selectColor.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		mPanel_ToolBar_selectColor.setBackground(new Color(245, 246, 247));
		mPanel_ToolBar_selectColor.setLayout(null);
		mPanel_mTextArea_Color = new JPanel();
		mPanel_mTextArea_Color.setBounds(2, PANEL_TOOL_SELECTCOLOR_HEIGHT*3/4, PANEL_TOOL_SELECTCOLOR_WIDTH -4,
				PANEL_TOOL_SELECTCOLOR_HEIGHT*1/4 - 2);
		mPanel_mTextArea_Color.setBackground(new Color(245, 246, 247));
		mTextArea_Color = new JLabel("选择颜色");
		mTextArea_Color.setFont(font);
		mPanel_mTextArea_Color.add(mTextArea_Color);
		mPanel_ToolBar_selectColor.add(mPanel_mTextArea_Color);
		//setColorButton
		ImageIcon Icon_Color = new ImageIcon(MyUI.class.getResource("/image/Color.png"));
		mButton_selectColor = new JButton("", Icon_Color);
		mButton_selectColor.setActionCommand("selectColor");
		mButton_selectColor.setBounds(5, PANEL_TOOL_SELECTCOLOR_HEIGHT/2 - 28,
				200, 57);
		mButton_selectColor.setToolTipText("选择颜色");
		mPanel_ToolBar_selectColor.add(mButton_selectColor);

		
		
		//设置工具栏：缩放
		mPanel_ToolBar_Transform = new JPanel(new BorderLayout());
		mPanel_ToolBar_Transform.setBounds(PANEL_TOOL_TRANSFORM_X, PANEL_TOOL_TRANSFORM_Y,
				PANEL_TOOL_TRANSFORM_WIDTH, PANEL_TOOL_TRANSFORM_HEIGHT);
		mPanel_ToolBar_Transform.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		mPanel_ToolBar_Transform.setBackground(new Color(245, 246, 247));
		mPanel_ToolBar_Transform.setLayout(null);
		mPanel_mTextArea_Transform = new JPanel();
		mPanel_mTextArea_Transform.setBounds(2, PANEL_TOOL_SELECTCOLOR_HEIGHT*3/4, PANEL_TOOL_SELECTCOLOR_WIDTH -4,
				PANEL_TOOL_SELECTCOLOR_HEIGHT*1/4 - 2);
		mPanel_mTextArea_Transform.setBackground(new Color(245, 246, 247));
		mTextArea_Transform = new JLabel("缩放");
		mTextArea_Transform.setFont(font);
		mPanel_mTextArea_Transform.add(mTextArea_Transform);
		mPanel_ToolBar_Transform.add(mPanel_mTextArea_Transform);
		//SmallerButton
		ImageIcon Icon_Smaller = new ImageIcon(MyUI.class.getResource("/image/Smaller.png"));
		mButton_Smaller = new JButton("", Icon_Smaller);
		mButton_Smaller.setActionCommand("Smaller");
		mButton_Smaller.setBounds(PANEL_TOOL_SELECTCOLOR_WIDTH/3 - 50, PANEL_TOOL_SELECTCOLOR_HEIGHT/2 - 30,
				80, 60);
		mButton_Smaller.setToolTipText("缩小");
		mPanel_ToolBar_Transform.add(mButton_Smaller);
		//BiggerButton
		ImageIcon Icon_Bigger = new ImageIcon(MyUI.class.getResource("/image/Bigger.png"));
		mButton_Bigger = new JButton("", Icon_Bigger);
		mButton_Bigger.setActionCommand("Bigger");
		mButton_Bigger.setBounds(PANEL_TOOL_SELECTCOLOR_WIDTH*2/3 - 40, PANEL_TOOL_SELECTCOLOR_HEIGHT/2 - 30,
				80, 60);
		mButton_Bigger.setToolTipText("放大");
		mPanel_ToolBar_Transform.add(mButton_Bigger);

		
		
		mPanel_ToolBar.add(mPanel_ToolBar_Tool);
		mPanel_ToolBar.add(mPanel_ToolBar_Shape);
		mPanel_ToolBar.add(mPanel_ToolBar_setStroke);
		mPanel_ToolBar.add(mPanel_ToolBar_selectColor);
		mPanel_ToolBar.add(mPanel_ToolBar_Transform);
	
	
		
		
		/***************************绘图区设置********************************/
		mImageArray = new BufferedImage [ImageArraySize];
		int i;
		for(i = 0; i < MyUI.ImageArraySize; i++){
			mImageArray[i] = new BufferedImage(DrawingPanel.DRAWING_PANEL_WIDTH,
					DrawingPanel.DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);//refer to definition
		}//创建一个 不带透明色(TYPE_INT_RGB) 的对象
		g2d = mImageArray[0].createGraphics();//definition
		g2d.fillRect(0, 0, DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT);//1200*500
		g2d.setStroke(new BasicStroke(DrawingPanel.mStroke));//???????????**********************************
		g2d.setColor(new Color(DrawingPanel.R, DrawingPanel.G, DrawingPanel.B));//BasicStroke(float w)：指定线条宽w。
				
		//设置绘图区
		mPanel_Draw = new JPanel();
		mPanel_Draw.setLayout(null);
		mPanel_Draw.setBounds(PANEL_DRAWING_X, PANEL_DRAWING_Y,
				PANEL_DRAWING_WIDTH, PANEL_DRAWING_HEIGHT);
		mPanel_Draw.setBackground(new Color(206, 216, 231));
		mPanel_Draw_TextView = new JPanel();
		mTextArea_Coord = new JLabel("坐标：");//尚未实现***********************************************************
		mPanel_Draw_TextView.add(mTextArea_Coord);
		mPanel_Draw_TextView.setBounds(PANEL_DRAWING_LABEL_X, PANEL_DRAWING_LABEL_Y, 
				PANEL_DRAWING_WIDTH, PANEL_DRAWING_HEIGHT/20);
		mPanel_Draw_TextView.setBackground(new Color(240, 240, 240));
		mPanel_Draw_TextView.setBorder(BorderFactory.createLineBorder(new Color(226, 227, 228)));
		mPanel_Draw.add(mPanel_Draw_TextView);
		
		mPanel_Draw_DrawingPanel = new Pen(mImageArray, mTextArea_Coord);	
		mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
		mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
				Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
				Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
		mPanel_Draw.add(mScrollPane);
		
		
		//为所有菜单栏添加监听器
		MenuItemListener mMenuItemListener = new MenuItemListener();//class MenuItemListener implements ActionListener
		mMenuItem_New.addActionListener(mMenuItemListener);
		mMenuItem_Open.addActionListener(mMenuItemListener);
		mMenuItem_Save.addActionListener(mMenuItemListener);
		mMenuItem_Exit.addActionListener(mMenuItemListener);
		
		//为所有的Button添加监听器
		MyActionListener mActionListener = new MyActionListener();
		mButton_Pen.addActionListener(mActionListener);
		mButton_Eraser.addActionListener(mActionListener);
		mButton_String.addActionListener(mActionListener);
		mButton_Redo.addActionListener(mActionListener);
		mButton_Line.addActionListener(mActionListener);
		mButton_Rect.addActionListener(mActionListener);
		mButton_Squ.addActionListener(mActionListener);
		mButton_Circle.addActionListener(mActionListener);
		mButton_Ecli.addActionListener(mActionListener);
		mButton_RoundRect.addActionListener(mActionListener);
		mButton_RoundSqu.addActionListener(mActionListener);
		mButton_RandomShape.addActionListener(mActionListener);
		mButton_Triangle.addActionListener(mActionListener);
		mButton_FillRect.addActionListener(mActionListener);
		mButton_FillSqu.addActionListener(mActionListener);
		mButton_FillCircle.addActionListener(mActionListener);
		mButton_FillEcli.addActionListener(mActionListener);
		mButton_FillRoundRect.addActionListener(mActionListener);
		mButton_FillRoundSqu.addActionListener(mActionListener);
		mButton_setStroke.addActionListener(mActionListener);
		mButton_selectColor.addActionListener(mActionListener);
		mButton_Smaller.addActionListener(mActionListener);
		mButton_Bigger.addActionListener(mActionListener);
		
		//光标设置,即鼠标进入画布后变成铅笔图标
		mToolkit = Toolkit.getDefaultToolkit();
		ImageIcon Icon_Cursor_Pen = new ImageIcon(MyUI.class.getResource("/image/Cursor_Pen.png"));
		Cursor_Pen = mToolkit.createCustomCursor(Icon_Cursor_Pen.getImage(),
				new Point(0, Icon_Cursor_Pen.getIconHeight() + 1), "pen");
		//cursor: the image to display when the cursor is activated

		//文件操作初始化
		mFileChooser = new JFileChooser();//Constructs a JFileChooser pointing to the user's default directory. 
		OpenFile = new File("");          //which on windows is "My Document"
		SaveFile = new File("");
		
		mPanel_Draw_DrawingPanel.setCursor(Cursor_Pen);
		jf.add(mMenuBar);
		jf.add(mPanel_ToolBar);
		jf.add(mPanel_Draw);
		jf.setVisible(true);
	}
	
	//菜单栏的监听器
	class MenuItemListener implements ActionListener{
		public MenuItemListener(){
			super();
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			String mActionCommand;
			mActionCommand = e.getActionCommand();
			if(mActionCommand.equals("打开")){
				int status;
				status = JOptionPane.showConfirmDialog(null, 
						"是否要保存当前文件", "是否要保存当前文件", JOptionPane.YES_NO_OPTION);
				if(status == JOptionPane.OK_OPTION){
					saveImage();
				}
				JOptionPane.showMessageDialog(null, "请选择打开的文件", "请选择打开的文件", JOptionPane.INFORMATION_MESSAGE);
				openImage();
			}
			else if(mActionCommand.equals("保存")){
				saveImage();
			}
			else if(mActionCommand.equals("新建")){
				int status;
				status = JOptionPane.showConfirmDialog(null, 
						"是否要保存当前文件", "是否要保存当前文件", JOptionPane.YES_NO_OPTION);
				if(status == JOptionPane.OK_OPTION){
					saveImage();
				}
				newPro();
			}
			else{
				int status;
				status = JOptionPane.showConfirmDialog(null, 
						"是否要保存当前文件", "是否要保存当前文件", JOptionPane.YES_NO_OPTION);
				if(status == JOptionPane.OK_OPTION){
					saveImage();
				}
				System.exit(0);
			}
		}
	}
	//所有的按钮的监听器
	class MyActionListener implements ActionListener{
		public MyActionListener(){
			super();
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			String mActionCommand;
			mActionCommand = e.getActionCommand();
			if(mActionCommand.equals("Pen")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Pen(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor_Pen);
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Eraser")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Eraser(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);//CROSSHAIR十字光标
			}
			else if(mActionCommand.equals("String")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new MyString(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
				jf.setVisible(true);
	
				Object[] mValue = {"宋体 " , "Times New Roman" ,
			            "Serif" , "Monospaced" ,
			            "SonsSerif" , "Garamond"};
				DrawingPanel.FontType = (String)JOptionPane.showInputDialog(null, "请选择一种字体", "字体选择",
						JOptionPane.INFORMATION_MESSAGE, null,
						mValue, "宋体");
				 MyString.mString = JOptionPane.showInputDialog("请输入你要输入的文字");//直接获取用户的输入
			}
			/**********************************撤销机制**********************************************/
			else if(mActionCommand.equals("Redo")){
				int i;
				for(i = 0; i < ImageArraySize - 1; i++){
					mImageArray[i] = mImageArray[i+1];
				}
				mPanel_Draw_DrawingPanel.repaint();
			}
			else if(mActionCommand.equals("Line")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Line(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Rect")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Rectangle(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Squ")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Squ(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Circle")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Circle(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Ecli")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Ecli(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Triangle")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Triangle(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("RandomShape")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new RandomShape(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("RoundRect")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new RoundRect(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("RoundSqu")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new RoundSqu(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("FillRect")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new FillRect(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("FillSqu")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new FillSqu(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("FillCircle")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new FillCircle(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("FillEcli")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new FillEcli(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("FillRoundSqu")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new FillRoundSqu(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw_DrawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("FillRoundRect")){
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new FillRoundRect(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				jf.setVisible(true);
			}
			/***************************此处可进行异常处理(比如输入字母等)***********************************************/
			else if(mActionCommand.equals("setStroke")){
				String input;
				input=JOptionPane.showInputDialog(null, "请输入线条粗细大小 ( 大于零 )：",
				          "5");
				if((Float.parseFloat(input))<0) {
					input="1";
				}
				DrawingPanel.mStroke=Float.parseFloat(input);
			}
	/******************************refer to API********************************/		
			else if(mActionCommand.equals("selectColor")){
				Color tempColor;
				tempColor = JColorChooser.showDialog(null, "颜色选择",
						new Color(255, 255, 255));
				DrawingPanel.R = tempColor.getRed();
				DrawingPanel.G = tempColor.getGreen();
				DrawingPanel.B = tempColor.getBlue();
			}
			else if(mActionCommand.equals("Smaller")){
				DrawingPanel.DRAWING_PANEL_HEIGHT = DrawingPanel.DRAWING_PANEL_HEIGHT*4/5;
				DrawingPanel.DRAWING_PANEL_WIDTH = DrawingPanel.DRAWING_PANEL_WIDTH*4/5;
				int i;
				for(i = 0; i < ImageArraySize; i++){
					BufferedImage tempImage = new BufferedImage(DrawingPanel.DRAWING_PANEL_WIDTH,
							DrawingPanel.DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
					Graphics2D  g2d = tempImage.createGraphics();
					g2d.fillRect(0, 0, DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT);
					g2d.drawImage(mImageArray[i].getScaledInstance(DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT,
							java.awt.Image.SCALE_SMOOTH     ), 0, 0,  null);
					mImageArray[i] = tempImage;
				}
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Squ(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw.repaint();
				jf.setVisible(true);
			}
			else if(mActionCommand.equals("Bigger")){
				DrawingPanel.DRAWING_PANEL_HEIGHT = DrawingPanel.DRAWING_PANEL_HEIGHT*5/4;
				DrawingPanel.DRAWING_PANEL_WIDTH = DrawingPanel.DRAWING_PANEL_WIDTH*5/4;
				int i;
				for(i = 0; i < ImageArraySize; i++){
					BufferedImage tempImage = new BufferedImage(DrawingPanel.DRAWING_PANEL_WIDTH,
							DrawingPanel.DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
					Graphics2D  g2d = tempImage.createGraphics();
					g2d.fillRect(0, 0, DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT);
					g2d.drawImage(mImageArray[i].getScaledInstance(DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT,
							java.awt.Image.SCALE_SMOOTH     ), 0, 0,  null);
					mImageArray[i] = tempImage;
				}
				mPanel_Draw.remove(mScrollPane);
				mPanel_Draw_DrawingPanel = new Squ(mImageArray, mTextArea_Coord);
				mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
				mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
						Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
						Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
				
				mPanel_Draw.add(mScrollPane);
				mPanel_Draw.repaint();
				jf.setVisible(true);
				
			}
			
		}
	}
	
	//保存文件
	public void saveImage(){
		int status;
		status = mFileChooser.showSaveDialog(jf);//public int showSaveDialog...
		if(status == JFileChooser.APPROVE_OPTION){
			SaveFile = mFileChooser.getSelectedFile();
			String imageType;
			imageType = "";
			if(SaveFile.getName().toUpperCase().endsWith(".JPG")
					|| SaveFile.getName().toUpperCase().endsWith(".JPEG")
					|| SaveFile.getName().toUpperCase().endsWith(".BMP")
					|| SaveFile.getName().toUpperCase().endsWith(".GIF")
					|| SaveFile.getName().toUpperCase().endsWith(".PNG")){
				imageType = SaveFile.getName().substring(SaveFile.getName().indexOf('.') + 1);
				try{
					ImageIO.write(mImageArray[0], imageType,
						SaveFile);
				}catch(IOException e){
					JOptionPane.showMessageDialog(null,
							"文件保存失败", "文件保存失败！", JOptionPane.ERROR_MESSAGE);
				}
			}
			//未知格式则默认jpeg保存
			else{
				SaveFile = new File(SaveFile.getAbsolutePath() + ".jpeg");
				try{
					ImageIO.write(mImageArray[0], "jpeg",
						SaveFile);
				}catch(IOException e){
					JOptionPane.showMessageDialog(null,
							"文件保存失败", "文件保存失败！", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	//打开文件
	public void openImage(){
		int status;
		status = mFileChooser.showOpenDialog(jf);
		if(status == JFileChooser.APPROVE_OPTION){
			OpenFile = mFileChooser.getSelectedFile();
			if(OpenFile.getName().toUpperCase().endsWith(".JPG")
					|| OpenFile.getName().toUpperCase().endsWith(".JPEG")
					|| OpenFile.getName().toUpperCase().endsWith(".BMP")
					|| OpenFile.getName().toUpperCase().endsWith(".GIF")
					|| OpenFile.getName().toUpperCase().endsWith(".PNG")){
				BufferedImage Image_Open;
				try{
					Image_Open = ImageIO.read(OpenFile);
					int Image_Open_Width = Image_Open.getWidth(null);
					int Image_Open_Height = Image_Open.getHeight(null);
					DrawingPanel.DRAWING_PANEL_WIDTH = Image_Open_Width;
					DrawingPanel.DRAWING_PANEL_HEIGHT = Image_Open_Height;//所以画布大小按照打开图片的实际大小确定
					int i;
					for(i = 0; i < ImageArraySize; i++){
						BufferedImage tempImage = new BufferedImage(DrawingPanel.DRAWING_PANEL_WIDTH,
								DrawingPanel.DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
						Graphics2D  g2d = tempImage.createGraphics();
						g2d.fillRect(0, 0, DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT);
						mImageArray[i] = tempImage;
					}
					Graphics2D  g2d = mImageArray[0].createGraphics();
					g2d.drawImage(Image_Open, 0, 0, null);
					mPanel_Draw.remove(mScrollPane);
		/*********************************************??????????????????????????????***********************/
					mPanel_Draw_DrawingPanel = new Squ(mImageArray, mTextArea_Coord);
					mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
					mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
							Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
							Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
					
					mPanel_Draw.add(mScrollPane);
					mPanel_Draw.repaint();
					jf.setVisible(true);
				}catch(IOException e){
					JOptionPane.showMessageDialog(null,
						"文件打开错误", "文件打开错误！", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(null,
						"文件格式错误", "文件格式错误！", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//新建
	public void newPro(){
		int i;
		for(i = 0; i < MyUI.ImageArraySize; i++){
			DrawingPanel.DRAWING_PANEL_WIDTH = 1200;
			DrawingPanel.DRAWING_PANEL_HEIGHT = 500;
			mImageArray[i] = new BufferedImage(DrawingPanel.DRAWING_PANEL_WIDTH,
					DrawingPanel.DRAWING_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		}
		g2d = mImageArray[0].createGraphics();
		g2d.fillRect(0, 0, DrawingPanel.DRAWING_PANEL_WIDTH, DrawingPanel.DRAWING_PANEL_HEIGHT);
		g2d.setStroke(new BasicStroke(DrawingPanel.mStroke));
		g2d.setColor(new Color(DrawingPanel.R, DrawingPanel.G, DrawingPanel.B));
		mPanel_Draw.remove(mScrollPane);
		mPanel_Draw_DrawingPanel = new Pen(mImageArray, mTextArea_Coord);
		mPanel_Draw_DrawingPanel.setCursor(Cursor_Pen);
		mScrollPane = new JScrollPane(mPanel_Draw_DrawingPanel);
		mScrollPane.setBounds(PANEL_DRAWING_DRAWING_X, PANEL_DRAWING_DRAWING_Y,
				Math.min(SCROLLPANE_MAX_WIDTH, DrawingPanel.DRAWING_PANEL_WIDTH + 20),
				Math.min(SCROLLPANE_MAX_HEIGHT, DrawingPanel.DRAWING_PANEL_HEIGHT + 20));
		
		mPanel_Draw.add(mScrollPane);
		mPanel_Draw.repaint();
		jf.setVisible(true);
	}
}
