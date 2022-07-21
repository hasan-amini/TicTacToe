import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

/**
 *CLI class for TicTacToe. Run your command here.<p>
 *     you can start the game here just use start command and give him 2 player's name.<p>
 *         you can even play with a unbeatable IA,just Enter 'ai' as a player.it will start playing depends on
 *         its turn<p>
 *             if you Enter two 'ai's as player then 2 AIs Challenge each other.
 *         </p>
 *     </p>
 *</p>
 */
public class TicTacToeCLI  {
    private TicTacToe tictactoe;
    private Boolean isStarted=false;
    private MiniMaxAI AI;
    private boolean isAICalled=false;

    public TicTacToeCLI(){}
    /**
     * commands will run here.
     * @param cmd is command.
     * @return result of function.
     */
    public  String run(String cmd){
        String[] Tokens = cmd.split(" ");



        if(Tokens[0].equals("/start")){
            if(isStarted) return "game is started already.";
            try {
                tictactoe = new TicTacToe(Tokens[1], Tokens[2]);
                if(Objects.equals(Tokens[2], "ai")|| Objects.equals(Tokens[1],"ai")){
                    AI=new MiniMaxAI();
                    isAICalled=true;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                return "input is not valid!";
            }

            if(Objects.equals(Tokens[1], "ai") ) {
                if(Objects.equals(Tokens[2], "ai")){
                    while(Objects.equals(tictactoe.getResult(), "UnKnown")) {
                        tictactoe.put(AI.AIPlay(tictactoe));
                        tictactoe.printMap();
                    }
                }else{
                tictactoe.put(AI.AIPlay(tictactoe));
                tictactoe.printMap();
                }
            }
            isStarted=true;




        }

        else if(Tokens[0].equals("/put")) {
            if (!isStarted) return "first start or load the game!";
            try {
                tictactoe.put(Integer.parseInt(Tokens[1]));
            } catch (Exception e) {
                return "input is not valid!";
            }
            tictactoe.printMap();
            if (!tictactoe.getResult().equals("UnKnown")) {
                System.out.println(tictactoe.getResult());
            }
            if(isAICalled){
                tictactoe.put(AI.AIPlay(tictactoe));
                tictactoe.printMap();
            }
            if (!tictactoe.getResult().equals("UnKnown")) {
                System.out.println(tictactoe.getResult());
            }
        }

        else if(Tokens[0].equals("/undo")) {
            tictactoe.undo();
            if(isAICalled) tictactoe.undo();
        }

        else if(Tokens[0].equals("/save")){
            if(!isStarted) return "first start or load the game!";
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmm_ss");
            Date date = new Date();
            String filename = tictactoe.getPlayer1()+tictactoe.getPlayer2()+formatter.format(date);
            try {
                try {
                    ObjectOutputStream output =
                            new ObjectOutputStream(Files.newOutputStream(
                                    Paths.get("D:/" + filename)));
                    output.writeObject(tictactoe);
                    output.close();
                }catch ( java.nio.file.NoSuchFileException e){
                    return "folder 'new' not found";
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("game saved!\nfilename: "+filename);
            return "/save";



        }

        else if(Tokens[0].equals("/load")){
            try {
                try {
                    try {
                        ObjectInputStream stream =
                        new ObjectInputStream(Files.newInputStream(Paths.get("D:/" + Tokens[1])));
                        try {
                            tictactoe = (TicTacToe) stream.readObject();
                            stream.close();
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }catch(ArrayIndexOutOfBoundsException e){
                        return "input is not valid!";
                    }
                }catch(java.nio.file.NoSuchFileException e){
                    return "file not found!";
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            isStarted=true;
            return "game loaded!";




        }

        else{
            return "command is not defined!";
        }

        return "";
    }


    /**
     * main method for running CLI.
     * @param args input args.
     */
    public static void main(String[] args)  {


        TicTacToeCLI game = new TicTacToeCLI();
        Scanner scanner = new Scanner(System.in);

        String input ;
        String output;

        while(true) {
            input = scanner.nextLine();
            output = game.run(input);

            if (output.equals("/save")) break;
            System.out.println(output);
        }
    }
}
