package music;

public class Statement {
	private double time=0.0;
	private String strMinute;
	private String strSecond;
	//private 
	private String lyric="";
	
	//获取时间
	public double getTime(){
		return time;
	}
	
	//设置时间
	public void setTime(double time){
		this.time=time;
	}
	
	/*
	 * 设置时间
	 * time: 被设置成的时间字符串, 格式为mm:ss.ms
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
	//获取歌词
	public String getLyric(){
		return lyric;
	}
	
	//设置歌词
	
	public void setLyric(String lyric){
		this.lyric=lyric;
	}
	
	/*
	 * 打印歌词
	 */
	public void printLyric()
	{
		System.out.println(time+": "+lyric);
	}


}
