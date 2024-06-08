package tictactoeProject;
import javafx.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.*;
import java.util.ArrayList;
import java.util.List;


class GameView {
	public GridPane board;
	public int size;
	public Button[][] buttonArray;
	public Button winner;
	public BorderPane borderpane;
	public HBox hbox;
	public Button optionTwoPlayers;
	public Button optionComputer;
	public Button restartButton;
	private Scene scene;
	private Stage stage;
	
	 public  GameView() {
		 
		 	this.size=3;	        
		 	this.buttonArray=new Button[size][size];
			this.board = new GridPane();
			this.winner=new Button();
			this.borderpane=new BorderPane();
			this.hbox = new HBox();
			this.optionTwoPlayers=new Button("PLAYER\n    VS\nPLAYER");
			this.optionComputer=new Button("   PLAYER\n       VS\nCOMPUTER");
			this.restartButton=new Button("RESTART");
			
			for(int i=0;i<this.size;i++) 
			{
				for(int j=0;j<this.size;j++)
				{
					Button box = new Button();
					box.setStyle("-fx-border-width: 3px;-fx-background-color: #E79898; -fx-border-color: #4e1019; -fx-pref-width: 200px; -fx-pref-height: 200px; -fx-font-size: 60px; "
								+ "-fx-text-fill: #4e1019; -fx-font-family:Papyrus;");
					buttonArray[i][j]=box;
					board.add(box, j, i);
				}
			}
		
			
		}


	public Stage getStage() 
	{
		
		optionTwoPlayers.setStyle("-fx-border-width: 7px;-fx-pref-width: 200px; -fx-pref-height: 200px; -fx-font-size: 20px; -fx-text-fill: #131906;-fx-border-color: #5c7820;"
					+ " -fx-font-family: Rockwell Extra Bold;-fx-background-color: #add25b;");
		optionComputer.setStyle("-fx-border-width: 7px;-fx-pref-width: 200px; -fx-pref-height: 200px; -fx-font-size: 20px; -fx-text-fill: #131906;-fx-border-color: #5c7820; "
					+ "-fx-font-family: Rockwell Extra Bold;-fx-background-color: #add25b;");
		hbox.setStyle("-fx-border-width:0 0 0 0 ;-fx-spacing: 10px;-fx-alignment: center;-fx-background-color: #F2E1CD; -fx-pref-width: 500px; -fx-pref-height: 200px;"
					+ " -fx-border-color: #FAFBFF;");
		hbox.getChildren().addAll(optionTwoPlayers,optionComputer);
		restartButton.setStyle("-fx-border-width: 7px;-fx-pref-width: 200px; -fx-pref-height: 200px; -fx-font-size: 20px; -fx-text-fill: #131906;-fx-border-color: #5c7820;"
					+ " -fx-font-family: Rockwell Extra Bold;-fx-background-color: #add25b;");
		borderpane.setStyle("-fx-pref-width: 600px; -fx-pref-height: 800px;-fx-background-color:#F2E1CD;-fx-padding: 0 0 20px 0;");
		borderpane.setCenter(hbox);
		borderpane.setBottom(restartButton);
		BorderPane.setAlignment(restartButton, Pos.CENTER);		
		scene = new Scene(borderpane);
		stage= new Stage();
		stage.setTitle("TIC TAC TOE");
	    stage.setScene(scene);
		return stage;
	}

	
	
}





class GameModel{
public String player;
public boolean end;
private ScaleTransition scaleTransition;
private FadeTransition fadeTransition;
private List<FadeTransition> fadeTransitions;


	public GameModel() 
	{
		this.player="X";
		this.end=false; 
        this.fadeTransitions = new ArrayList<>();
	}
	
	public void moveAnimation(Button button) {
		double originalScale = 1.0;
		double targetScale = 1.3;

		scaleTransition = new ScaleTransition(Duration.seconds(1), button);
		scaleTransition.setFromX(originalScale);
		scaleTransition.setFromY(originalScale);
		scaleTransition.setToX(targetScale);
		scaleTransition.setToY(targetScale);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(Animation.INDEFINITE);
		scaleTransition.play();

	}
	
	public void flash(Button button) {
		colorChange(button,true);
		fadeTransition = new FadeTransition(Duration.seconds(0.15), button);
		fadeTransition.setFromValue(-5.0);
		fadeTransition.setToValue(0.9);
		fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        fadeTransitions.add(fadeTransition);

	}
	
	public void stopFlash(GridPane board) {
        for (FadeTransition fadeTransition : fadeTransitions) {
            fadeTransition.stop();
        }
        fadeTransitions.clear();
       
        colorOfBoard(board);
    }

    public void stopMoveAnimation(Button button)
    {
        if (scaleTransition != null) { scaleTransition.stop();}
    }
       
	    

	
	
