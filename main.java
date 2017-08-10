import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;

public class main extends Application{
    
    private String saveName = null;
    private String defaultName = "2048.save";
    private Gui2048 gameStart = null;
    private String loadFileName = null;
    private Scene mainScene = null;
    private Stage gameStage = new Stage();
	
	@Override
	public void start(Stage c){
        Button start = new Button("Start");
        Button load = new Button("Load");
        Button quit = new Button("Quit");
        HBox mainBox = new HBox();
        mainBox.setSpacing(20);
        mainBox.getChildren().addAll(start,load,quit);
        mainBox.setAlignment(Pos.CENTER);
        mainScene = new Scene(mainBox,300,50);
        
        
        load.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent a){
                FileChooser fileChooser = new FileChooser();
                File file = null;
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SAVE files (*.save)", "*.save");
                fileChooser.getExtensionFilters().add(extFilter);
                
                //Show open file dialog
                file = fileChooser.showOpenDialog(null);
                
                if(file != null){
                    loadFileName = file.getPath();
                    String[] args = new String[2];
                    args[0] = "-i";
                    args[1] = loadFileName;
                    gameStart = new Gui2048(args);
                    gameStart.start(gameStage);
                }
               
             
            }
        });

        
		
        start.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent a){
                TextField name = new TextField();
                Button confirm = new Button("Confirm");
                HBox main = new HBox();
                main.getChildren().addAll(name,confirm);
                main.setAlignment(Pos.CENTER);
                main.setSpacing(10);
                
                Scene pop = new Scene(main,300,50);
                c.setTitle("Enter SAVE's Name(length>3)");
                c.setScene(pop);
                c.show();
                confirm.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent d){
                        saveName = name.getText();
                        if(saveName.length()>3)
                        {
                            System.out.println("reached");
                            String[] args = new String[2];
                            args[0] = "-o";
                            args[1] = ""+saveName+".save";
                            gameStart = new Gui2048(args);
                            gameStart.start(gameStage);
                            c.close();
                        }
                    }
                });
            }
        });
        
        
        quit.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent a){
                Button yes = new Button("YES");
                Button no  = new Button("NO");
                HBox confirm = new HBox();
                Scene confirmS = new Scene(confirm,300,50);
                confirm.getChildren().addAll(yes,no);
                confirm.setAlignment(Pos.CENTER);
                confirm.setSpacing(20);
                c.setTitle("Are You Sure ?");
                c.setScene(confirmS);
                c.show();
                yes.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent a){
                        System.exit(1);
                    }
                });
                no.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent a){
                        c.setTitle("MyGame 2048 Loading Interface");
                        c.setScene(mainScene);
                        c.show();
                    }
                });

            }
        });
        c.setTitle("MyGame 2048 Loading Interface");
        c.setScene(mainScene);
        c.show();
    }
    
   }

