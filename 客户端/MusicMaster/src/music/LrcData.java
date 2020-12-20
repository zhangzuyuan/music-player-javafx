package music;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LrcData {
	BufferedReader bufferReader = null;//��ȡ�ļ�ʵ��
	public String title="";//������Ŀ
	public String artist="";//�ݳ���
	public String album="";//ר��
	public String lrcMaker="";//���������
	Vector<Statement> statements=new Vector<Statement>();//���
	
	/* 
	 * ʵ����һ���������. ���������Ϣ��ָ�����ļ��ṩ. 
	 * fileName: ָ���ĸ���ļ�. 
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
		
		//���ļ����ݶ����ڴ�
		readData();
	}
	
	/*
	 * ��ȡ�ļ����������ڴ�. 
	 */
	public void readData() throws IOException{
		statements.clear();
		String strLine;
		
		//ѭ������������
		while(null!=(strLine=bufferReader.readLine())){
			//�жϸ����Ƿ�Ϊ��
			if("".equals(strLine.trim())){
				continue;
			}
			
			//�жϸ��������Ƿ��ʾ����
			if(null==title||"".equals(strLine.trim())){
				Pattern pattern=Pattern.compile("\\[ti:(.+?)\\]");
				Matcher matcher=pattern.matcher(strLine);
				if(matcher.find()){
					title=matcher.group(1);
					continue;
				}
			}
			
			//�жϸ����Ƿ��ʾ�ݳ���
			
			if(null==artist||"".equals(artist.trim())){
				Pattern pattern = Pattern.compile("\\[ar:(.+?)\\]");
				Matcher matcher = pattern.matcher(strLine);
				if(matcher.find()){
					artist=matcher.group(1);
					continue;
				}
			}
			
			//�жϸ��������Ƿ��ʾר��
			if(null == album || "".equals(album.trim())){
				Pattern pattern = Pattern.compile("\\[al:(.+?)\\]");
				Matcher matcher = pattern.matcher(strLine);
				if(matcher.find()){
					album=matcher.group(1);
					continue;
				}
			}
			
			//�жϸ��������Ƿ��ʾ���������
			if(null == lrcMaker || "".equals(lrcMaker.trim())){
				Pattern pattern = Pattern.compile("\\[by:(.+?)\\]");
				Matcher matcher = pattern.matcher(strLine);
				if(matcher.find()){
					lrcMaker=matcher.group(1);
					continue;
				}
			}
			
			//��ȡ���������
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
	 * �жϸ������ַ����Ƿ��ʾʱ��. 
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
	 * ����ȡ�ĸ�ʰ�ʱ������. 
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
	 * ��ӡ��������ļ�
	 */
	public void printLrcDate()
	{
		System.out.println("������: "+title);
		System.out.println("�ݳ���: "+artist);
		System.out.println("ר����: "+album);
		System.out.println("�������: "+lrcMaker);
		for(int i=0;i<statements.size();++i)
		{
			statements.elementAt(i).printLyric();
		}
	}


}
