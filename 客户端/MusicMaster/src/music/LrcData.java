package music;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcData {
	BufferedReader bufferReader = null;//读取文件实例
	public String title="";//歌曲题目
	public String artist="";//演唱者
	public String album="";//专辑
	public String lrcMaker="";//歌词制作者
	Vector<Statement> statements=new Vector<Statement>();//歌词
	
	/* 
	 * 实例化一个歌词数据. 歌词数据信息由指定的文件提供. 
	 * fileName: 指定的歌词文件. 
	 */
	
	public void setTitle(String title){
		this.title=title;
	}
		
	public String getTitle(){
		return this.title;
	}
	
	public void setArtist(String artist){
		this.artist=artist;
	}
		
	public String getArtist(){
		return this.artist;
	}
	
	public void setAlbum(String album){
		this.album=album;
	}
		
	public String getAlbum(){
		return this.album;
	}
	
	public void setLrcMaker(String lrcMaker){
		this.lrcMaker=lrcMaker;
	}
		
	public String getLrcMaker(){
		return this.lrcMaker;
	}
	
	public LrcData(String fileName) throws IOException{
		FileInputStream file=new FileInputStream(fileName);
		bufferReader =new BufferedReader(new InputStreamReader(file,"GB2312"));
		
		//将文件数据读入内存
		readData();
	}
	
	/*
	 * 读取文件中数据至内存. 
	 */
	public void readData() throws IOException{
		statements.clear();
		String strLine;
		
		//循环读入所有行
		while(null!=(strLine=bufferReader.readLine())){
			//判断该行是否为空
			if("".equals(strLine.trim())){
				continue;
			}
			
			//判断该行数据是否表示歌名
			if(null==title||"".equals(strLine.trim())){
				Pattern pattern=Pattern.compile("\\[ti:(.+?)\\]");
				Matcher matcher=pattern.matcher(strLine);
				if(matcher.find()){
					title=matcher.group(1);
					continue;
				}
			}
			
			//判断该行是否表示演唱者
			
			if(null==artist||"".equals(artist.trim())){
				Pattern pattern = Pattern.compile("\\[ar:(.+?)\\]");
				Matcher matcher = pattern.matcher(strLine);
				if(matcher.find()){
					artist=matcher.group(1);
					continue;
				}
			}
			
			//判断该行数据是否表示专辑
			if(null == album || "".equals(album.trim())){
				Pattern pattern = Pattern.compile("\\[al:(.+?)\\]");
				Matcher matcher = pattern.matcher(strLine);
				if(matcher.find()){
					album=matcher.group(1);
					continue;
				}
			}
			
			//判断该行数据是否表示歌词制作者
			if(null == lrcMaker || "".equals(lrcMaker.trim())){
				Pattern pattern = Pattern.compile("\\[by:(.+?)\\]");
				Matcher matcher = pattern.matcher(strLine);
				if(matcher.find()){
					lrcMaker=matcher.group(1);
					continue;
				}
			}
			
			//读取并分析歌词
			int timeNum=0;
			String str[]=strLine.split("\\]");
			for(int i=0;i<str.length;++i){
				String str2[]=str[i].split("\\[");
				str[i]=str2[str2.length-1];
				if(isTime(str[i])){
					++timeNum;
				}
			}
			for(int i=0;i<timeNum;++i){
				Statement sm=new Statement();
				sm.setTime(str[i]);
				if(timeNum<str.length){
					sm.setLyric(str[str.length-1]);
				}
				statements.add(sm);
			}
			
		}
		
		sortLyric();
	}
	
	/*
	 * 判断给定的字符串是否表示时间. 
	 */
	
	public boolean isTime(String string){
		String str[]=string.split(":|\\.");
		if(3!=str.length){
			return false;
		}
		try{
			for(int i=0;i<str.length;++i){
				Integer.parseInt(str[i]);
			}
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	/*
	 * 将读取的歌词按时间排序. 
	 */
	public void sortLyric(){
		for(int i=0;i<statements.size()-1;++i){
			int index=i;
			double delta=Double.MAX_VALUE;
			boolean moveFlag=false;
			for(int j=i+i;j<statements.size()-1;++j){
				double sub;
				if(0>=(sub=statements.get(i).getTime()-statements.get(j).getTime())){
					continue;
				}
				moveFlag=true;
				if(sub<delta){
					delta=sub;
					index=j+1;
				}
			}
			if(moveFlag){
				statements.add(index,statements.elementAt(i));
				statements.remove(i);
				--i;
			}
		}
	}
	
	/*
	 * 打印整个歌词文件
	 */
	public void printLrcDate()
	{
		System.out.println("歌曲名: "+title);
		System.out.println("演唱者: "+artist);
		System.out.println("专辑名: "+album);
		System.out.println("歌词制作: "+lrcMaker);
		for(int i=0;i<statements.size();++i)
		{
			statements.elementAt(i).printLyric();
		}
	}


}
