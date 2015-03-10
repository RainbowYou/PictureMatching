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
		//new���
		JButton btnStart = new JButton("��ʼ��Ϸ");
		JButton btnAbout = new JButton("������Ϸ");
		JButton btnLevel = new JButton("�Ѷ�ѡ��");
		JButton btnMore = new JButton("����");
		
		//ע���¼�����
		btnStart.addActionListener(this);
		btnAbout.addActionListener(this);
		btnLevel.addActionListener(this);
		btnMore.addActionListener(this);
		
		//���ð�ť���
		JPanel panButton = new JPanel();
		panButton.setLayout(new FlowLayout());
		panButton.add(btnStart);
		panButton.add(btnAbout);
		panButton.add(btnLevel);
		panButton.add(btnMore);
		
		//���ô���
		picturePanel = new BackgroundPanel("Image/menuFrameBg.png");
		this.add(picturePanel);
		this.add(panButton , BorderLayout.SOUTH);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getActionCommand().equals("��ʼ��Ϸ")) 
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
		else if (arg0.getActionCommand().equals("������Ϸ")) 
		{
			JOptionPane.showMessageDialog(picturePanel, "����Ϸ���ܻ���������ȫ�ģ�\n" +
					"�������ˡ���ͣ���������¿�ʼ���ȹ���  ...\nϣ����ϲ����\n" +
					"�����κ����ʼ������������ʱ��ӭָ����\n" +
					"���лл����ʹ�ã�","������Ϸ...", getDefaultCloseOperation());
		}
		else if (arg0.getActionCommand().equals("�Ѷ�ѡ��")) 
		{
			
			
			
		}
		else if(arg0.getActionCommand().equals("����")) 
		{
			JOptionPane.showMessageDialog(picturePanel,"���Ʋ�����Ϸ , �ܾ�������Ϸ\n\nע�����ұ��� , ������ƭ�ϵ�\n\n" +
					"�ʶ���Ϸ���� , ������Ϸ����\n\n������ʱ�� , ���ܽ�������\n","������Ϸ�Ҹ�...", JOptionPane.INFORMATION_MESSAGE);
		}
	}	
}
