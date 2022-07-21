import java.util.Arrays;
import java.util.Objects;

/**
 * <p>it's minimap algorithm for TicTacToi game that choose the best and fastest move for victory.</p>
 * <p>contains two part that named maximizer and minimizer.</p>
 * <p>maximizer trying to find the best move to get the maximum score
 * and against it,minimizer trying to find the best move to minimize the enemy's score.</p>
 * <p>in other word this algorithm simulates a state that 2 players trying their best to defeat
 * each others and never make a mistake.</p>
 */
public class MiniMaxAI {
    private int BestMove;
    private int depth;
    private int counter;
    private final Integer[] counterArray=new Integer[9];
    private final Integer[] Alpha={5, 5, 5, 5};
    private final Integer[] Beta= {-5, -5, -5, -5};
    private int WayPassed;
    private boolean isMinimizer=true;


    /**
     * AI start playing with given map and its turn with minimax algorithm;
     * @param game TicTacToe gameMap
     * @return the best move of the game.
     */
    public int AIPlay(TicTacToe game){
        for(int i=0;i<4;i++){
            Alpha[i]=5;
            Beta[i]=-5;
        }
        WayPassed=0;
        if(game.getTurn()%2==0){
            isMinimizer=false;
            FindMax(game);
        }
        if(game.getTurn()%2==1) FindMin(game);
        System.out.println("all way passed is: "+WayPassed);
        return BestMove+1;
    }

    /**
     * in game tree, every time it is maximizer's turn, this method will be called.
     * @param MaxMap map of TicTacToe game
     * @return the most valuable move for Maximizer
     */
    public int FindMax(TicTacToe MaxMap){
        Integer[] result=new Integer[9];
        for(int i=1;i<10;i++){
            if (MaxMap.getGameMap()[i-1].equals(String.valueOf(i))){
                MaxMap.put(i);
                counter++;
                depth++;
                WayPassed++;
                if(!Objects.equals(MaxMap.getResult(), "UnKnown"))result[i-1]=EndCheck(MaxMap);
                else result[i-1]=FindMin(MaxMap);
                depth--;
                MaxMap.undo();
            }else result[i-1]=-2;
            if(AlphaBetaPruning(result[i-1],MaxMap)!=100)return result[i-1];
            if(depth==0){
                counterArray[i-1]=counter;
                counter=0;
            }
        }
        if(depth==0)BestMove=FindBestMove(result,MaxMap);
        return  result[HighScore(result)];

    }
    /**
     * in game tree, every time it is minimizer's turn, this method will be called.
     * @param MinMap map of TicTacToe game
     * @return the most valuable move for minimizer.
     */
    public int FindMin(TicTacToe MinMap){
        Integer[] result=new Integer[9];
        for(int i=1;i<10;i++){
            if (MinMap.getGameMap()[i-1].equals(String.valueOf(i))){
                MinMap.put(i);
                counter++;
                depth++;
                WayPassed++;
                if(!Objects.equals(MinMap.getResult(), "UnKnown"))result[i-1]=EndCheck(MinMap);
                else result[i-1]=FindMax(MinMap);
                depth--;
                MinMap.undo();
            }else result[i-1]=2;
            if(AlphaBetaPruning(result[i-1],MinMap)!=100)return result[i-1];
            if(depth==0){
                counterArray[i-1]=counter;
                counter=0;
            }
        }
        if(depth==0)
            BestMove=FindBestMove(result,MinMap);
        return  result[LowScore(result)];
    }

    /**
     * in every stage of game tree,
     * when the maximizer performs all possible movements with given value this method will
     * be called to choose best move
     * @param moves all possible moves with given value.
     * @return the most valuable move(maximum value).
     */
    public int HighScore(Integer[] moves){
        int max=-10;
        for(int i=0;i<=moves.length-1;i++){
            if(max<=moves[i])max=moves[i];
        }
        return Arrays.asList(moves).indexOf(max);
    }
    /**
     * in every stage of game tree,
     * when the minimizer performs all possible movements with given value this method will
     * be called to choose best move
     * @param moves all possible moves with given value.
     * @return the most valuable move(minimum valve).
     */
    public int LowScore(Integer[] moves){
        int min=10;
        for(int i=0;i<=moves.length-1;i++){
            if(min>=moves[i])min=moves[i];
        }
        return Arrays.asList(moves).indexOf(min);
    }

