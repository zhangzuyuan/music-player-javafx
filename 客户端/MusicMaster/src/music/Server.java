package music;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	public void setComment(String content,String songName) throws RemoteException;
	public String[] getComment(String songName) throws RemoteException;
	public String[] getTime(String songName) throws RemoteException;
	//public String getAllComment(String songName);

}