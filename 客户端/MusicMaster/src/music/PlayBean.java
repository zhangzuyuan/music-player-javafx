package music;

import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.media.MediaPlayer;

public class PlayBean {
    private int id;//���
    private String soundName;//���ֱ���
    private String artist;//�ݳ���
    private String album;//ר������
    private String length;//��С
    private String time;//ʱ��

    private WritableImage image;//ͼƬ
    private int totalSeconds;//������
    private String filePath;//�ļ�·��
    private MediaPlayer mediaPlayer;//������


    public PlayBean() {
    }

    public PlayBean(int id, String soundName, String artist, String length, String time, WritableImage image, int totalSeconds, String filePath, MediaPlayer mediaPlayer, String lrcPath) {
        //this.id = id;
        this.soundName = soundName;
        this.artist = artist;
        this.album = album;
        this.length = length;
        this.time = time;
        this.image = image;
        this.totalSeconds = totalSeconds;
        this.filePath = filePath;
        this.mediaPlayer = mediaPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public WritableImage getImage() {
        return image;
    }

    public void setImage(WritableImage image) {
        this.image = image;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public String toString() {
        return "PlayBean{" +
                "id=" + id +
                ", soundName='" + soundName + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", length='" + length + '\'' +
                ", time='" + time + '\'' +
                ", image=" + image +
                ", totalSeconds=" + totalSeconds +
                ", filePath='" + filePath + '\'' +
                ", mediaPlayer=" + mediaPlayer +
                '}';
    }
}