	public void colorOfBoard(GridPane board)
	{
		for (Node node : board.getChildren()) 
		{
		    if (node instanceof Button)  { colorChange((Button)(node), false); }
		}   
	}
	
	
	public Button getRandomButton(GridPane board, Button[][] buttonArray) 
	{
		Button computerButton;
		int randomRow = (int)(Math.random()*3);
		int randomColumn = (int)(Math.random()*3);
        while (buttonArray[randomRow][randomColumn].getText() != "") 
        {
        	randomRow = (int)(Math.random()*3);
			randomColumn = (int)(Math.random()*3);
        }
        computerButton = (Button) board.getChildren().get(randomRow*3+randomColumn);
        return computerButton;
	}
	
	
	 public void clickedButton(Button button) 
	 {
		button.setText(player);
		button.setDisable(true);
		button.setOpacity(1);
	 }
	
	public void disable(GridPane board,boolean state) 
	{
		for (Node node : board.getChildren()) 
		{
			if (node instanceof Button) { ((Button) node).setDisable(state); }
		}
	}
	
	public void clear(GridPane board) 
	{
		for (Node node :board.getChildren()) 
		{
		    if (node instanceof Button) { ((Button) node).setText(""); }
		}    
	}
	
	
	public void colorChange(Button button,boolean change) 
	{
		if(change==true) {button.setStyle("-fx-border-width: 3px;-fx-background-color: #ffc0c1; -fx-border-color: #4e1019; -fx-pref-width: 200px; -fx-pref-height: 200px;"
					+ " -fx-font-size: 60px; -fx-text-fill: #4e1019; -fx-font-family: Papyrus;");}
		else if(change==false) {button.setStyle("-fx-border-width: 3px;-fx-background-color: #E79898; -fx-border-color: #4e1019; -fx-pref-width: 200px; -fx-pref-height: 200px;"
					+ " -fx-font-size: 60px; -fx-text-fill: #4e1019; -fx-font-family: Papyrus	;");
		button.setOpacity(1);}
	}
	
	
    public boolean win(Button [][] buttonArray) 
    {
    	
    	//row win
    	for(int i=0;i<buttonArray.length;i++)
    	{
    		if(buttonArray[i][0].getText().equals(player)&&
    		   buttonArray[i][1].getText().equals(player)&&
    		   buttonArray[i][2].getText().equals(player)) 
    		{
    			flash(buttonArray[i][0]);
    			flash(buttonArray[i][1]);
    			flash(buttonArray[i][2]);
    			return true;
    		}
       	}
    	
    	//column win
    	for(int j=0;j<buttonArray[0].length;j++) 
    	{
    		if(buttonArray[0][j].getText().equals(player)&&
    		   buttonArray[1][j].getText().equals(player)&&
    		   buttonArray[2][j].getText().equals(player)) 
    		{
    			flash(buttonArray[0][j]);
    			flash(buttonArray[1][j]);
    			flash(buttonArray[2][j]);
    			return true;
    		}
    		
    	}
    	
    	//diagonal1 win
    	if(buttonArray[0][0].getText().equals(player)&&
    	   buttonArray[1][1].getText().equals(player)&&
    	   buttonArray[2][2].getText().equals(player)) 
    	{
    		flash(buttonArray[0][0]);
    		flash(buttonArray[1][1]);
    		flash(buttonArray[2][2]);
			return true;
		}
    	
    	//diagonal2 win
        if(buttonArray[0][2].getText().equals(player)&&
       	   buttonArray[1][1].getText().equals(player)&&
           buttonArray[2][0].getText().equals(player))
        {
        	flash(buttonArray[0][2]);
        	flash(buttonArray[1][1]);
        	flash(buttonArray[2][0]);
        	return true;
        }
			
		return false;
		
	}

    
	
	public boolean isFull(Button[][] buttonArray)
	{
		boolean full = true;
		// If there's empty boxes return false because board isn't full yet
	    for (int i = 0; i < buttonArray.length; i++) 
	    {
	        for (int j = 0; j < buttonArray[i].length; j++)
	        {  
	        	if (buttonArray[i][j].getText() == "") { full=false;   break; } 
	        }
	    }
      return full;
	}
	
	
	
	
	
	
	
	 
	
}



class GameController  implements EventHandler<ActionEvent>{

private GameModel model;
private GameView view;

	public GameController(GameModel model, GameView view) 
	{
	this.model=model;
	this.view=view;
	view.optionTwoPlayers.setOnAction(event -> handleOptions(event));
	view.optionComputer.setOnAction(event -> handleOptions(event));
	view.restartButton.setOnAction(event -> handleOptions(event));
	}



	
   public void handle(ActionEvent e) 
   {
	Button original = (Button)e.getSource();
	model.clickedButton(original);
	if(model.win(view.buttonArray)) 
	{
		model.disable(view.board,true);
		model.moveAnimation(view.winner);
		view.winner.setText("Player "+model.player+" won!!!!!!!");
	}
	
	else if(model.isFull(view.buttonArray)) 
	{
		model.disable(view.board,true);
		model.moveAnimation(view.winner);
		view.winner.setText("It's a tie!!!!!!!");	
	}
	
	if(model.player.equals("X")) { model.player="O"; }
	else { model.player="X"; }
	
}
   
