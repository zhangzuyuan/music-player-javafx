package music;

public class Song {
	public int num;
	public String id;
	public String name;
	public String[] songName;
	public Song(int num,String id,String name){
		this.num=num;
		this.id=id;
		this.name=name;
		songName=name.split("\\.");
	}
	
	public int getNum(){
		return num;
	}
	
	public void setNum(int num){
		this.num=num;
	}
	
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
		songName=name.split("\\.");
	}
	
	public String getSongName(){
		return songName[0];
	}

}
