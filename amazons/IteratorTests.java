package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** Junit tests for our Board iterators.
 *  @author Shuang Ni
 */
public class IteratorTests {

    /** Run the JUnit tests in this package. */
    public static void main(String[] ignored) {
        textui.runClasses(IteratorTests.class);
    }

    /** Tests reachableFromIterator to make sure it returns all reachable
     *  Squares. This method may need to be changed based on
     *   your implementation. */
    @Test
    public void testReachableFrom() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 4), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQUARES.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQUARES.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQUARES.size(), squares.size());
    }

    @Test
    public void testReachableFromWithEmpty() {
        Board b = new Board();
        buildBoard(b, REACHABLEBOARD2);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Square s11 = Square.sq(1, 8);
        Square s00 = Square.sq(0, 9);
        Iterator<Square> reachableFrom = b.reachableFrom(s11, s00);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLETESTSQUARE2.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLETESTSQUARE2.size(), numSquares);
        assertEquals(REACHABLETESTSQUARE2.size(), squares.size());
    }

    @Test
    public void testLegalMoves2() {
        Board b = new Board();
        buildBoard(b, LEGALTESTBOARD2);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(LEGALTESTMOVES2.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(LEGALTESTMOVES2.size(), numMoves);
        assertEquals(LEGALTESTMOVES2.size(), moves.size());
    }

    @Test
    public void testLegalMoves3() {
        Board b = new Board();
        buildBoard(b, LEGALTESTBOARD3);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(LEGALTESTMOVES3.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(LEGALTESTMOVES3.size(), numMoves);
        assertEquals(LEGALTESTMOVES3.size(), moves.size());
    }

    @Test
    public void testUnblocked2() {
        Board a = new Board();
        buildBoard(a, UNBLOCKED2);
        assertFalse(a.isUnblockedMove(Square.sq("h9"), Square.sq("i10"), null));
    }
    @Test
    public void testUnblocked() {
        Board b = new Board();
        buildBoard(b, UNBLOCKED);
        assertFalse(b.isUnblockedMove(Square.sq("g10"), Square.sq("h9"), null));
    }

    @Test
    public void testLegalMoves5() {
        Board b = new Board();
        buildBoard(b, LEGALTESTBOARD5);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(LEGALTESTMOVES5.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(LEGALTESTMOVES5.size(), numMoves);
        assertEquals(LEGALTESTMOVES5.size(), moves.size());
    }

    @Test
    public void testLegalMoves4() {
        Board b = new Board();
        buildBoard(b, LEGALTESTBOARD4);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(LEGALTESTMOVES4.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(LEGALTESTMOVES4.size(), numMoves);
        assertEquals(LEGALTESTMOVES4.size(), moves.size());
        b.makeMove(Square.sq("a1"), Square.sq("a2"), Square.sq("a1"));
        b.undo();
    }

    @Test
    public void testLegalMoves6() {
        Board b = new Board();
        buildBoard(b, LEGALTESTBOARD6);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.BLACK);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(LEGALTESTMOVES6.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(LEGALTESTMOVES6.size(), numMoves);
        assertEquals(LEGALTESTMOVES6.size(), moves.size());
    }



    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testLegalMoves() {
        Board b = new Board();
        int numMoves = 0;

        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            legalMoves.next();
            numMoves += 1;

        }
        assertEquals(2176, numMoves);
    }

    @Test
    public void testLegalMoves7() {
        Board b = new Board();
        buildBoard(b, LEGALTESTBOARD7);
        assertEquals(b.winner(), Piece.BLACK);
    }

    @Test
    public void testwhitemoves() {
        Board b = new Board();
        buildBoard(b, LANGNULL);
        assertTrue(b.legalMoves().hasNext());
    }

    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - row - 1][col];
                b.put(piece, Square.sq(col, row));
            }
        }
    }

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Piece[][] REACHABLEFROMTESTBOARD = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, W, W },
            { E, E, E, E, E, E, E, S, E, S },
            { E, E, E, S, S, S, S, E, E, S },
            { E, E, E, S, E, E, E, E, B, E },
            { E, E, E, S, E, W, E, E, B, E },
            { E, E, E, S, S, S, B, W, B, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
    };

    static final Set<Square> REACHABLEFROMTESTSQUARES =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 5),
                    Square.sq(4, 5),
                    Square.sq(4, 4),
                    Square.sq(6, 4),
                    Square.sq(7, 4),
                    Square.sq(6, 5),
                    Square.sq(7, 6),
                    Square.sq(8, 7)));

    static final Piece[][] REACHABLEBOARD2 = {
            { W, S, S, S, S, S, S, S, S, S },
            { S, W, S, S, S, S, S, S, S, S },
            { S, S, E, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
    };

    static final Set<Square> REACHABLETESTSQUARE2 =
            new HashSet<>(Arrays.asList(
                    Square.sq(2, 7),
                    Square.sq(0, 9)));

    static final Piece[][] LEGALTESTBOARD2 = {
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, E, S, S, S, S, S, S, S, S },
            { W, S, S, S, S, S, S, S, S, S },
    };

    static final Set<Move> LEGALTESTMOVES2 =
            new HashSet<>(Arrays.asList(
                    Move.mv(Square.sq(0, 0), Square.sq(1, 1), Square.sq(0, 0))
            ));

    static final Piece[][] LEGALTESTBOARD3 = {
            { S, S, S, S, S, S, S, S, E, W },
            { S, S, S, S, S, S, S, S, W, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
    };

    static final Set<Move> LEGALTESTMOVES3 =
            new HashSet<>(Arrays.asList(
                    Move.mv(Square.sq(9, 9), Square.sq(8, 9), Square.sq(9, 9)),
                    Move.mv(Square.sq(8, 8), Square.sq(8, 9), Square.sq(8, 8))
            ));

    static final Piece[][] LEGALTESTBOARD4 = {
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { E, W, S, S, S, S, S, S, S, S },
            { W, S, S, S, S, S, S, S, S, S },
    };

    static final Set<Move> LEGALTESTMOVES4 =
            new HashSet<>(Arrays.asList(
                    Move.mv(Square.sq(0, 0), Square.sq(0, 1), Square.sq(0, 0)),
                    Move.mv(Square.sq(1, 1), Square.sq(0, 1), Square.sq(1, 1))
            ));

    static final Piece[][] LEGALTESTBOARD5 = {
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { B, B, S, S, S, S, S, S, S, S },
            { B, B, S, S, S, S, S, S, S, S },
    };

    static final Set<Move> LEGALTESTMOVES5 =
            new HashSet<>(Arrays.asList());

    static final Piece[][] LEGALTESTBOARD6 = {
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { E, B, S, S, S, S, S, S, S, S },
            { B, E, S, S, S, S, S, S, S, S },
    };

    static final Set<Move> LEGALTESTMOVES6 =
            new HashSet<>(Arrays.asList(
                    Move.mv(Square.sq(0, 0), Square.sq(0, 1), Square.sq(0, 0)),
                    Move.mv(Square.sq(0, 0), Square.sq(0, 1), Square.sq(1, 0)),
                    Move.mv(Square.sq(0, 0), Square.sq(1, 0), Square.sq(0, 0)),
                    Move.mv(Square.sq(0, 0), Square.sq(1, 0), Square.sq(0, 1)),
                    Move.mv(Square.sq(1, 1), Square.sq(0, 1), Square.sq(1, 1)),
                    Move.mv(Square.sq(1, 1), Square.sq(0, 1), Square.sq(1, 0)),
                    Move.mv(Square.sq(1, 1), Square.sq(1, 0), Square.sq(1, 1)),
                    Move.mv(Square.sq(1, 1), Square.sq(1, 0), Square.sq(0, 1))
            ));

    static final Piece[][] LEGALTESTBOARD7 = {
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { S, S, S, S, S, S, S, S, S, S },
            { W, S, S, S, S, S, S, S, S, S },
    };

    static final Piece[][] UNBLOCKED = {
            {S, S, S, B, S, B, E, S, S, E},
            {E, S, S, S, S, S, S, B, S, E},
            {S, S, S, S, S, S, S, E, E, E},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},

    };
    static final Piece[][] UNBLOCKED2 = {
            {S, S, S, S, E, E, S, E, B, E},
            {E, S, S, E, E, E, E, B, S, E},
            {S, E, W, E, E, E, S, E, E, E},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
    };
    static final Piece[][] LANGNULL = {
            {W, S, E, E, S, S, S, S, E, E},
            {E, S, S, E, S, W, S, S, E, S},
            {B, E, S, E, S, S, S, S, E, W},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
            {S, S, S, S, S, S, S, S, S, S},
    };

}