   public void handleComputer(ActionEvent e) 
   {
		
		Button original = (Button)e.getSource(); 
		model.clickedButton(original);
		if(model.win(view.buttonArray)) 
		{
			model.end=true;
			model.disable(view.board,true);
			model.moveAnimation(view.winner);
			view.winner.setText("Player "+model.player+" won!!!!!!!");
		}
		
		else if(model.isFull(view.buttonArray))
		{
			model.end=true;
			model.disable(view.board,true);
			model.moveAnimation(view.winner);
			view.winner.setText("It's a tie!!!!!!!");
		}
		 
		if(!model.end&&model.player.equals("X")) 
		{
		
			model.player="O";
			
			Button randomButton = model.getRandomButton(view.board, view.buttonArray);
			model.clickedButton(randomButton);
			if(model.win(view.buttonArray)) 
			{
				model.disable(view.board,true);
				model.moveAnimation(view.winner);
				view.winner.setText("Player "+model.player+" won!!!!!!!");
			}
			
			else if(model.isFull(view.buttonArray)) 
			{
				model.disable(view.board,true);
				model.moveAnimation(view.winner);
				view.winner.setText("It's a tie!!!!!!!");
			}

			model.player="X";

        }
	}
   

   public void handleOptions(ActionEvent e)
   {
   	Button original = (Button)e.getSource();
   	if(original==view.optionTwoPlayers) 
   	{
   		for(int i=0;i<view.size;i++) 
   		{	
   			for(int j=0;j<view.size;j++)
				{
					view.buttonArray[i][j].setOnAction(event -> handle(event));
				}
			}
   		view.borderpane.setMargin(view.board, new Insets(10, 0, 10, 0));
   		view.winner.setStyle("-fx-alignment: center;-fx-border-width: 3px;-fx-pref-height:30px;-fx-pref-width:400px;-fx-font-size:20px;-fx-text-fill: #4e1019;"
   					+ "-fx-background-color: #F2E1CD; -fx-font-family: Rockwell Extra Bold;");
   		view.borderpane.setTop(view.winner);
   		BorderPane.setAlignment(view.winner, Pos.CENTER);		
   		view.borderpane.setCenter(view.board);
   		view.optionTwoPlayers.setDisable(true);
   		view.optionTwoPlayers.setDisable(true);
   		view.borderpane.getChildren().remove(view.hbox);
   	}
   	
   	else if(original==view.optionComputer)
   	{
   		for(int i=0;i<view.size;i++) 
   		{
				for(int j=0;j<view.size;j++) 
				{
					view.buttonArray[i][j].setOnAction(event -> handleComputer(event));
				}
			}
   		view.borderpane.setMargin(view.board, new Insets(10, 0, 10, 0));
   		view.winner.setStyle("-fx-alignment: center;-fx-border-width: 3px;-fx-pref-height:30px;-fx-pref-width:400px;-fx-font-size:20px;-fx-text-fill: #4e1019;"
   					+ "-fx-background-color: #F2E1CD; -fx-font-family: Rockwell Extra Bold	;");
   		view.borderpane.setTop(view.winner);
   		BorderPane.setAlignment(view.winner, Pos.CENTER);		
   		view.borderpane.setCenter(view.board);
   		view.optionComputer.setDisable(true);
   		view.optionComputer.setDisable(true);
   		view.borderpane.getChildren().remove(view.hbox);
   	}
   	
   	else if(original==view.restartButton)
   	{
   		if(view.borderpane.getChildren().contains(view.board))
   		{
   			view.borderpane.getChildren().remove(view.winner);
       		view.winner.setText("");
       		model.stopFlash(view.board);
       		model.stopMoveAnimation(view.winner);
   			model.clear(view.board);
   			model.colorOfBoard(view.board);
   			model.disable(view.board,false);
   			view.borderpane.getChildren().remove(view.board);
   			model.player="X";
   		    model.end=false;
   		}
   		view.borderpane.setCenter(view.hbox);
   		view.optionTwoPlayers.setDisable(false);
   		view.optionComputer.setDisable(false);
   	}

   }
   
	

	

}




public class TicTacToe extends Application{
	private GameModel model;
	private GameView view;
	private GameController controller;

	public void start(Stage stage)
	{
		 model = new GameModel();
		 view = new GameView();
		 controller= new GameController(model,view);
		 view.getStage().show();
		 
	}
	
	public static void main(String[] args)
	{
       launch(args);			
	}
	
	
}
	
	
	
    
	
	
