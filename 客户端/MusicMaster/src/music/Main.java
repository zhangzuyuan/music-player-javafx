package music;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import com.jfoenix.controls.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
//import play.utils.CssUtils;
//import play.utils.screenshotUtil;
import javafx.util.Duration;
//import play.utils.ImageUtils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;


public class Main extends Application {
	
	//场景图的x y坐标
	double scene_x;
	double scene_y;
	
	double min = 0;
	double millis;
    double max = 0;
    int countList=0;
    int countCycle=0;
    int maxSongList;
    int stage_full=0;
    boolean check_play=false;
    private double oldStageX;
    private double oldStageY;
    private double oldScreenX;
    private double oldScreenY;
    String[] songComment;
    String[] songCommentTime;
    int commentIndex=0;
    int lrcStageIndex=0;
    
    //桌面歌词
    lrcStage lrc_stage;
    
    //定义圆形波
    Circle circle_back =new Circle();
    Ellipse  ellipse1=new Ellipse();
    Ellipse ellipse2 =new Ellipse();
    Circle circle_up =new Circle();
    Pane pane_circle=new Pane();
    
    //歌词切换的间隔时间
    private Duration lrctime=Duration.seconds(0.5);
    
    Pane paneCuboid;
    
    //歌词位置索引
    private double lab0_y=0;
    private double lab1_y=0;
    private double lab2_y=0;
    private double lab3_y=0;
    private double lab4_y=0;
    private double lab5_y=0;
    private double lab6_y=0;
    private double lab7_y=0;
    
    private double lab_z=50;
    
    //创建音频
    HBox hboxmusic=new HBox();
    
	//14.旋转的时间轴对象
    private Timeline timeline;
    
    private Timeline timelineList;
	
	//13.碟片的ImageView对象
    private ImageView panImageView;
    
    //显示歌曲名，演唱者等信息;
    VBox dataLrcVBox=new VBox();
    
    //
    
  //25.存储歌词时间的ArrayList
    private ArrayList<String> lrcList = new ArrayList<>();
    
    private ArrayList<Label> lrcLab = new ArrayList<>();
    
    //26.当前歌词的索引
    private int currentLrcIndex;
    
	//9.当前播放歌曲的索引
    private int currentIndex;
	
	ObservableList<Song> listsong=FXCollections.observableArrayList();
	
	ArrayList<Media> listmedia = new ArrayList<Media>();
	
	public static MediaPlayer player;
	String mark=null;
	
    public  static Scene scene;
	public static int count=0;
	String url;
	
	boolean mouse=false;
	
	Slider s1;
	Slider s2;
	
	
	public static void main(String[] args) {
        launch(args);
    }
	
