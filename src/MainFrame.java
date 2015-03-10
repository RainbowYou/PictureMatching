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
	
	//���ｫʵ�������������������˶���ʵ�ʱ��Ϊ12X8��С
	final int ROWS=10;
	final int COLUMNS=14;
	//�ؿ��ѶȺ���ֵ��Խ����ͼƬ����Խ�࣬��ζ����ϷԽ��
	private int checkpoint;
	
	BackgroundPanel picturePanel;
	//JPanel picturePanel;
	
	ImageIcon[][] labelIcon;
	ArrayList<Position> positionList;//���ڱ���ͼƬ��̬��Ϣ���б�
	Vector<Line> vector;//vector ���ڱ����������
	
	Position aPosition;//a,b������¼������������ͼƬ
	Position bPosition;
	
	
	//ͼƬ�����¼�������
	MouseLabelListener mouseLabelListener;

    int clickTimes=0;//��¼ͼƬ��������
    
    //ͼƬӳ�����飬���ڱ���ͼƬ��״̬��0��ʾ�˴�������ͼƬ��1��ʾ����ͼƬ
    //�����ס��ά������������������������ĺ�������ֵ�պ��෴
    int[][] Map;
    
    JToolBar toolBar;
    JLabel[] toolBarLabel;

	public MainFrame(String title) throws IOException
	{
		super(title);
		
		//����Ϊ�߽粼��,����д��getInsets()����Ϊ��Ե��ӿ�϶
		this.getContentPane().setLayout(new BorderLayout());

		setResizable(false);//��ֹ��Ҹı䴰�ڴ�С
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//��Ա������ʼ��
		
		checkpoint=20;
		labelIcon=new ImageIcon[ROWS-1][COLUMNS-1];
		positionList=new ArrayList<Position>();
		
		vector=new Vector<Line>();
		
		Map=new int[ROWS][COLUMNS];
		
		toolBar=new JToolBar();
		toolBarLabel=new JLabel[5];
		toolBar.setBackground(Color.DARK_GRAY);
		//toolBar.setSize(10,5);
				
		initMap();//��ʼ��ӳ������
		
		importImages();//����ͼƬ
        setLabelIcon();//Ϊ��ǩ����ͼ��
        
        addListener();//����¼�������

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
	 * ��ʼ����ͼӳ������
	 * �м�12*8������Ԫ�����ڷ���ͼƬ����Ϊ1����������Ϊ0
	 */
	private void initMap()
	{
		//��һ�С����һ�У���һ�С����һ��Ԫ�ؾ���ʼ��Ϊ0����������Ԫ��
		for(int i=0;i<=COLUMNS-1;i++) Map[0][i]=0;
		for(int i=0;i<=COLUMNS-1;i++) Map[ROWS-1][i]=0;
		for(int i=1;i<=ROWS-2;i++) Map[i][0]=0;
		for(int i=1;i<=ROWS-2;i++) Map[i][COLUMNS-1]=0;
		//�м��ͼ�����ʼ��Ϊ1���������Ԫ��
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
		//�ճ�����
		for(int i=1;i<ROWS-3;i++)
		{
			for(int j=1;j<COLUMNS-1;j++)
			{
				arbitraNum=(int)(Math.random()*checkpoint);
				labelIcon[i][j]=new ImageIcon("Image/Picture/"+arbitraNum+".png",
						                      "Image/Picture/"+arbitraNum+".png");//�˴�����һ����Ϣ��������ͼƬ
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
				
				if(k==20) break;//���ͼƬ��Ϊż����������ѭ��
				
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
	                                               "Image/Picture/"+rand+".png");//�˴�����һ����Ϣ��������ͼƬ
				labelIcon[ROWS-3][j+1]=new ImageIcon("Image/Picture/"+rand+".png",
	                                                 "Image/Picture/"+rand+".png");//�˴�����һ����Ϣ��������ͼƬ
			}
			
			for(int j=1;j<COLUMNS-2;j+=2)
			{
				int rand=(int)(Math.random()*checkpoint);
				labelIcon[ROWS-2][j]=new ImageIcon("Image/Picture/"+rand+".png",
	                                               "Image/Picture/"+rand+".png");//�˴�����һ����Ϣ��������ͼƬ
				labelIcon[ROWS-2][j+1]=new ImageIcon("Image/Picture/"+rand+".png",
	                                                 "Image/Picture/"+rand+".png");//�˴�����һ����Ϣ��������ͼƬ
			}
		}
		
		else if(m==ROWS-2)
		{
			for(int j=(n+1)%(COLUMNS-2);j<COLUMNS-2;j+=2)
			{
				int rand=(int)(Math.random()*checkpoint);
				labelIcon[ROWS-2][j]=new ImageIcon("Image/Picture/"+rand+".png",
	                                               "Image/Picture/"+rand+".png");//�˴�����һ����Ϣ��������ͼƬ
				labelIcon[ROWS-2][j+1]=new ImageIcon("Image/Picture/"+rand+".png",
	                                                 "Image/Picture/"+rand+".png");//�˴�����һ����Ϣ��������ͼƬ
			}
		}
	}//end of function importImages
	
	
	/**
	 * set icon of label
	 */
	private void setLabelIcon()
	{
		//�ȼ����һ�ſհױ�ǩ
		for(int i=0;i<COLUMNS;i++)
		{
			Position position=new Position();
			position.x=i;
			position.y=0;
			position.imageLabel=new JLabel();
			//position.imageLabel.setIcon(labelIcon[i][j]);
			//position.description=labelIcon[i][j].getDescription();//��ȡ����¼ͼƬ��Ϣ
			positionList.add(position);
		}
		
		for(int i=1;i<ROWS-1;i++)
		{
			//��ÿ�е�һ���հױ�ǩ����
			Position positionFirst=new Position();
			positionFirst.x=0;
			positionFirst.y=i;
			positionFirst.imageLabel=new JLabel();
			positionList.add(positionFirst);
			
			//�����м�ͼ�� �ı�ǩ
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
				position.description=labelIcon[i][j].getDescription();//��ȡ����¼ͼƬ��Ϣ
				positionList.add(position);
			}
			
			//��ÿ�����һ���հױ�ǩ����
			Position positionLast=new Position();
			positionLast.x=COLUMNS-1;
			positionLast.y=i;
			positionLast.imageLabel=new JLabel();
			positionList.add(positionLast);
		}
		
		//�������һ�ſհױ�ǩ
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
	 * ���ڲ��������ļ�
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
	 * ���ڲ�����Ϸ�ı�������
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
	 * *************************************ͼƬ�����㷨*********************************************************
	 * *************************************ͼƬ�����㷨*********************************************************
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
	 * �����㷨�����ж����ȷ������ͼƬ�ɷ�����
	 * �㷨��Ϊ�����֣�
	 * 1.��ͼƬ֮��ֻ��һ��ֱ��������
	 * 2.��ͼƬ֮����Ҫ����ֱ��������
	 * 3.��ͼƬ֮����Ҫ����ֱ������
	 * ����ͼƬ֮�����ֻ��������ֱ��������
	 */
	
	
	 // ������֮��ֻ��Ҫһ��ֱ������	 
	 
    /**
     * �����ϵ��ж�
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
	 * �����ϵ��ж�
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
	 * ������֮����Ҫ����ֱ������
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
		{//C���ϱ���û���ϰ�
			isMatch=horizonMatch(a, c)&&verticalMatch(b,c);
			if(isMatch) 
			{
				return isMatch;
			}				
		}
		
		if(Map[d.y][d.x]==0)
		{//D���ϱ���û���ϰ�
			isMatch=verticalMatch(a, d)&&horizonMatch(b, d);
			return isMatch;
		}
		return false;
	}//end function oneCorner
	
	
	/**
	 * ��ӵ������������֮����Ҫ����ֱ������
	 */
	
	
	//ɨ�����㹹�� �ľ�������û�������Ŀհ��߶�
	private Vector<Line> scan(Point a,Point b)
	{
		Vector<Line> v=new Vector<Line>();
		
		//��A��C������Bɨ�裬ɨ������
		
		//ɨ��A����ߵ�������
		for(int y=a.x;y>=0;y--)
		{
			if(Map[a.y][y]==0&&Map[b.y][y]==0&&
			   verticalMatch(new Point(y,a.y), new Point(y,b.y)));
			{//��������·��
				v.add(new Line(0,new Point(y,a.y),new Point(y,b.y)));
			}
		}
		
		//ɨ��A���ұߵ�������
		for(int y=a.x;y<COLUMNS;y++)
		{
			if(Map[a.y][y]==0&&Map[b.y][y]==0&&
			   verticalMatch(new Point(y,a.y), new Point(y,b.y)))
			{
				v.add(new Line(0,new Point(y,a.y),new Point(y,b.y)));
			}
		}
		
		
		//��A��D������Bɨ�裬ɨ�����
		
		//ɨ��A�������������
		for(int x=a.y;x>=0;x--)
		{
			if(Map[x][a.x]==0&&Map[x][b.x]==0&&
			   horizonMatch(new Point(a.x,x), new Point(b.x,x)))
			{
				v.add(new Line(1,new Point(a.x,x),new Point(b.x,x)));
			}
		}
		
		//ɨ��A�������������
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
	
	
	
	//���ڣ��������ҵ��ķ����߽����жϣ�����AC��BD�Ƿ�ͬ��Ҳ��������
	
	/**
	 * A��B֮��ֻ�������߿�������
	 * @param Point a
	 * @param Point b
	 * @return true or false
	 */
	protected boolean twoCorner(Point a,Point b)
	{
		//********tip  message shows on window title**********************************************************
		//setTitle("twoCorner function has been invoked!");
		
		//ɨ��A��B���㹹�ɵ�����֮�������ֱ��
		vector=scan(a,b);
		
		if(vector.isEmpty())
		{//�����ڿ��ܵ�ֱ��
			return false;
		}
		
		for(int index=0;index<vector.size();index++)
		{
			Line line=(Line)vector.elementAt(index);
			
			if(line.direct==1)
			{//�����ϵ�ɨ���߶Σ��ҵ�������
				
				if(verticalMatch(a, line.a)&&verticalMatch(b, line.b))
				{   //�ҵ��˽�
					//tip message shows on window title*********************************************************
				    setTitle("Find solution!(����)");
				    vector.clear();
					return true;
				}
			}
			
			else
			{   //�����ϵ�ɨ���߶Σ��ҵ��˺���
				if(horizonMatch(a, line.a)&&horizonMatch(b, line.b))
				{   //�ҵ��˽�
					//tip message shows on window title****************************************************
					setTitle("Find solution!(����)");
					vector.clear();
					return true;
				}
			}
		}		
		return false;
	}

}
