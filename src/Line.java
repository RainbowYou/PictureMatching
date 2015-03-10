import java.awt.Point;

/**
 * 构建一个保存C、D点的类Line,
 * 并指明C、D的方向时横向还是纵向
 * @author cjj
 *
 */
public class Line 
{
	Point a,b;
	int direct;//1表示横线，0表示竖线
	public Line(){}
	public Line(int direct ,Point a,Point b)
	{
		this.direct=direct;
		this.a=a;
		this.b=b;
	}
}