    /**
     * when every possible move happens this method check if the game ends or not.
     * @param Map  map of TicTacToe game.
     * @return the value associated with each result.
     * <p>if maximizer wins returns 1.</p>
     * <p>if minimizer wins returns -1.</p>
     * <p>and if the game is tied returns 0.</p>
     */
    public int EndCheck(TicTacToe Map){
        if(Map.getResult().equals("Player2("+Map.getPlayer2()+") Winner"))return -1;
        else if(Map.getResult().equals("Player1("+Map.getPlayer1()+") Winner"))return 1;
        else return 0;
    }

    /**
     * at the end, when we perform all movements and passes all the tree game,
     * it's time to choose the best and fastest move for AI.
     * @param result results of all possible moves for AI in its turn.
     * @param game gameMap
     * @return best move for AI
     */
    public int FindBestMove(Integer[] result, TicTacToe game){
        int temp=1000000;
        int index=0;
        if(game.getTurn()%2==0){
            for(int i=0;i<result.length;i++){
                if(Objects.equals(result[HighScore(result)], result[i]) & counterArray[i]<temp){
                    temp=counterArray[i];
                    index=i;
                }
            }
            BestMove= index;
        }
        if(game.getTurn()%2==1){
            for(int i=0;i<result.length;i++){
                if(Objects.equals(result[LowScore(result)], result[i]) & counterArray[i]<temp){
                    temp=counterArray[i];
                    index=i;
                }
            }
            BestMove= index;
        }
        return BestMove;
    }

    /**
     * A method for pruning unnecessary branches of game tree
     * @param result a value we want to check is it enough for stop searching?
     * @param game game map
     * @return value that give us enough info to stop continue for searching.
     */
    public int AlphaBetaPruning(int result,TicTacToe game){
        if(game.getTurn()%2==0){
            if(depth==1 && result>Alpha[0])return result;
            if(depth==3 && result>Alpha[1])return result;
            if(depth==5 && result>Alpha[2])return result;
            if(depth==7 && result>Alpha[3])return result;

            if(depth==1 && result>Beta[0]) Beta[0]=result;
            if(depth==3 && result>Beta[1]) Beta[1]=result;
            if(depth==5 && result>Beta[2]) Beta[2]=result;
            if(depth==7 && result>Beta[3]) Beta[3]=result;

            if(!isMinimizer) {
                if (depth == 0 && result > Beta[0]) Beta[0] = result;
                if (depth == 2 && result > Beta[1]) Beta[1] = result;
                if (depth == 4 && result > Beta[2]) Beta[2] = result;
                if (depth == 6 && result > Beta[3]) Beta[3] = result;

                if (depth == 2 && result > Alpha[0]) return result;
                if (depth == 4 && result > Alpha[1]) return result;
                if (depth == 6 && result > Alpha[2]) return result;
                if (depth == 8 && result > Alpha[3]) return result;


            }
        }
        if(game.getTurn()%2==1) {
            if(depth==2 && result<Beta[0])return result;
            if(depth==4 && result<Beta[1])return result;
            if(depth==6 && result<Beta[2])return result;
            if(depth==8 && result<Beta[3])return result;

            if(depth==0 && result<Alpha[0]) Alpha[0]=result;
            if(depth==2 && result<Alpha[1]) Alpha[1]=result;
            if(depth==4 && result<Alpha[2]) Alpha[2]=result;
            if(depth==6 && result<Alpha[3]) Alpha[3]=result;

            if(!isMinimizer) {
                if (depth == 1 && result < Alpha[0]) Alpha[0] = result;
                if (depth == 3 && result < Alpha[1]) Alpha[1] = result;
                if (depth == 5 && result < Alpha[2]) Alpha[2] = result;
                if (depth == 7 && result < Alpha[3]) Alpha[3] = result;

                if (depth == 1 && result < Beta[0]) return result;
                if (depth == 3 && result < Beta[1]) return result;
                if (depth == 5 && result < Beta[2]) return result;
                if (depth == 7 && result < Beta[3]) return result;


            }



        }
        return 100;
    }
}

