import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class BackgroundPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	protected Image image;
	String source;
	
	public BackgroundPanel(String source)
	{
		//String sourceString=new String(setBackgroundImage("Image/background.jpg"));
		//source=setSource("Image/background.jpg");
		this.source=setSource(source);
		icon=new ImageIcon(source);
		image=icon.getImage();
	}
	//Image
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image,0,0,null);
	}
	
	public String setSource(String name)
	{
		source=name;
		return source;
	}

}
