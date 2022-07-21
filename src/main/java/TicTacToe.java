import java.io.Serializable;

/**
 * TicTacToe class makes a TicTacToe game with certain properties and methods.
 * <p>this game contains 2 player that can play against each other.</p><p>
 * or if you like,you can play against a AI(that you can not beat him)<p>
 *     or make two AIs to play against each other(result becomes always draw).
 * </p>
 * </p>
 */
public class TicTacToe implements Serializable {
        private final String Player1;
        private final String Player2;
        private final String[] GameMap = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        private final int[] moves =new int[]{0,0,0,0,0,0,0,0,0,0};
        private int turn = 0;
        private String result = "UnKnown";
        public TicTacToe(String player1, String player2) {
            Player1 = player1;
            Player2 = player2;
        }


    public String[] getGameMap() {return GameMap;}
    public String getResult() {
        return result;
    }
    public String getPlayer1() {return Player1;}
    public String getPlayer2() {return Player2;}
    public int getTurn(){return turn;}


    /**
     * put X or O on the chosen location
     * @param n is location between 1 and 9
     */
    public void put(int n) {
        if(!result.equals("UnKnown")){
            System.out.println("Game is over! can not continue.");
            return;
        }
        if(n<=0 || n>=10){
            System.out.println("invalid location.");
            return;
        }
        if (!GameMap[n-1].equals(String.valueOf(n))) {
            System.out.println("this location has been chosen before by:  " + GameMap[n-1]);
            return;
        }
        if (turn%2==1) {
            GameMap[n-1] = "O";
        } else{
            GameMap[n-1] = "X";
        }
        moves[turn]=n;
        turn +=1;
        Scan();
        }

    /**
     * undo the last move.
     */
        public void undo(){
        if(turn==0){
            System.out.println("can not undo");
            return;
        }
        for(int i=0;i<moves.length;i++){
            if(moves[i]==0){
                GameMap[moves[i-1]-1]=String.valueOf(moves[i-1]);
                moves[i-1]=0;
                turn-=1;
                result="UnKnown";
                return;
            }
        }
        }


    /**
     * scan the game map for result.
     */
    public void Scan() {
        if(turn==9) result="draw";
        if ((GameMap[0].equals(GameMap[1]) && GameMap[1].equals(GameMap[2])) ||
            (GameMap[3].equals(GameMap[4]) && GameMap[4].equals(GameMap[5])) ||
            (GameMap[6].equals(GameMap[7]) && GameMap[7].equals(GameMap[8])) ||
            (GameMap[0].equals(GameMap[3]) && GameMap[3].equals(GameMap[6])) ||
            (GameMap[1].equals(GameMap[4]) && GameMap[4].equals(GameMap[7])) ||
            (GameMap[2].equals(GameMap[5]) && GameMap[5].equals(GameMap[8])) ||
            (GameMap[0].equals(GameMap[4]) && GameMap[4].equals(GameMap[8])) ||
            (GameMap[2].equals(GameMap[4]) && GameMap[4].equals(GameMap[6]))) {
            if(turn%2==0)result="Player2("+Player2+") Winner";
            if(turn%2==1)result="Player1("+Player1+") Winner";
        }
    }

    /**
     * print the game map.
     */
    public void printMap(){
        for(int i=0;i<9;i++){
            if(i%3==0) System.out.println();
            if(GameMap[i].equals(String.valueOf(i + 1))) System.out.print(" - ");
            else System.out.print(" "+GameMap[i]+" ");
        }
        System.out.println();
    }
}