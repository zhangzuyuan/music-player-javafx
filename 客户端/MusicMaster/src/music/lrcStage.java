package music;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class lrcStage {
	
	double scene_x;
	double scene_y;
	private Stage stage=new Stage();
	Label lab=new Label("");
	
	public lrcStage(){
	}
	
	public void show(){
		
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
        
        Image img_min1=new Image("stage_component/6.png");
        Image img_min2=new Image("stage_component/2.png");
        ImageView bn_min=new ImageView(img_min1);
        bn_min.setPreserveRatio(true);
        bn_min.setPickOnBounds(true);
        bn_min.setFitWidth(width);
        bn_min.setCursor(Cursor.HAND);
		
        hbox_stage.getChildren().addAll(bn_min,ret1,bn_close,ret3);
        
     
		
		bn_min.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
					stage.setIconified(true);
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
		
		bn_close.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				stage.close();
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
		
		lab.setTextFill(Color.GREEN);
		lab.setFont(new Font("KaiTi",40));
		lab.setMinWidth(530);
		//lab.setStyle("-fx-background-color: transparent");
		//lab.setBackground(new Background(new BackgroundFill(Color.valueOf("#696969"),new CornerRadii(15),null)));
		
		
		HBox pane=new HBox();
		pane.getChildren().addAll(lab,hbox_stage);
		pane.setStyle("-fx-background:transparent;");
		Scene scene=new Scene(pane);
		scene.setFill(null);
		stage.setScene(scene);
		stage.setHeight(50);
		stage.setWidth(600);
		stage.setAlwaysOnTop(true);
		stage.setY(0);
		//stage.setX(stage.getX());
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
		
		scene.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				scene_x=event.getSceneX()-stage.getX();
				scene_y=event.getSceneY()-stage.getY();
				
			}
	    	
	    });
	    scene.setOnMouseDragged(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				stage.setX(event.getSceneX()-scene_x);
				stage.setY(event.getSceneY()-scene_y);
			}
	    	
	    });
		
		
		
	}
	
	public void close(){
		stage.close();
	}
	
	public void setLrc(String lrc){
		lab.setText(lrc);
	}

}
