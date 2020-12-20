package music;

import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class test extends Application{
    private Duration lrctime=Duration.seconds(0.5);


    private ArrayList<String> lrcList = new ArrayList<>();
    
    private ArrayList<Label> lrcLab = new ArrayList<>();
	
	private double lab0_x=0;
    private double lab1_x=0;
    private double lab2_x=0;
    private double lab3_x=0;
    
    private double lab0_y=0;
    private double lab1_y=0;
    private double lab2_y=0;
    private double lab3_y=0;
    
    private double lab_z=0;
	
	public void start(Stage primaryStage) throws Exception{
		
		Text label1 = new Text("��������v�꿨��Ů��ʼ");
		
		//Text.setStyle("-fx-font-family: '������Ů����W5(P)';-fx-font-size: 16;");
		label1.setFont(new Font("KaiTi",20));
		
		Font.getFontNames().forEach(item->System.out.println(item));
		
		//System.out.println(Font.getFontNames());
		AnchorPane root=new AnchorPane();
		
		
		root.getChildren().add(label1);
		AnchorPane.setTopAnchor(label1, 100.0);
		//��ӳ�����scene�������ò���
		Scene scene =new Scene(root);
				
		//��������ʾ��̨��stage��
		primaryStage.setScene(scene);
		
		primaryStage.setWidth(1500);
		primaryStage.setHeight(900);
		primaryStage.centerOnScreen();
		primaryStage.setTitle("Music Player (made by Zhang Zuyuan and Zhang Xiaojing)");
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
	
	
	public Pane lrcView(int w,int h){
		//���ظ��
		Label lab0=new Label("1");
		Label lab1=new Label("2");
		Label lab2=new Label("3");
		Label lab3=new Label("4");
		
		lab0.setMinHeight(40);
		lab1.setMinHeight(40);
		lab2.setMinHeight(40);
		lab3.setMinHeight(40);

		lab1_y=lab0_y+lab0.getMinHeight();
		System.out.print(lab0.getMinHeight());
		lab2_y=lab1_y+lab1.getMinHeight();
		lab3_y=lab2_y+lab2.getMinHeight();
		
		lab0.setTranslateX(0);
		lab1.setTranslateX(0);
		lab2.setTranslateX(0);
		lab3.setTranslateX(0);
		
		lab0.setTranslateY(lab0_y);
		lab1.setTranslateY(lab1_y);
		lab2.setTranslateY(lab2_y);
		lab3.setTranslateY(lab3_y);
		
		lab0.setTranslateZ(lab_z);
		lab1.setTranslateZ(0);
		lab2.setTranslateZ(lab_z);
		lab3.setTranslateZ(lab_z);
		
		lrcLab=new ArrayList<Label>();
		
		lrcLab.add(lab0);//����Ϊ���ڵ�һ��
		lrcLab.add(lab1);//����Ϊһ�ڵڶ���
		lrcLab.add(lab2);//����Ϊ���ڵ�����
		lrcLab.add(lab3);//����Ϊ���ڵ��ĸ�
		
		//�Ÿ�ʵ�ͼ��
		AnchorPane ap=new AnchorPane();
		ap.getChildren().addAll(lab0,lab1,lab2,lab3);
		
		//��D�Ӿ��ӳ���ͼ
		SubScene subscene=new SubScene(ap,w,h,true,SceneAntialiasing.BALANCED);
		
		//3D���
		PerspectiveCamera camera =new PerspectiveCamera();
		subscene.setCamera(camera);
		
		//������
		StackPane pane=new StackPane();
		pane.setStyle("-fx-background-color:#FFB6C1");//���ñ���
		
		pane.getChildren().addAll(subscene);
		
		return pane;
	}
	
	public void lrc_play(){
		Label lab0=lrcLab.get(0);
		
		Label lab1=lrcLab.get(1);
		
		Label lab2=lrcLab.get(2);
		
		Label lab3=lrcLab.get(3);
		
		first_to_forth(lab0);
		
		second_to_first(lab1);
		
		third_to_second(lab2);
		
		forth_to_third(lab3);
		
		lrcLab.clear();
		
		lrcLab.add(lab1);
		lrcLab.add(lab2);
		lrcLab.add(lab3);
		lrcLab.add(lab0);
	}
	
	public void first_to_forth(Label lab){
		TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab0_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab3_y);
		tt.setToZ(lab_z);
		
		tt.play();
	}
	
	public void second_to_first(Label lab){
		TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab1_y);
		tt.setFromZ(0);
		
		tt.setToY(lab0_y);
		tt.setToZ(lab_z);
		
		tt.play();
	}
	
    public void third_to_second(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab2_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab1_y);
		tt.setToZ(0);
		
		tt.play();
	}
    
    public void forth_to_third(Label lab){
    	TranslateTransition tt=new TranslateTransition();
		tt.setDuration(lrctime);
		tt.setNode(lab);
		
		tt.setFromY(lab3_y);
		tt.setFromZ(lab_z);
		
		tt.setToY(lab2_y);
		tt.setToZ(lab_z);
		
		tt.play();
    }
	
}

