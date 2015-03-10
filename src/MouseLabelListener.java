import java.awt.Point;
import java.awt.event.*;



/**
 * This class is to add imageLabel Listener
 * @author ���� Software College of BUAA
 */
public class MouseLabelListener extends MouseAdapter
{
	MainFrame mainFrame;
	int num;
	Position labelPosition;
	
	public MouseLabelListener(MainFrame mainFrame,int num,Position labelPosition)
	{
		this.mainFrame=mainFrame;
		this.num=num;
		this.labelPosition=labelPosition;
	}
	
	public void mouseClicked(MouseEvent me)
	{
		
		mainFrame.clickTimes++;
		
		if(mainFrame.clickTimes==1)
		{
			mainFrame.aPosition=labelPosition;
			//tip message shows on window title****************************************************************
			mainFrame.setTitle("you have clicked one time");
		}
			
		
		if(mainFrame.clickTimes==2)
		{
			mainFrame.bPosition=labelPosition;
			//tip message shows on title***********************************************************************	
			mainFrame.setTitle("you have clicked double times");			
			//and it shows that this event can be triggered
			
			Point a=new Point();
			Point b=new Point();

			
			a.x=mainFrame.aPosition.x;
			a.y=mainFrame.aPosition.y;
			b.x=mainFrame.bPosition.x;
			b.y=mainFrame.bPosition.y;
			
			if(mainFrame.aPosition.description.equals(mainFrame.bPosition.description)&&
					!(mainFrame.aPosition.equals(mainFrame.bPosition)))
			{//��ѡ������ͼƬ������Ϣ��ͬ���������������ͬ��ͼƬ
				//tip message shows on window title*********************************************************
                mainFrame.setTitle("they are equal");
                
				if(mainFrame.horizonMatch(a, b))
				{
					//tip message shows on title**********************************************************************
					mainFrame.setTitle("horizonMatch algorithm is right!");
					
					
					//����ͼƬ������ʾ
					mainFrame.aPosition.imageLabel.setVisible(false);
				    mainFrame.bPosition.imageLabel.setVisible(false);
				    
				    
				    mainFrame.playSound("Sounds/Delete.wav");
				    mainFrame.repaint();

				    if(checkAll())
				    	mainFrame.playSound("Sounds/GameWin.wav");
					//�ӵ�ǰ�б��Ƴ�ͼƬ
					mainFrame.positionList.remove(mainFrame.aPosition);
					mainFrame.positionList.remove(mainFrame.bPosition);
					
					
					//����Ӧӳ��������Ϊ��
					mainFrame.Map[a.y][a.x]=0;
					mainFrame.Map[b.y][b.x]=0;
					
				}
				else if(mainFrame.verticalMatch(a, b))
				{
					//tip message shows on title**********************************************************************
					mainFrame.setTitle("verticalMatch algorithm is right!");
					
					
					//����ͼƬ������ʾ
					mainFrame.aPosition.imageLabel.setVisible(false);
				    mainFrame.bPosition.imageLabel.setVisible(false);
				    
				    //AudioPlayer.player.start(mainFrame.as);
				    mainFrame.playSound("Sounds/Delete.wav");
				    mainFrame.repaint();

				    if(checkAll())
				    	mainFrame.playSound("Sounds/GameWin.wav");
				    
					//�ӵ�ǰ�б��Ƴ�ͼƬ
					mainFrame.positionList.remove(mainFrame.aPosition);
					mainFrame.positionList.remove(mainFrame.bPosition);
					
					
					//����Ӧӳ��������Ϊ��
					mainFrame.Map[a.y][a.x]=0;
					mainFrame.Map[b.y][b.x]=0;
					
				}
				else if(mainFrame.oneCorner(a, b)|| mainFrame.oneCorner(b, a))
				{
					//tip message shows on title**********************************************************************
					mainFrame.setTitle("oneCorner algorithm is right!");
					
					
					//����ͼƬ������ʾ
					mainFrame.aPosition.imageLabel.setVisible(false);
				    mainFrame.bPosition.imageLabel.setVisible(false);
				    

				    mainFrame.playSound("Sounds/Delete.wav");
				    mainFrame.repaint();
				    
				    if(checkAll())
				    	mainFrame.playSound("Sounds/GameWin.wav");

					//�ӵ�ǰ�б��Ƴ�ͼƬ
					mainFrame.positionList.remove(mainFrame.aPosition);
					mainFrame.positionList.remove(mainFrame.bPosition);
					
					
					//����Ӧӳ��������Ϊ��
					mainFrame.Map[a.y][a.x]=0;
					mainFrame.Map[b.y][b.x]=0;
					
				}
				else if( mainFrame.twoCorner(a, b))
				{
					//tip message shows on title**********************************************************************
					//mainFrame.setTitle("twoCorner algorithm is right!");
					
					
					//����ͼƬ������ʾ
					mainFrame.aPosition.imageLabel.setVisible(false);
				    mainFrame.bPosition.imageLabel.setVisible(false);
				    

				    mainFrame.playSound("Sounds/Delete.wav");
				    mainFrame.repaint();
				    
				    if(checkAll())
				    	mainFrame.playSound("Sounds/GameWin.wav");

					//�ӵ�ǰ�б��Ƴ�ͼƬ
					mainFrame.positionList.remove(mainFrame.aPosition);
					mainFrame.positionList.remove(mainFrame.bPosition);
					
					
					//����Ӧӳ��������Ϊ��
					mainFrame.Map[a.y][a.x]=0;
					mainFrame.Map[b.y][b.x]=0;
					
				}
				
				else 
				{
					mainFrame.playSound("Sounds/Error.wav");
					if(checkAll())
				    	mainFrame.playSound("Sounds/GameWin.wav");
				}
			}
			
			else 
			{
				mainFrame.playSound("Sounds/Error.wav");
				if(checkAll())
			    	mainFrame.playSound("Sounds/GameWin.wav");
			}			
			mainFrame.clickTimes=0;//ͼƬ���������Ϊ��
		}			
	}
	
	//����Ƿ�����ͼƬ��������
	public boolean checkAll()
	{
		for(int i=0;i<mainFrame.ROWS;i++)
		{
			for(int j=0;j<mainFrame.COLUMNS;j++)
			{
				if(mainFrame.Map[i][j]!=0)
					return false;
			}
		}		
		return true;
	}

}