	public void start(Stage primaryStage) throws Exception{
		
		
		
		File file=new File("E:\\java\\MusicMaster\\src\\song");
		File[] files=file.listFiles();
		int num=0;
		for(File id : files){
		     Song song=new Song(num,id.getPath(),id.getName());
		     //System.out.println(id.getPath());
		     listsong.add(song);
		     num++;
			 String url=this.getClass().getClassLoader().getResource("song/"+id.getName()).toExternalForm();
			 //System.out.println("file:\\" +listsong.get(i).getId());
		     listmedia.add(new Media(url));
		}
		
		//创建评论客户端
		//Server comment = (Server) Naming.lookup("rmi://localhost:12334/RHello");
        
        //创建表格
        TableView<Song> tableview=new TableView<Song>(listsong);
        maxSongList=tableview.getItems().size();
        
        
        TableColumn<Song,String> tc_id=new TableColumn<Song,String>("music list");
        //tc_id.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String,String>,ObservableValue<String>>);
        //显示名字
        tc_id.setCellValueFactory(new Callback<CellDataFeatures<Song, String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Song, String> param) {
				SimpleStringProperty name=new SimpleStringProperty(param.getValue().getName());
				return name;
			}
        	
        }
        );
        
        tableview.getColumns().add(tc_id);
        
        tableview.setRowFactory(tv->{
        	TableRow<Song> row =new TableRow<>();
        	row.setOnMouseClicked(event ->{
        		if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
        			Song rowData=row.getItem();
        			currentIndex=rowData.getNum();
        			 if (player != null) {
        				 player.stop();
        				 player.dispose();
     	            }
        			 player=new MediaPlayer(listmedia.get(currentIndex));
     	             try {
						play();
					    } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        		}
        	});
        	return row;
        });
        
        
        FlowPane panetable=new FlowPane();
        panetable.getChildren().addAll(tableview);
        panetable.setPrefHeight(600);
        panetable.setOpacity(0.5);
        
		panetable.setTranslateX(-300);
        
		// 窗口图标
		HBox hbox_stage=new HBox();
		int width=17;
		Rectangle ret1 = new Rectangle(20,width);
        ret1.setFill(Color.TRANSPARENT);
 
        Rectangle ret2 = new Rectangle(20,width);
        ret2.setFill(Color.TRANSPARENT);
        
        Rectangle ret3 = new Rectangle(20,width);
        ret3.setFill(Color.TRANSPARENT);
		
		Image img_close1=new Image("stage_component/1.png");
        Image img_close2=new Image("stage_component/5.png");
        ImageView bn_close=new ImageView(img_close1);
        bn_close.setPreserveRatio(true);
        bn_close.setPickOnBounds(true);
        bn_close.setFitWidth(width);
        bn_close.setCursor(Cursor.HAND);
        
        Image img_full1=new Image("stage_component/8.png");
        Image img_full2=new Image("stage_component/4.png");
        ImageView bn_full=new ImageView(img_full1);
        bn_full.setPreserveRatio(true);
        bn_full.setPickOnBounds(true);
        bn_full.setFitWidth(width);
        bn_full.setCursor(Cursor.HAND);
        
        Image img_min1=new Image("stage_component/6.png");
        Image img_min2=new Image("stage_component/2.png");
        ImageView bn_min=new ImageView(img_min1);
        bn_min.setPreserveRatio(true);
        bn_min.setPickOnBounds(true);
        bn_min.setFitWidth(width);
        bn_min.setCursor(Cursor.HAND);
		
        hbox_stage.getChildren().addAll(bn_min,ret1,bn_full,ret2,bn_close,ret3);
        
		//创建botton
        HBox hbox=new HBox();
        
        Image img_play1=new Image("component_write/7.png");
        Image img_stop1=new Image("component_write/8.png");
        Image img_play2=new Image("component/7.png");
        Image img_stop2=new Image("component/8.png");
        ImageView bn_play=new ImageView(img_play1);
        bn_play.setFitHeight(70);
        bn_play.setFitWidth(70);
        bn_play.setCursor(Cursor.HAND);
		
        Image img_last1=new Image("component_write/1.png");
        Image img_last2=new Image("component/1.png");
        ImageView bn_last=new ImageView(img_last1);
        bn_last.setFitHeight(50);
        bn_last.setFitWidth(50);
        bn_last.setCursor(Cursor.HAND);
        
        Image img_next1=new Image("component_write/2.png");
        Image img_next2=new Image("component/2.png");
        ImageView bn_next=new ImageView(img_next1);
        bn_next.setFitHeight(50);
        bn_next.setFitWidth(50);
        bn_next.setCursor(Cursor.HAND);
        
        Image img_cycle_single1=new Image("component_write/9.png");
        Image img_cycle_all1=new Image("component_write/10.png");
        Image img_cycle_random1=new Image("component_write/4.png");
        Image img_cycle_single2=new Image("component/9.png");
        Image img_cycle_all2=new Image("component/10.png");
        Image img_cycle_random2=new Image("component/4.png");
        ImageView bn_cycle=new ImageView(img_cycle_single1);
        bn_cycle.setFitHeight(50);
        bn_cycle.setFitWidth(50);
        bn_cycle.setCursor(Cursor.HAND);
        
        Image img_volume1=new Image("component_write/11.png");
        Image img_volume2=new Image("component/11.png");
        ImageView bn_volume=new ImageView(img_volume1);
        bn_volume.setFitHeight(50);
        bn_volume.setFitWidth(50);
        bn_volume.setCursor(Cursor.HAND);
        
		Image img_list1=new Image("component_write/3.png");
        Image img_list2=new Image("component/3.png");
        ImageView bn_list=new ImageView(img_list1);
        bn_list.setFitHeight(50);
        bn_list.setFitWidth(50);
        bn_list.setCursor(Cursor.HAND);
        
        Rectangle rect1 = new Rectangle(60,20);
        rect1.setFill(Color.TRANSPARENT);
 
        Rectangle rect2 = new Rectangle(60,20);
        rect2.setFill(Color.TRANSPARENT);


        //创建进度条
		s1= new Slider(0,1,0.2);//音量范围0-1 初始值0.2
		s1.setPrefWidth(100);
		
		s2= new Slider(0,1,0);//进度范围0-1 初始值0
		s2.setPrefWidth(800);
		
		
		
		//创建按钮栏
		hbox.getChildren().addAll(rect1,bn_list,bn_last,bn_play,bn_next,bn_cycle,bn_volume,s1,s2,rect2);
		hbox.setAlignment(Pos.CENTER);
		HBox.setHgrow(bn_play, Priority.ALWAYS);
        HBox.setHgrow(bn_last, Priority.ALWAYS);
        HBox.setHgrow(bn_next, Priority.ALWAYS);
        HBox.setHgrow(bn_cycle, Priority.ALWAYS);
        HBox.setHgrow(bn_volume, Priority.ALWAYS);
        HBox.setHgrow(s1, Priority.ALWAYS);
        HBox.setHgrow(s2, Priority.ALWAYS);
		//hbox.getChildren().addAll(bn1,bn2,bn3,bn4,bn5,s1,s2);
		
		FlowPane panemusic=new FlowPane();
		panemusic.getChildren().addAll(hbox);
		
		//添加的背景
		Image img= new Image("picture/background1.png");
		ImageView imgView=new ImageView(img);
				
		//添加布局
		StackPane backgroundpane=new StackPane(imgView); 
		backgroundpane.setStyle("-fx-background-color:cornsilk");
		backgroundpane.setOpacity(0.7);
		
		//定义圆形波
		circle_back.setCenterX(150);
		circle_back.setCenterY(150);
		circle_back.setRadius(150);
		circle_back.setFill(Color.valueOf("#ffffff00"));
		circle_back.setStroke(Color.RED);
		circle_back.setStrokeWidth(4);
		
		ellipse1.setCenterX(150);
		ellipse1.setCenterY(150);
		ellipse1.setRadiusX(150);
		ellipse1.setRadiusY(150);
		ellipse1.setFill(Color.valueOf("#ffffff00"));
		ellipse1.setStroke(Color.RED);
		ellipse1.setStrokeWidth(1);
		
		ellipse2.setCenterX(150);
		ellipse2.setCenterY(150);
		ellipse2.setRadiusX(150);
		ellipse2.setRadiusY(150);
		ellipse2.setFill(Color.valueOf("#ffffff00"));
		ellipse2.setStroke(Color.RED);
		ellipse2.setStrokeWidth(1);
		
		circle_up.setCenterX(150);
		circle_up.setCenterY(150);
		circle_up.setRadius(150);
		circle_up.setFill(Color.valueOf("#ffffff00"));
		circle_up.setStroke(Color.RED);
		circle_up.setStrokeWidth(1);
		
		pane_circle.getChildren().addAll(circle_back,ellipse1,ellipse2,circle_up);
		
		//定义碟片
		panImageView= new ImageView("picture/default1.jpg");
		panImageView.setFitHeight(300);
		panImageView.setFitWidth(300);
		Label lab2=new Label("",panImageView);
		lab2.setLayoutX(20);
		lab2.setLayoutY(72);
		
		//定义一个圆
		Circle circle =new Circle();
		circle.setCenterX(150);
		circle.setCenterY(150);
		circle.setRadius(150);//圆的半径
		circle.setStroke(Color.RED);
		
		panImageView.setClip(circle);
		
		//定义一个时间轴动画
		timeline=new Timeline();
		timeline.getKeyFrames().addAll(
			new KeyFrame(new Duration(0),new KeyValue(panImageView.rotateProperty(),0)),
			new KeyFrame(new Duration(120*800),new KeyValue(panImageView.rotateProperty(),360))
		);
		timeline.setCycleCount(Timeline.INDEFINITE);//无限循环
		
		
		
		//创建总布局
		AnchorPane root=new AnchorPane();
		root.getChildren().addAll(backgroundpane,hboxmusic,panemusic,hbox_stage,panetable,dataLrcVBox,pane_circle,lab2);
		AnchorPane.setRightAnchor(hbox_stage,10.0);
		AnchorPane.setTopAnchor(hbox_stage,5.0);
		AnchorPane.setTopAnchor(dataLrcVBox, 100.0);
		AnchorPane.setLeftAnchor(dataLrcVBox, 550.0);
		AnchorPane.setLeftAnchor(hboxmusic, 10.0);
		AnchorPane.setBottomAnchor(hboxmusic, -1.0);
		AnchorPane.setRightAnchor(lab2, 60.0);
		AnchorPane.setTopAnchor(lab2, 300.0);
		AnchorPane.setRightAnchor(pane_circle, 60.0-3);
		AnchorPane.setTopAnchor(pane_circle, 300.0);
		AnchorPane.setLeftAnchor(panemusic, 100.0);
		AnchorPane.setBottomAnchor(panemusic, 10.0);
		AnchorPane.setLeftAnchor(panetable,0.0);
		AnchorPane.setBottomAnchor(panetable, 60.0);
		imgView.fitHeightProperty().bind(root.heightProperty());
		imgView.fitWidthProperty().bind(root.widthProperty());
		
		//设置窗口的拖动效果
		
		
		//设置根节点圆角
		root.setBackground(new Background(new BackgroundFill(Color.valueOf("#696969"),new CornerRadii(15),null)));
		
		//添加场景（scene）并设置布局
		Scene scene =new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		
		//创建并显示舞台（stage）
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("picture/lab1.jpg"));//设置logo
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setWidth(1500);
		primaryStage.setHeight(900);
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Music Player (made by Zhang Zuyuan and Zhang Xiaojing)");
		primaryStage.show();
		
		//创建任务栏动画
		//double sceneWidth = scene.getWidth();
	    double panetableWidth = panetable.getLayoutBounds().getWidth();

	    KeyValue initKeyValue = new KeyValue(panetable.translateXProperty(), -1.0
	            * panetableWidth);
	    KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);

	    KeyValue endKeyValue = new KeyValue(panetable.translateXProperty(), 0);
	    KeyFrame endFrame = new KeyFrame(Duration.seconds(0.1), endKeyValue);

	    timelineList = new Timeline(initFrame, endFrame);

	   // timelineList.setCycleCount(Timeline.INDEFINITE);
	    //timeline.play();
		
		
		
		
	    player=new MediaPlayer(listmedia.get(currentIndex));
		
		//窗口拖动
	    scene.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				scene_x=event.getSceneX()-primaryStage.getX();
				scene_y=event.getSceneY()-primaryStage.getY();
				
			}
	    	
	    });
	    scene.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				primaryStage.setX(event.getSceneX()-scene_x);
				primaryStage.setY(event.getSceneY()-scene_y);
			}
	    	
	    });
		
		s2.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				mouse=true;
			}
			
		});
		
		s2.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				player.seek(Duration.seconds(s2.getValue()));//Duration.seconds(s2.getValue())
				mouse=false;
			}
			
		});
		
		
		//窗口按钮的事件监听
		bn_close.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				primaryStage.close();
				lrc_stage.close();
			}
			
		});
		bn_close.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_close.setImage(img_close2);
			}
			
		});
		bn_close.setOnMouseExited(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_close.setImage(img_close1);
			}
			
		});
		
		bn_full.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				stage_full++;
				if(stage_full%2==1)
				    primaryStage.setMaximized(true);
				else
					primaryStage.setMaximized(false);
			}
			
		});
		bn_full.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_full.setImage(img_full2);
			}
			
		});
		bn_full.setOnMouseExited(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_full.setImage(img_full1);
			}
			
		});
		
		
		bn_min.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
					primaryStage.setIconified(true);
			}
			
		});
		bn_min.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_min.setImage(img_min2);
			}
			
		});
		bn_min.setOnMouseExited(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_min.setImage(img_min1);
			}
			
		});
		
		
		
		bn_list.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_list.setImage(img_list2);
			}});
		
		bn_list.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_list.setImage(img_list1);
				countList++;
				if(countList%2==1) {
					timelineList.play();
				}
				else{
					root.getChildren().remove(panetable);
					panetable.setOpacity(0.7);
					panetable.setTranslateX(-300);
					root.getChildren().add(panetable);
				}
			}
			
		});
		
		bn_play.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(count%2==0){
					bn_play.setImage(img_play2);
				}
				else{
					bn_play.setImage(img_stop2);
				}
			}});
		
		bn_play.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				count++;
				if(count%2==1) {
					try {
						bn_play.setImage(img_stop1);
						if(check_play){
						  player.play();
						  timeline.play();
						}
						else
					      play();
						check_play=false;
				    } catch (IOException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				else {player.pause();bn_play.setImage(img_play1);timeline.pause();check_play=true;}
			}
			
		});
		
		bn_last.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_last.setImage(img_last2);
			}});
		
		bn_last.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (player != null) {
					player.stop();
					player.dispose();
	            }
	            currentIndex--;
	            if (currentIndex < 0) {
                    currentIndex = tableview.getItems().size()-1;
                }
	            player=new MediaPlayer(listmedia.get(currentIndex));
	             try {
	            	bn_play.setImage(img_stop1);
	            	bn_last.setImage(img_last1);
					play();
				  } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				  } catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
		
		bn_next.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_next.setImage(img_next2);
			}});
		
		
		bn_next.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (player != null) {
					player.stop();
					player.dispose();
	            }
	            currentIndex++;
	            if (currentIndex >= tableview.getItems().size()) {
                    currentIndex = 0;//定位到第一首歌
                }
	            player=new MediaPlayer(listmedia.get(currentIndex));
	            try {
	            	bn_play.setImage(img_stop1);
	            	bn_next.setImage(img_next1);
					play();
				  } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}});
		
		
		bn_cycle.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(countCycle==0)
					bn_cycle.setImage(img_cycle_single2);
				if(countCycle==1)
					bn_cycle.setImage(img_cycle_all2);
				if(countCycle==2)
					bn_cycle.setImage(img_cycle_random2);
			}});
		
		
		bn_cycle.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				countCycle++;
				if(countCycle==3) countCycle=0;
				if(countCycle==0)
					bn_cycle.setImage(img_cycle_single1);
				if(countCycle==1)
					bn_cycle.setImage(img_cycle_all1);
				if(countCycle==2)
					bn_cycle.setImage(img_cycle_random1);
			}});
		
		
		bn_volume.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_volume.setImage(img_volume2);
			}});
		
		
		bn_volume.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				bn_volume.setImage(img_volume1);
				lrcStageIndex++;
				if(lrcStageIndex%2==1){
					lrc_stage=new lrcStage();
					lrc_stage.show();
				}
				else{
					lrc_stage.close();
				}
				
			}});
			
	}
	
	public void play() throws IOException, NotBoundException{
		//创建评论功能
		songComment=null;
		songCommentTime=null;
		commentIndex=0;
		Server comment = (Server) Naming.lookup("rmi://localhost:12334/RHello");
		
		songComment=comment.getComment(listsong.get(currentIndex).getSongName());
		songCommentTime=comment.getTime(listsong.get(currentIndex).getSongName());
		
		//评论的标签
		Label labTime=new Label();
		Label labComment=new Label();
		
		//创建输入框
		HBox hboxComment =new HBox();
		Button bn_comment=new Button("发送");
		Label lab = new Label("评论："); // 创建一个标签
		TextField field = new TextField(); // 创建一个单行输入框
		field.setPrefSize(200, 30); // 设置单行输入框的推荐宽高
		field.setEditable(true); // 设置单行输入框能否编辑
		field.setPromptText("评论"); // 设置单行输入框的提示语
		field.setAlignment(Pos.CENTER_LEFT); // 设置单行输入框的对齐方式
		field.setPrefColumnCount(11); // 设置单行输入框的推荐列数
		hboxComment.getChildren().addAll(lab,field,bn_comment); // 给水平箱子添加一个单行输入框
		
		bn_comment.setOnMouseClicked(event->{
			
			try {
				comment.setComment(field.getText(), listsong.get(currentIndex).getSongName());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				songComment=comment.getComment(listsong.get(currentIndex).getSongName());
				songCommentTime=comment.getTime(listsong.get(currentIndex).getSongName());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			field.clear();
		});
		

		//
		player.play();
		timeline.play();
		currentLrcIndex=9;
		
		LrcData ld = new LrcData("E:\\java\\MusicMaster\\src\\songlrc\\"+listsong.get(currentIndex).getSongName()+".lrc");				//路径\\输入文件名
		ld.printLrcDate();
		//设置题目
		dataLrcVBox.getChildren().clear();
		  //设置字体
		  //Font font=Font.loadFont("C:\\Windows\\\Fonts\\默陌咿呀体.ttf", 20);
		  //设置外部阴影
		  DropShadow ds = new DropShadow();
		  ds.setOffsetY(3.0f);
		  ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		  
		  //设置内部阴影效果
		  InnerShadow is = new InnerShadow();
		  is.setOffsetX(4.0f);
		  is.setOffsetY(4.0f);
		
		Label label1 = new Label();
		if("".equals(ld.getTitle())){
			label1.setText("歌曲名: "+listsong.get(currentIndex).getSongName());
		}else{
		    label1.setText("歌曲名: "+ld.getTitle());
		}
		 label1.setFont(new Font("KaiTi",30));
		 label1.setEffect(is);
		 //label1.setTextFill(Color.Bl);
		 label1.setEffect(ds);
		 label1.setCache(true);
		 
		Label label2 = new Label("演唱者: "+ld.getArtist());
		 label2.setEffect(is);
		 label2.setFont(new Font("KaiTi",15));
		 //label2.setTextFill(Color.YELLOW);
		 label2.setEffect(ds);
		 label2.setCache(true);
		 
		Label label3 = new Label("专辑名: "+ld.getAlbum());
		 label3.setEffect(is);
		 label3.setFont(new Font("KaiTi",15));
		 //label3.setTextFill(Color.YELLOW);
		 label3.setEffect(ds);
		 label3.setCache(true);
		 
		Label label4 = new Label("歌词制作: "+ld.getLrcMaker());
		 label4.setEffect(is);
		 label4.setFont(new Font("KaiTi",15));
		 //label4.setTextFill(Color.YELLOW);
		 label4.setEffect(ds);
		 label4.setCache(true);
		 lrcList.clear();
		 for(int i=0;i<ld.statements.size();++i){
			 lrcList.add(ld.statements.elementAt(i).getLyric());
		 }
		 Pane pane=lrcView(400,500);
		 
		 
		dataLrcVBox.getChildren().addAll(label1,label2,label3,label4,pane,labTime,labComment,hboxComment);
		
		//获取专辑图片
		if(listmedia.get(currentIndex).getMetadata().get("image")!=null){
			panImageView.setImage((Image)listmedia.get(currentIndex).getMetadata().get("image"));
		}
		else{
			Image temp_image=new Image("picture/default1.jpg");
			panImageView.setImage(temp_image);
		}
		player.setOnPlaying(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				player.volumeProperty().bind(s1.valueProperty());//绑定音量和音量条
				
				s2.setValue(0);
				s2.setMin(0);
				s2.setMax(player.getTotalDuration().toSeconds());
				//mp.seek(Duration.seconds(100.0));//Duration.seconds(s2.getValue())
				
				//监听播放器播放时的事件
	            // currentTimeProperty​() 当前媒体播放时间。
	            //System.out.println(currentPlayBean.getTotalSeconds());
				player.currentTimeProperty().addListener(new ChangeListener<Duration>(){

					@Override
					public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,Duration newValue) {
						// TODO Auto-generated method stub
						
						if((int)newValue.toSeconds()!=(int)oldValue.toSeconds()){
								labTime.setText("游客"+"  "+songCommentTime[commentIndex]);
								labComment.setText(songComment[commentIndex]);
								commentIndex++;
								if(commentIndex==songComment.length) commentIndex=0;
						}
						
						if(mouse==false){
							//mp.seek(Duration.seconds(s2.getValue()));//Duration.seconds(s2.getValue())
							s2.setValue(newValue.toSeconds());
							//System.out.println(newValue.toSeconds());
						}
						double seconds1=ld.statements.elementAt(currentLrcIndex-5).getTime();
						//double seconds2=ld.statements.elementAt(currentLrcIndex-6).getTime(); 
						//System.out.println(Math.abs(newValue.toSeconds()-seconds1));
						if(Math.abs(newValue.toSeconds()-seconds1)<0.1||(newValue.toSeconds()>seconds1)){
							//System.out.print(Math.abs(newValue.toSeconds()-seconds));
							lrc_play();
							if(lrc_stage!=null)
							  lrc_stage.setLrc(lrcList.get(currentLrcIndex-5));
							currentLrcIndex++;
						}
					}
					
				});
				
				player.setAudioSpectrumListener(new AudioSpectrumListener(){

					@Override
					public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
						    hboxmusic.getChildren().clear();
						    hboxmusic.getChildren().clear();
						    float[] value=new float[115];
						    for(int i=0;i<=110;++i) {
								 value[i]=Math.abs(magnitudes[i]);
								 value[i]=100-(100/(60/value[i]));
								 value[i]*=7;
							}
						    float rad=value[0]/20;
						    for(int i=0;i<=110;++i){
						    	Rectangle rec=new Rectangle();
						    	//rec.setX(i*10);
								//rec.setY(0);
								rec.setWidth(10);
								rec.setHeight((double)value[i]);
								rec.setArcHeight(5);
								rec.setArcWidth(5);
								rec.setOpacity(0.7);
								LinearGradient linearGradient1 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
						                new Stop(0, javafx.scene.paint.Color.rgb(135,206,250)),
						                new Stop(1, Color.rgb(0,0,255))
						        });
								
								circle_up.setRadius(150+rad);
								ellipse1.setRadiusX(150+rad);
								ellipse2.setRadiusY(150+rad);
								AnchorPane.setRightAnchor(pane_circle, 60.0-rad*2-2.5);
								rec.setFill(linearGradient1);
								hboxmusic.getChildren().add(rec);
								hboxmusic.setAlignment(Pos.BOTTOM_LEFT);
						    }
						    //hboxmusic.setPrefHeight(400);
						    //hboxmusic.setPrefWidth(1500);
						    //.setOpacity(0.7);
					}
					
				});
			}
			
		});
		
		player.setOnEndOfMedia(()->{
            //1.停止当前播放器的播放
			if (player != null) {
				player.stop();
				player.dispose();
            }
			if(countCycle==0){
				
			}
			if(countCycle==1){
				currentIndex++;
	            if (currentIndex >= maxSongList) {
                    currentIndex = 0;//定位到第一首歌
                }
			}
			if(countCycle==2){
				Random random=new Random();
				currentIndex=random.nextInt(maxSongList);
			}
			
			player=new MediaPlayer(listmedia.get(currentIndex));
            try {
				play();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
	}
	
	
	//歌词显示的组件
	public Pane lrcView(int w,int h){
		//加载歌词
		
		Label lab0=new Label(lrcList.get(0));
		Label lab1=new Label(lrcList.get(1));
		Label lab2=new Label(lrcList.get(2));
		Label lab3=new Label(lrcList.get(3));
		Label lab4=new Label(lrcList.get(4));
		Label lab5=new Label(lrcList.get(5));
		Label lab6=new Label(lrcList.get(6));
		Label lab7=new Label(lrcList.get(7));
		
		//设置外部阴影
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		
		//设置歌词
		lab0.setFont(new Font("Edwardian Script ITC",20));
		lab1.setFont(new Font("Oblique",20));
		lab2.setFont(new Font("Oblique",20));
		lab3.setFont(new Font("Oblique",20));
		lab4.setFont(new Font("Oblique",20));
		lab5.setFont(new Font("Oblique",20));
		lab6.setFont(new Font("Oblique",20));
		lab7.setFont(new Font("Oblique",20));
		lab0.setEffect(ds);
		lab1.setEffect(ds);
		lab2.setEffect(ds);
		lab3.setEffect(ds);
		lab4.setEffect(ds);
		lab5.setEffect(ds);
		lab6.setEffect(ds);
		lab7.setEffect(ds);
		
		lab0.setMinHeight(50);
		lab1.setMinHeight(50);
		lab2.setMinHeight(50);
		lab3.setMinHeight(50);
		lab4.setMinHeight(50);
		lab5.setMinHeight(50);
		lab6.setMinHeight(50);
		lab7.setMinHeight(50);

		lab1_y=lab0_y+lab0.getMinHeight();
		lab2_y=lab1_y+lab1.getMinHeight();
		lab3_y=lab2_y+lab2.getMinHeight();
		lab4_y=lab3_y+lab3.getMinHeight();
		lab5_y=lab4_y+lab4.getMinHeight();
		lab6_y=lab5_y+lab5.getMinHeight();
		lab7_y=lab6_y+lab6.getMinHeight();
		
		lab0.setTranslateX(0);
		lab1.setTranslateX(0);
		lab2.setTranslateX(0);
		lab3.setTranslateX(0);
		lab4.setTranslateX(0);
		lab5.setTranslateX(0);
		lab6.setTranslateX(0);
		lab7.setTranslateX(0);
		
		lab0.setTranslateY(lab0_y);
		lab1.setTranslateY(lab1_y);
		lab2.setTranslateY(lab2_y);
		lab3.setTranslateY(lab3_y);
		lab4.setTranslateY(lab4_y);
		lab5.setTranslateY(lab5_y);
		lab6.setTranslateY(lab6_y);
		lab7.setTranslateY(lab7_y);
		
		lab0.setTranslateZ(lab_z);
		lab1.setTranslateZ(lab_z);
		lab2.setTranslateZ(0);
		lab3.setTranslateZ(lab_z);
		lab4.setTranslateZ(lab_z);
		lab5.setTranslateZ(lab_z);
		lab6.setTranslateZ(lab_z);
		lab7.setTranslateZ(lab_z);
		
		lrcLab=new ArrayList<Label>();
		
		lrcLab.add(lab0);//索引为零在第一个
		lrcLab.add(lab1);//索引为一在第二个
		lrcLab.add(lab2);//索引为三在第三个
		lrcLab.add(lab3);//索引为四在第四个
		lrcLab.add(lab4);//索引为零在第一个
		lrcLab.add(lab5);//索引为一在第二个
		lrcLab.add(lab6);//索引为三在第三个
		lrcLab.add(lab7);//索引为四在第四个
		
		//放歌词的图层
		AnchorPane ap=new AnchorPane();
		ap.getChildren().addAll(lab0,lab1,lab2,lab3,lab4,lab5,lab6,lab7);
		ap.setStyle("-fx-background-color: transparent");
		//三D视觉子场景图
		SubScene subscene=new SubScene(ap,w,h,true,SceneAntialiasing.BALANCED);
		
		//3D相机
		PerspectiveCamera camera =new PerspectiveCamera();
		subscene.setCamera(camera);
		
		//层叠面板
		StackPane pane=new StackPane();
		pane.setStyle("-fx-background-color: transparent");
		
		pane.getChildren().addAll(subscene);
		
		return pane;
	}
	
	public void lrc_play(){
		Label lab0=lrcLab.get(0);
		
		Label lab1=lrcLab.get(1);
		
		Label lab2=lrcLab.get(2);
		
		Label lab3=lrcLab.get(3);
		
        Label lab4=lrcLab.get(4);
		
		Label lab5=lrcLab.get(5);
		
		Label lab6=lrcLab.get(6);
		
		Label lab7=lrcLab.get(7);
		
		first_to_eighth(lab0);
		
		second_to_first(lab1);
		
		third_to_second(lab2);
		
		forth_to_third(lab3);
		
		fifth_to_forth(lab4);
		
		sixth_to_fifth(lab5);
		
		seventh_to_sixth(lab6);
		
		eighth_to_seventh(lab7);
		
		lrcLab.clear();
		
		lrcLab.add(lab1);
		lrcLab.add(lab2);
		lrcLab.add(lab3);
		lrcLab.add(lab4);
		lrcLab.add(lab5);
		lrcLab.add(lab6);
		lrcLab.add(lab7);
		lrcLab.add(lab0);
	}
	
	public void first_to_eighth(Label lab){
		TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		lab.setText("");
		
		tt.setFromY(lab0_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab7_y);
		tt.setToZ(lab_z);
		
		tt.play();
		lab.setText(lrcList.get(currentLrcIndex));
	}
	
	public void second_to_first(Label lab){
		TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab1_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab0_y);
		tt.setToZ(lab_z);
		
		tt.play();
	}
	
    public void third_to_second(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab2_y);
		tt.setFromZ(0);
		
		tt.setToY(lab1_y);
		tt.setToZ(lab_z);
		lab.setTextFill(Color.BLACK);
		
		tt.play();
	}
    
    public void forth_to_third(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab3_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab2_y);
		tt.setToZ(0);
		lab.setTextFill(Color.BLUE);
		
		tt.play();
    }
	
    public void fifth_to_forth(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab4_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab3_y);
		tt.setToZ(lab_z);
		
		tt.play();
    }
    
    public void sixth_to_fifth(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab5_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab4_y);
		tt.setToZ(lab_z);
		
		tt.play();
    }
    
    public void seventh_to_sixth(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab6_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab5_y);
		tt.setToZ(lab_z);
		
		tt.play();
    }
    
    public void eighth_to_seventh(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab7_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab6_y);
		tt.setToZ(lab_z);
		
		tt.play();
    }
}
