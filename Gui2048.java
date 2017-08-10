//Login:cs8baoc
//name:yuhang lian


/** Gui2048.java */
/** PA8 Release */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.control.Button;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Gui2048 extends Application 
{
  private String outputBoard; // The filename for where to save the Board
  private Board board; // The 2048 Game Board
  
  // Fill colors for each of the Tile values
  private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
  private static final Color COLOR_2 = Color.rgb(238, 228, 218);
  private static final Color COLOR_4 = Color.rgb(237, 224, 200);
  private static final Color COLOR_8 = Color.rgb(242, 177, 121);
  private static final Color COLOR_16 = Color.rgb(245, 149, 99);
  private static final Color COLOR_32 = Color.rgb(246, 124, 95);
  private static final Color COLOR_64 = Color.rgb(246, 94, 59);
  private static final Color COLOR_128 = Color.rgb(237, 207, 114);
  private static final Color COLOR_256 = Color.rgb(237, 204, 97);
  private static final Color COLOR_512 = Color.rgb(237, 200, 80);
  private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
  private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
  private static final Color COLOR_OTHER = Color.BLACK;
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
  
  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); // For tiles >= 8
  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); // For tiles < 8
  
  /** Add your own Instance Variables here */
 
  /*@int size
   *@int score
   *@Random random
   *@GridPane pane
   *@StackPane finalPane
   *@boolean endgame
   */ 
  private int size;
  private int score = 0;
  private Random random = new Random();
  private GridPane pane = new GridPane();
  private StackPane finalPane = new StackPane();
  private boolean endgame = false;
    private String[] args = null;
  
    public Gui2048(String[] args){
        this.args = args;
        
    }
  
  //start the gui 
  //@Stage primary
  //return none
  @Override
  public void start(Stage primaryStage)
  {
 
    
    // Process Arguments and Initialize the Game Board
    processArgs(this.args);
    size = board.GRID_SIZE;

    
    /** Add your Code for the GUI Here */
    
    //this construct the board
    primaryStage.setTitle(outputBoard);
    //this will set the title of the windows
    
    //create the square in the game
    Rectangle[] square = new Rectangle[size*size];
    //text for score
    Text score = new Text("Score = "+ this.score + " ");
    //set font for score
    score.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
    //set text for the gamename
    Text game = new Text("2048");
    //this set font for game name
    game.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 36));
    
    //this for loop will initiate squares in the array
    for(int i = 0; i < size*size; i++)
    {
      square[i] = new Rectangle(100,100,100,100);
      //set the size of the rectangle
      square[i].setFill(COLOR_EMPTY);
      //set the color of the square
    }
    
    
    //this will create a grid pane to store the pane 
    
    
    //this will set up the position of node 
    pane.setAlignment(Pos.CENTER);
    //this will set the default color
    pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
    //this setup the padding of the board
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    //set up the space of vgap
    pane.setVgap(5.5);
    //this set up the Hgap 
    pane.setHgap(5.5);
    Button quit = new Button("Quit");
      Button help = new Button("help");
    
    //this will set the default color
    finalPane.setStyle("-fx-background-color: rgb(187, 173, 160)");
    //this setup the padding of the board
    finalPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    Rectangle square2 = new Rectangle(100,100,150,100);
    square2.setFill(Color.rgb(187, 173, 160));
    pane.add(square2,0,1);
    pane.add(game,0,0);
    pane.add(score,0,1);
      pane.add(quit,0,3);
      pane.add(help,0,2);
     
    //this for loop will add all the square in to the pane
    for(int index = 0 ; index < size ; index++)
    {
      //this indicate the y index
      for(int y = 0 ; y < size  ; y++)
      {
        //this indicate the x index
        for(int x = 1 ; x < size+1 ; x++)
        {
          //adding all the object into the pane
          pane.add(square[index],x,y);
          //this add 1 to the index to prevent out of bond error
          index++;
        }
        
      }
      
    }
    readBoard();
    updateScore();
    finalPane.getChildren().addAll(pane);
    //this creates a new scene to store the pane 
    Scene scene = new Scene(finalPane);
    //this set the scene on the primaryStage
    primaryStage.setScene(scene);
    //this will show the stage
    primaryStage.show();
      
      
      
      help.setOnAction(new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent a){
              VBox help = new VBox();
              Text control1 = new Text("To Move left, press <-");
              Text control2 = new Text("To Move right, press ->");
              Text control3 = new Text("To Move up, press UP");
              Text control4 = new Text("To Move down, press DOWN");
              Text info = new Text("DEVELOPED BY LYH LOOOOOOOL");
              Button returnb = new Button("Return");
              help.setStyle("-fx-background-color: rgb(187, 173, 160)");
              help.setAlignment(Pos.CENTER);
              help.setSpacing(10);
              help.getChildren().addAll(control1,control2,control3,control4,info,returnb);
              Scene helpScene = new Scene(help);
              primaryStage.setScene(helpScene);
              primaryStage.show();
              
              returnb.setOnAction(new EventHandler<ActionEvent>(){
                  @Override
                  public void handle(ActionEvent a){
                      primaryStage.setScene(scene);
                      primaryStage.show();
                  }
              });
              
          }
          
          
      });
      
      quit.setOnAction(new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent a){
              HBox confirmBox = new HBox();
              Button yes = new Button("YES");
              Button no  = new Button("NO");
              confirmBox.getChildren().addAll(yes,no);
              confirmBox.setAlignment(Pos.CENTER);
              confirmBox.setSpacing(10);
              Scene confirm = new Scene(confirmBox,300,50);
              confirmBox.setStyle("-fx-background-color: rgb(187, 173, 160)");
              primaryStage.setScene(confirm);
              yes.setOnAction(new EventHandler<ActionEvent>(){
                  @Override
                  public void handle(ActionEvent a){
                      board.saveBoard(outputBoard);//save board method
                      System.out.println("Save Board to" + " " + outputBoard);
                      System.exit(1);
                  }
              });
              no.setOnAction(new EventHandler<ActionEvent>(){
                  @Override
                  public void handle(ActionEvent a){
                      primaryStage.setScene(scene);
                      primaryStage.show();
                  }
              });
          }
      });

    //this is the method for keyevents 
    scene.setOnKeyPressed( e-> {
	    //move up if key up is pressed
      if(e.getCode() == KeyCode.UP && !endgame)
      {
	//check game over first 
        gameOver();
	//then move
        board.moveAndAdd(Direction.UP);
        //clear board first 
	clearBoard();
        //then read from the array
	readBoard();
        //then update the score
	updateScore();
	//print direction in console 
	System.out.println("MOVING UP");
      }
      

      //move right
      if(e.getCode() == KeyCode.RIGHT&& !endgame)
      {
        //check game over and then clear board to re create the board 
	gameOver();
        board.moveAndAdd(Direction.RIGHT);
        clearBoard();
        readBoard();
        updateScore();
	System.out.println("MOVING RIGHT");//print out direction 
      }
      if(e.getCode() == KeyCode.LEFT&& !endgame) // move left
      {
	//check game over then read from the board
        gameOver();
        board.moveAndAdd(Direction.LEFT);
        clearBoard();
        readBoard();
        updateScore();
	System.out.println("MOVING LEFT");//print out command in console 
      }
      if(e.getCode() == KeyCode.DOWN&& !endgame)//move down 
      {
	//check the condition for game over then recreate the board 
        gameOver();
        board.moveAndAdd(Direction.DOWN);
        clearBoard();
	//read board
        readBoard();
        updateScore();
	System.out.println("MOVING DOWN");//print out direction in console 
      }
      //this will save the game once detected q is pressed 
      if(e.getCode() == KeyCode.Q&& !endgame)
      {
        board.saveBoard(outputBoard);//save board method 
        System.out.println("Save Board to" + " " + outputBoard);
                           System.exit(1);
                           }//print out the save name
        });
        
    }       
        
        
        
        
        
    

    /** Add your own Instance Methods Here */

    //check game over ?
    //if game is over 
    //add game over on top of all the stuff
    //return void 
    //take in no parameter 
    public void gameOver()
    {
	    Rectangle end;
      if(board.isGameOver())//call the method in board class
      {
	      if(score<10000)
	      {
        end = new Rectangle (0,0,600,500);//create new rectangle 
	      }
	      else
	      {
        end = new Rectangle (0,0,800,500);//create new Rectangle
	      }
       end.setFill( Color.rgb(238, 228, 218, 0.73));//set color 
       Text endT = new Text("GAME OVER");//init text for gameover
       this.endgame = true;//change instance variable endgame to true
                           //so wont take in any further move 
       //set fount for gameover
       endT.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 36));
       //final Pane will add the newly created stackpane
       finalPane.getChildren().addAll(end,endT);
      }
    }
    
    //clear board for recreating 
    public void clearBoard()
    {
	    //this for loop will go though the gridpane pane and erase it
      for(int x = 1; x < board.GRID_SIZE+1; x++)
      {
        for(int y = 0; y < board.GRID_SIZE; y++)
        {
		//create rectangle
          Rectangle square = new Rectangle(100,100,100,100);
          Rectangle empty = new Rectangle(100,100,100,100);
          square.setFill(Color.rgb(187, 173, 160));//this will set color 
	  empty.setFill(COLOR_EMPTY);//this will set color
          pane.add(square,x,y);//add rectangle to the pane 
          pane.add(empty,x,y);//add rectangle to pane 
          
        }
      }
      
    }
    
    
    //update score
    //this will update the score and 
    public void updateScore()
    {
	    //read score from the board 
       this.score = board.getScore();
       //create new text to overlay old score
       Text score = new Text("Score = "+ this.score + " ");
       //this will set font of text
       score.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 20));
       //this will set square
       Rectangle square;
       if(this.score<10000) 
       {
       square = new Rectangle(100,100,150,100);
       }
       else
       {
       square = new Rectangle(100,100,200,100);
       }
       //set color for the rectangle named square
       square.setFill(Color.rgb(187, 173, 160));
       //add the text and rectangle to the pane 
       pane.add(square,0,1);
       pane.add(score,0,1);
    }
    
    
    
    
    
    //read board
    //return void 
    //take in no parameter 
    
    public void readBoard()
    {
	    // create the array for board 
      int[][] boardArray = board.getGrid();
      //loop though the board to read all the index 
      for(int x = 1; x < size+1 ; x++)
      {
        for(int y = 0; y < size; y++)
        {
		//create text and set font 
          Text text = new Text(" "+ boardArray[y][x-1]);     
          text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 36));
	  text.setFill(Color.WHITE);
          if(boardArray[y][x-1] == 0)//set color for the square if the number 
		  //is 0
          {
            Rectangle square = new Rectangle(100,100,100,100);
            square.setFill(COLOR_EMPTY);
            pane.add(square,x,y);
          }
          if(boardArray[y][x-1] != 0)//set color if the num is not 0 
          {
            
            if(boardArray[y][x-1] == 2)//set color if the num is 2
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_2);
	      text.setFill(Color.BLACK);
              pane.add(square, x, y);
              pane.add(text,x,y);
            }
            else if(boardArray[y][x-1] == 4)//set color if the num is 4
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_4);
	      text.setFill(Color.BLACK);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 8)//set color if the num is 8
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_8);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 16)//set the color if the num is 16
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_16);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 32)//set color if the num is 32
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_32);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 64)//set color if the num is 64
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_64);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 128)//set color if num is 128
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_128);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 256)//set color if num is 256
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_256);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 512)//set color if num is 512
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_512);
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else if(boardArray[y][x-1] == 1024)//set color if num is 1024
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_1024);
	      text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 28));
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
            else//for other number set color 
            {
              Rectangle square = new Rectangle(100,100,100,100);
              square.setFill(COLOR_OTHER);
	      text.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 28));
              pane.add(square, x, y);
              pane.add(text,x,y);
              
            }
          }
        }
      }
      
      
    }
    
    
    
    
    
    
    
    
    
    
    
    /** DO NOT EDIT BELOW */
    
    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
      String inputBoard = null;   // The filename for where to load the Board
      int boardSize = 0;          // The Size of the Board
      
      // Arguments must come in pairs
      if((args.length % 2) != 0)
      {
        printUsage();
        System.exit(-1);
      }
      
      // Process all the arguments 
      for(int i = 0; i < args.length; i += 2)
      {
        if(args[i].equals("-i"))
        {   // We are processing the argument that specifies
          // the input file to be used to set the board
          inputBoard = args[i + 1];
        }
        else if(args[i].equals("-o"))
        {   // We are processing the argument that specifies
          // the output file to be used to save the board
          outputBoard = args[i + 1];
        }
        else if(args[i].equals("-s"))
        {   // We are processing the argument that specifies
          // the size of the Board
          boardSize = Integer.parseInt(args[i + 1]);
        }
        else
        {   // Incorrect Argument 
          printUsage();
          System.exit(-1);
        }
      }
      
      // Set the default output file if none specified
      if(outputBoard == null)
        outputBoard = "2048.board";
      // Set the default Board size if none specified or less than 2
      if(boardSize < 2)
        boardSize = 4;
      
      // Initialize the Game Board
      try{
        if(inputBoard != null)
          board = new Board(inputBoard, new Random());
        else
          board = new Board(boardSize, new Random());
      }
      catch (Exception e)
      {
        System.out.println(e.getClass().getName() + " was thrown while creating a " +
                           "Board from file " + inputBoard);
        System.out.println("Either your Board(String, Random) " +
                           "Constructor is broken or the file isn't " +
                           "formated correctly");
        System.exit(-1);
      }
    }
    
    // Print the Usage Message 
    private static void printUsage()
    {
      System.out.println("Gui2048");
      System.out.println("Usage:  Gui2048 [-i|o file ...]");
      System.out.println();
      System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
      System.out.println();
      System.out.println("  -i [file]  -> Specifies a 2048 board that should be loaded");
      System.out.println();
      System.out.println("  -o [file]  -> Specifies a file that should be used to save the 2048 board");
      System.out.println("                If none specified then the default \"2048.board\" file will be used");
      System.out.println("  -s [size]  -> Specifies the size of the 2048 board if an input file hasn't been");
      System.out.println("                specified.  If both -s and -i are used, then the size of the board");
      System.out.println("                will be determined by the input file. The default size is 4.");
    }
    
    
    }
