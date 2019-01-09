package amazons;

import java.util.Iterator;
import static amazons.Piece.*;
import java.util.Stack;
import java.util.Date;

/** A Player that automatically generates moves.
 *  @author Shuang
 */
class AI extends Player {

    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;
    /** StartTime for the iterative deepening process. */
    private static long startTime = System.currentTimeMillis();
    /** Calculated elapsed time. */
    private static long elapsed = 0L;

    /** A new AI with no piece or controller (intended to produce
     *  a template). */
    AI() {
        this(null, null);
    }

    /** A new AI playing PIECE under control of CONTROLLER. */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        startTime = System.currentTimeMillis();
        if (_myPiece == WHITE) {
            f(b, maxDepth(b), 1, -INFTY, INFTY);
        } else {
            f(b, maxDepth(b), -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int f(Board board, int depth, int sense,
                         int alpha, int beta) {
        elapsed = (new Date()).getTime() - startTime;
        if (depth == 0 || board.winner() != null
                || elapsed > Integer.parseInt("1000")) {
            return staticScore(board);
        }
        if (sense == 1) {
            Iterator<Move> chufa = board.legalMoves();
            while (chufa.hasNext()) {
                Move dao = chufa.next();
                board.makeMove(dao);
                alpha = Math.max(alpha, f(board, depth - 1, -1, alpha, beta));
                board.undo();
                _lastFoundMove = dao;
                if (alpha > beta) {
                    return alpha;
                }
            }
            return alpha;
        } else {
            Iterator<Move> chufa = board.legalMoves();
            while (chufa.hasNext()) {
                Move dao = chufa.next();
                board.makeMove(dao);
                beta = Math.min(beta, f(board, depth - 1, 1, alpha, beta));
                board.undo();
                _lastFoundMove = dao;
                if (alpha > beta) {
                    return beta;
                }
            }
            return beta;
        }
    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        return 8;
    }


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == BLACK) {
            return -WINNING_VALUE;
        } else if (winner == WHITE) {
            return WINNING_VALUE;
        } else {
            int myqueens = 0;
            Stack<Square> myqueensset = new Stack<>();
            for (int i = 0; i <= 9; i += 1) {
                for (int j = 0; j <= 9; j += 1) {
                    if (board.get(i, j) == WHITE) {
                        myqueensset.push(Square.sq(i, j));
                        myqueens += 1;
                    }
                    if (myqueens == 4) {
                        break;
                    }
                }
            }
            int howmanycanreach = 0;
            while (!myqueensset.empty()) {
                Square q = myqueensset.pop();
                Iterator<Square> fromme = board.reachableFrom(q, null);
                while (fromme.hasNext()) {
                    fromme.next();
                    howmanycanreach += 1;
                }
            }
            int hisqueens = 0;
            Stack<Square> hisqueensset = new Stack<>();
            for (int i = 0; i <= 9; i += 1) {
                for (int j = 0; j <= 9; j += 1) {
                    if (board.get(i, j) == BLACK) {
                        myqueensset.push(Square.sq(i, j));
                        hisqueens += 1;
                    }
                    if (hisqueens == 4) {
                        break;
                    }
                }
            }
            int howmanycanhreach = 0;
            while (!hisqueensset.empty()) {
                Square q = hisqueensset.pop();
                Iterator<Square> fromhe = board.reachableFrom(q, null);
                while (fromhe.hasNext()) {
                    fromhe.next();
                    howmanycanhreach += 1;
                }
            }
            return howmanycanreach - howmanycanhreach;
        }
    }

}
