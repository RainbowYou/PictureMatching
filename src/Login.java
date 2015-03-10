import java.awt.*;

import javax.swing.*;

//import java.io.*;
//import java.net.*;
import java.awt.event.*;
import java.io.IOException;

public class Login extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	BackgroundPanel picturePanel;
	public Login() 
	{
		this.setSize(572,418);
		this.getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//new组件
		JButton btnStart = new JButton("开始游戏");
		JButton btnAbout = new JButton("关于游戏");
		JButton btnLevel = new JButton("难度选择");
		JButton btnMore = new JButton("更多");
		
		//注册事件监听
		btnStart.addActionListener(this);
		btnAbout.addActionListener(this);
		btnLevel.addActionListener(this);
		btnMore.addActionListener(this);
		
		//布置按钮面板
		JPanel panButton = new JPanel();
		panButton.setLayout(new FlowLayout());
		panButton.add(btnStart);
		panButton.add(btnAbout);
		panButton.add(btnLevel);
		panButton.add(btnMore);
		
		//布置窗体
		picturePanel = new BackgroundPanel("Image/menuFrameBg.png");
		this.add(picturePanel);
		this.add(panButton , BorderLayout.SOUTH);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getActionCommand().equals("开始游戏")) 
		{
			MainFrame mainFrame = null;
			try 
			{
				mainFrame = new MainFrame("Picture Matching");
			}
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mainFrame = new MainFrame("Picture Matching");

			mainFrame.setSize(720,575);
			mainFrame.setLocation(240,35);
			mainFrame.setVisible(true);
			this.setVisible(false);
			
		}
		else if (arg0.getActionCommand().equals("关于游戏")) 
		{
			JOptionPane.showMessageDialog(picturePanel, "本游戏功能基本上是齐全的！\n" +
					"并新增了“暂停”、“重新开始”等功能  ...\n希望您喜欢！\n" +
					"如有任何疑问及改善意见，随时欢迎指出。\n" +
					"最后谢谢您的使用！","关于游戏...", getDefaultCloseOperation());
		}
		else if (arg0.getActionCommand().equals("难度选择")) 
		{
			
			
			
		}
		else if(arg0.getActionCommand().equals("更多")) 
		{
			JOptionPane.showMessageDialog(picturePanel,"抵制不良游戏 , 拒绝盗版游戏\n\n注意自我保护 , 谨防受骗上当\n\n" +
					"适度游戏益脑 , 沉迷游戏伤身\n\n合理安排时间 , 享受健康生活\n","健康游戏忠告...", JOptionPane.INFORMATION_MESSAGE);
		}
	}	
}
