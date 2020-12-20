package music;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Comment extends UnicastRemoteObject implements Server{
	
	static HashMap<String,ArrayList<Data>> songComment=new HashMap<String,ArrayList<Data>>();
	Data temp;
	static String content;
	static String songName;

	protected Comment() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setComment(String content, String songName) throws RemoteException {
		// TODO Auto-generated method stub
		//获取当前时间
		Calendar cal=Calendar.getInstance();      
		int y=cal.get(Calendar.YEAR);      
		int m=cal.get(Calendar.MONTH);      
		int d=cal.get(Calendar.DATE);      
		int h=cal.get(Calendar.HOUR_OF_DAY);      
		int mi=cal.get(Calendar.MINUTE);      
		int s=cal.get(Calendar.SECOND);      
				//System.out.println("现在时刻是"+y+"年"+m+"月"+d+"日"+h+"时"+mi+"分"+s+"秒");
		
		//ArrayList<Data> a=new ArrayList<>();
		if(songComment.containsKey(songName)){
			temp=new Data();
			temp.setComment(content);
			temp.setTime(""+y+"年"+m+"月"+d+"日"+h+":"+mi+":"+s);
			ArrayList<Data> a=songComment.get(songName);
			a.add(temp);
			//songComment.replace(songName, a);
		}
		else{
			temp=new Data();
			temp.setComment(content);
			temp.setTime(""+y+"年"+m+"月"+d+"日"+h+":"+mi+":"+s);
			ArrayList<Data> x = new ArrayList<>();
			x.add(temp);
			//a.clear();
			//a.add(temp);
			songComment.put(songName, x);
		}
		
	}

	@Override
	public String[] getComment(String songName) throws RemoteException {
		// TODO Auto-generated method stub
		if(!songComment.containsKey(songName)){
			String[] s=new String[0];
			return s;
		}
		ArrayList<Data> a=songComment.get(songName);
		String[] s=new String[a.size()];
		for(int i=0;i<a.size();++i){
			s[i]=a.get(i).getComment();
		}
		return s;
		
	}

	@Override
	public String[] getTime(String songName) throws RemoteException {
		// TODO Auto-generated method stub
		if(!songComment.containsKey(songName)){
			String[] s=new String[0];
			return s;
		}
		ArrayList<Data> a=songComment.get(songName);
		String[] s=new String[a.size()];
		for(int i=0;i<a.size();++i){
			s[i]=a.get(i).getTime();
		}
		return s;
	}

}
