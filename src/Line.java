import java.awt.Point;

/**
 * ����һ������C��D�����Line,
 * ��ָ��C��D�ķ���ʱ����������
 * @author cjj
 *
 */
public class Line 
{
	Point a,b;
	int direct;//1��ʾ���ߣ�0��ʾ����
	public Line(){}
	public Line(int direct ,Point a,Point b)
	{
		this.direct=direct;
		this.a=a;
		this.b=b;
	}
}
