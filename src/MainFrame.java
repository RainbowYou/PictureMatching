import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Color;

import java.awt.GridLayout;

import java.awt.Point;


import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;



public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	//这里将实际行数和列数都增加了二，实际表格为12X8大小
	final int ROWS=10;
	final int COLUMNS=14;
	//关卡难度衡量值，越大则图片种类越多，意味着游戏越难
	private int checkpoint;
	
	BackgroundPanel picturePanel;
	//JPanel picturePanel;
	
	ImageIcon[][] labelIcon;
	ArrayList<Position> positionList;//用于保存图片动态信息的列表
	Vector<Line> vector;//vector 用于保存求解后的线
	
	Position aPosition;//a,b用来记录被单击的两幅图片
	Position bPosition;
	
	
	//图片单击事件监听器
	MouseLabelListener mouseLabelListener;

    int clickTimes=0;//记录图片单击次数
    
    //图片映射数组，用于保存图片的状态，0表示此处不存在图片，1表示存在图片
    //但需记住二维数组的所处的行列数与点坐标的横纵坐标值刚好相反
    int[][] Map;
    
    JToolBar toolBar;
    JLabel[] toolBarLabel;

	public MainFrame(String title) throws IOException
	{
		super(title);
		
		//设置为边界布局,并重写了getInsets()方法为边缘添加空隙
		this.getContentPane().setLayout(new BorderLayout());

		setResizable(false);//禁止玩家改变窗口大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//成员变量初始化
		
		checkpoint=20;
		labelIcon=new ImageIcon[ROWS-1][COLUMNS-1];
		positionList=new ArrayList<Position>();
		
		vector=new Vector<Line>();
		
		Map=new int[ROWS][COLUMNS];
		
		toolBar=new JToolBar();
		toolBarLabel=new JLabel[5];
		toolBar.setBackground(Color.DARK_GRAY);
		//toolBar.setSize(10,5);
				
		initMap();//初始化映射数组
		
		importImages();//导入图片
        setLabelIcon();//为标签设置图标
        
        addListener();//添加事件监听器

	    picturePanel=new BackgroundPanel("Image/back.png");

		picturePanel.setLayout(new GridLayout(ROWS,COLUMNS));

        
        
		addLabels();
		
		addToolBar();
		
		this.add(picturePanel);	
		this.add(toolBar,BorderLayout.NORTH);
		
		playBackgroundMusic();
	}//end of constructor MainFrame
	
	
	public void addToolBar()
	{
		for(int i=0;i<5;i++)
		{
			ImageIcon icon=new ImageIcon("Image/Button/"+i+".png");
			toolBarLabel[i]=new JLabel();
			toolBarLabel[i].setIcon(icon);
			toolBar.add(toolBarLabel[i]);
		}
	}
	
	private void addListener()
	{
		int k=15;
		
		for(int i=1;i<=ROWS-2;i++)
		{
			for(int j=1;j<=COLUMNS-2;j++)
			{
				mouseLabelListener=new MouseLabelListener(this,k,positionList.get(k));
				//recordList.add(positionList.get(k));
				positionList.get(k).imageLabel.addMouseListener(mouseLabelListener);
				k++;
			}
			k+=2;
		}
	}
	

	
	/**
	 * 初始化地图映射数组
	 * 中间12*8个数组元素用于放置图片设置为1，其余设置为0
	 */
	private void initMap()
	{
		//第一行、最后一行，第一列、最后一列元素均初始化为0，代表不存在元素
		for(int i=0;i<=COLUMNS-1;i++) Map[0][i]=0;
		for(int i=0;i<=COLUMNS-1;i++) Map[ROWS-1][i]=0;
		for(int i=1;i<=ROWS-2;i++) Map[i][0]=0;
		for(int i=1;i<=ROWS-2;i++) Map[i][COLUMNS-1]=0;
		//中间地图数组初始化为1，代表存在元素
		for(int i=1;i<=ROWS-2;i++)
		{
			for(int j=1;j<=COLUMNS-2;j++)
				Map[i][j]=1;
		}
	}
	

	
	
	//import images randomly
	private void importImages()
	{
		int arbitraNum=0;
		int[] record=new int[21];
		for(int i=0;i<=20;i++)
			record[i]=0;
		//add icons to icon array randomly
		//空出两行
		for(int i=1;i<ROWS-3;i++)
		{
			for(int j=1;j<COLUMNS-1;j++)
			{
				arbitraNum=(int)(Math.random()*checkpoint);
				labelIcon[i][j]=new ImageIcon("Image/Picture/"+arbitraNum+".png",
						                      "Image/Picture/"+arbitraNum+".png");//此处增加一段信息用于描述图片
				record[arbitraNum]++;
			}
		}
		
		int k=0;
		int m=ROWS-3,n = 1;
		for(int i=ROWS-3;i<ROWS-1;i++)
		{
			//m=i;
			for(int j=1;j<COLUMNS-1;j++)
			{
				//n=j;
				for(int l=0;l<20;l++)
				{
					boolean judge=(record[k]%2==0)&&(k<20);
					if(judge)
					{
						k++;
					}
					else 
						break;
				}
				
				if(k==20) break;//如果图片均为偶数，则跳出循环
				
				labelIcon[i][j]=new ImageIcon("Image/Picture/"+k+".png",
	                                          "Image/Picture/"+k+".png");				
				if(k<20) k++;
				n=j;
				m=i;
			}
		}
		
		if(m==ROWS-3)
		{
			for(int j=(n+1)%(COLUMNS-2);j<COLUMNS-2;j+=2)
			{
				int rand=(int)(Math.random()*checkpoint);
				labelIcon[ROWS-3][j]=new ImageIcon("Image/Picture/"+rand+".png",
	                                               "Image/Picture/"+rand+".png");//此处增加一段信息用于描述图片
				labelIcon[ROWS-3][j+1]=new ImageIcon("Image/Picture/"+rand+".png",
	                                                 "Image/Picture/"+rand+".png");//此处增加一段信息用于描述图片
			}
			
			for(int j=1;j<COLUMNS-2;j+=2)
			{
				int rand=(int)(Math.random()*checkpoint);
				labelIcon[ROWS-2][j]=new ImageIcon("Image/Picture/"+rand+".png",
	                                               "Image/Picture/"+rand+".png");//此处增加一段信息用于描述图片
				labelIcon[ROWS-2][j+1]=new ImageIcon("Image/Picture/"+rand+".png",
	                                                 "Image/Picture/"+rand+".png");//此处增加一段信息用于描述图片
			}
		}
		
		else if(m==ROWS-2)
		{
			for(int j=(n+1)%(COLUMNS-2);j<COLUMNS-2;j+=2)
			{
				int rand=(int)(Math.random()*checkpoint);
				labelIcon[ROWS-2][j]=new ImageIcon("Image/Picture/"+rand+".png",
	                                               "Image/Picture/"+rand+".png");//此处增加一段信息用于描述图片
				labelIcon[ROWS-2][j+1]=new ImageIcon("Image/Picture/"+rand+".png",
	                                                 "Image/Picture/"+rand+".png");//此处增加一段信息用于描述图片
			}
		}
	}//end of function importImages
	
	
	/**
	 * set icon of label
	 */
	private void setLabelIcon()
	{
		//先加入第一排空白标签
		for(int i=0;i<COLUMNS;i++)
		{
			Position position=new Position();
			position.x=i;
			position.y=0;
			position.imageLabel=new JLabel();
			//position.imageLabel.setIcon(labelIcon[i][j]);
			//position.description=labelIcon[i][j].getDescription();//获取并记录图片信息
			positionList.add(position);
		}
		
		for(int i=1;i<ROWS-1;i++)
		{
			//将每行第一个空白标签加入
			Position positionFirst=new Position();
			positionFirst.x=0;
			positionFirst.y=i;
			positionFirst.imageLabel=new JLabel();
			positionList.add(positionFirst);
			
			//加入中间图案 的标签
			for(int j=1;j<COLUMNS-1;j++)
			{
				//imageLabel[i][j]=new JLabel("new");
				//imageLabel[i][j].setIcon(labelIcon[i][j]);
				//labelList.add(imageLabel[i][j]);
				Position position=new Position();
				position.x=j;
				position.y=i;
				position.imageLabel=new JLabel();
				position.imageLabel.setIcon(labelIcon[i][j]);
				position.description=labelIcon[i][j].getDescription();//获取并记录图片信息
				positionList.add(position);
			}
			
			//将每行最后一个空白标签加入
			Position positionLast=new Position();
			positionLast.x=COLUMNS-1;
			positionLast.y=i;
			positionLast.imageLabel=new JLabel();
			positionList.add(positionLast);
		}
		
		//加入最后一排空白标签
		for(int i=0;i<COLUMNS;i++)
		{
			Position position=new Position();
			position.x=i;
			position.y=ROWS-1;
			position.imageLabel=new JLabel();
			positionList.add(position);
		}
	}//end of function setLabelIcon
	
	/**
	 * add label to picturePanel
	 */ 
	private void addLabels()
	{
		//for(int i=0;i<COLUMNS;i++) picturePanel.add(new JLabel());
		
		int k=0;
		for(int i=0;i<=ROWS-1;i++)
		{
			//picturePanel.add(new JLabel());
			for(int j=0;j<=COLUMNS-1;j++)
			{
				picturePanel.add(positionList.get(k).imageLabel);
				k++;
				
			}
			//picturePanel.add(new JLabel());
		}
		
		//for(int i=0;i<COLUMNS;i++) picturePanel.add(new JLabel());
	}//end of function addLabels
	

	/**
	 * 用于播放声音文件
	 * @param s
	 */
	public void playSound(String s)
	{
		String source=new String(s);
		try 
		{
			FileInputStream fileInputStream=new FileInputStream(source);
			AudioStream as=new AudioStream(fileInputStream);
			AudioPlayer.player.start(as);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	/**
	 * 用于播放游戏的背景音乐
	 */
	public void playBackgroundMusic()
	{
		String source=new String("Background Music/0.mid");
		try 
		{
			FileInputStream fileInputStream=new FileInputStream(source);
			AudioStream as=new AudioStream(fileInputStream);
			AudioPlayer.player.start(as);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}
	
	
	
	
	/***********************************************************************************************************
	 * *************************************图片消除算法*********************************************************
	 * *************************************图片消除算法*********************************************************
	 ***********************************************************************************************************/	
	
	
	/**
	 * return the maximum number
	 * @param y1
	 * @param y2
	 * @return
	 */
	private int max(int y1, int y2) 
	{
		int temp=y1;
		if(y1<=y2)
			temp=y2;
		return temp;
	}//end function max

	/**
	 * return the minimum number
	 * @param y1
	 * @param y2
	 * @return
	 */
	private int min(int y1, int y2) 
	{
		int temp=y1;
		if(y2<=y1)
			temp=y2;
		return temp;
	}//end function min
	

	/**
	 * 下面算法用于判定如何确认两幅图片可否消除
	 * 算法分为三部分：
	 * 1.两图片之间只需一条直线相连；
	 * 2.两图片之间需要两条直线相连；
	 * 3.两图片之间需要三条直线相连
	 * （两图片之间最多只能由三条直线相连）
	 */
	
	
	 // 两个点之间只需要一条直线相连	 
	 
    /**
     * 竖线上的判断
     */
	protected boolean verticalMatch(Point a,Point b)
	{
		//********tip  message shows on window title***********************************************************
		//setTitle("verticalMatch function has been invoked!");
		
		if(a.x==b.x && a.y!=b.y) 
		{
			int verticalMin=min(a.y,b.y)+1;
			int verticalMax=max(a.y,b.y)-1;
			for(int i=verticalMin;i<=verticalMax;i++)
			{
				if(Map[i][a.x]!=0)
				{
					return false;
				}
				//tip message shows on window title*************************************************************
				//setTitle("Find solution!");
			}
			return true;
		}
		return false;
	}//end of protected function verticalMatch
	

	/**
	 * 横线上的判断
	 */
	protected boolean horizonMatch(Point a,Point b)
	{
		//********tip  message shows on window title*************************************************************
		//setTitle("horizonMatch function has been invoked!");
				
		if(a.y==b.y && a.x!=b.x)
		{
			int horizonMin=min(a.x, b.x)+1;
			int horizonMax=max(a.x, b.x)-1;
			for(int i=horizonMin;i<=horizonMax;i++)
			{
				if(Map[a.y][i]!=0)
				{
					return false;
				}
				//tip message shows on window title*************************************************************
				//setTitle("Find solution!");
			}
			return true;
		}
		return false;
	}//end of protected function horizonMatch
	
	
	/**
	 * 两个点之间需要两条直线相连
	 */
	protected boolean oneCorner(Point a,Point b)
	{
		//********tip  message shows on window title***********************************************************
		//setTitle("oneCorner function has been invoked!");
				
		Point c,d;
		boolean isMatch;
		c=new Point(a.x,b.y);
		d=new Point(a.x,b.y);
		
		if(Map[c.y][c.x]==0)
		{//C点上必须没有障碍
			isMatch=horizonMatch(a, c)&&verticalMatch(b,c);
			if(isMatch) 
			{
				return isMatch;
			}				
		}
		
		if(Map[d.y][d.x]==0)
		{//D点上必须没有障碍
			isMatch=verticalMatch(a, d)&&horizonMatch(b, d);
			return isMatch;
		}
		return false;
	}//end function oneCorner
	
	
	/**
	 * 最复杂的情况，两个点之间需要三条直线相连
	 */
	
	
	//扫描两点构成 的矩形内有没有完整的空白线段
	private Vector<Line> scan(Point a,Point b)
	{
		Vector<Line> v=new Vector<Line>();
		
		//从A、C连线向B扫描，扫描竖线
		
		//扫描A点左边的所有线
		for(int y=a.x;y>=0;y--)
		{
			if(Map[a.y][y]==0&&Map[b.y][y]==0&&
			   verticalMatch(new Point(y,a.y), new Point(y,b.y)));
			{//存在完整路线
				v.add(new Line(0,new Point(y,a.y),new Point(y,b.y)));
			}
		}
		
		//扫描A点右边的所有线
		for(int y=a.x;y<COLUMNS;y++)
		{
			if(Map[a.y][y]==0&&Map[b.y][y]==0&&
			   verticalMatch(new Point(y,a.y), new Point(y,b.y)))
			{
				v.add(new Line(0,new Point(y,a.y),new Point(y,b.y)));
			}
		}
		
		
		//从A、D连线向B扫描，扫描横线
		
		//扫描A点上面的所有线
		for(int x=a.y;x>=0;x--)
		{
			if(Map[x][a.x]==0&&Map[x][b.x]==0&&
			   horizonMatch(new Point(a.x,x), new Point(b.x,x)))
			{
				v.add(new Line(1,new Point(a.x,x),new Point(b.x,x)));
			}
		}
		
		//扫描A点下面的所有线
		for(int x=a.y;x<ROWS;x++)
		{
			if(Map[x][a.x]==0&&Map[x][b.x]==0&&
			   horizonMatch(new Point(a.x,x), new Point(b.x,x)))
			{
				v.add(new Line(1,new Point(a.x,x),new Point(b.x,x)));
			}
		}
		
		return v;
	}//end function scan
	
	
	
	//现在，对所有找到的符合线进行判断，看看AC、BD是否同样也可以消除
	
	/**
	 * A、B之间只有三条线可以相连
	 * @param Point a
	 * @param Point b
	 * @return true or false
	 */
	protected boolean twoCorner(Point a,Point b)
	{
		//********tip  message shows on window title**********************************************************
		//setTitle("twoCorner function has been invoked!");
		
		//扫描A、B两点构成的区域之间的所有直线
		vector=scan(a,b);
		
		if(vector.isEmpty())
		{//不存在可能的直线
			return false;
		}
		
		for(int index=0;index<vector.size();index++)
		{
			Line line=(Line)vector.elementAt(index);
			
			if(line.direct==1)
			{//横线上的扫描线段，找到了竖线
				
				if(verticalMatch(a, line.a)&&verticalMatch(b, line.b))
				{   //找到了解
					//tip message shows on window title*********************************************************
				    setTitle("Find solution!(竖线)");
				    vector.clear();
					return true;
				}
			}
			
			else
			{   //竖线上的扫描线段，找到了横线
				if(horizonMatch(a, line.a)&&horizonMatch(b, line.b))
				{   //找到了解
					//tip message shows on window title****************************************************
					setTitle("Find solution!(横线)");
					vector.clear();
					return true;
				}
			}
		}		
		return false;
	}

}
