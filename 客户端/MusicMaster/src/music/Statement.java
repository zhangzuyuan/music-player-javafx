package music;

public class Statement {
	private double time=0.0;
	private String strMinute;
	private String strSecond;
	//private 
	private String lyric="";
	
	//��ȡʱ��
	public double getTime(){
		return time;
	}
	
	//����ʱ��
	public void setTime(double time){
		this.time=time;
	}
	
	/*
	 * ����ʱ��
	 * time: �����óɵ�ʱ���ַ���, ��ʽΪmm:ss.ms
	 */
	
	public void setTime(String time) {
		String str[] = time.split(":|\\.");
		this.time = Integer.parseInt(str[0])*60+Integer.parseInt(str[1])+Integer.parseInt(str[2])*0.01;
		this.strMinute=str[0];
		this.strSecond=str[1]+"."+str[2];
	}
	
	public void setStrMinute(String strMinute){
		this.strMinute=strMinute;
	}
	
	public void setStrSecond(String strSecond){
		this.strSecond=strSecond;
	}
	
	public String getStrMinute(){
		return strMinute;
	}
	
	public String getStrSecond(){
		return strSecond;
	}
	//��ȡ���
	public String getLyric(){
		return lyric;
	}
	
	//���ø��
	
	public void setLyric(String lyric){
		this.lyric=lyric;
	}
	
	/*
	 * ��ӡ���
	 */
	public void printLyric()
	{
		System.out.println(time+": "+lyric);
	}


}
