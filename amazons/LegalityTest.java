package amazons;
import org.junit.Test;
import static org.junit.Assert.*;


/** Junit tests for our Board iterators.
 *  @author Shuang Ni
 */

public class LegalityTest {

    @Test
    public void testIslegal() {
        Board b = new Board();
        assertTrue(b.isLegal(Square.sq(30)));
        assertTrue(b.isLegal(Square.sq(30), Square.sq(35), Square.sq(57)));
    }

    @Test
    public void testmoving() {
        Board b = new Board();
        Move made = Move.mv("d1-d2(d3)");
        b.makeMove(made);
        Board c = new Board();
        buildBoard(c, AFTERMOVE2);
        assertEquals(b.toString(), c.toString());

    }

    @Test
    public void testmoving2() {
        Board b = new Board();
        buildBoard(b, AFTERMOVE3);
        Move made = Move.mv("f9-c6(f9)");
        b.makeMove(made);
        Board c = new Board();
        buildBoard(c, AFTERMOVE4);
        assertEquals(b.toString(), c.toString());

    }
    @Test
    public void testMove() {
        Board a = new Board();
        Board b = new Board();
        a.makeMove(Square.sq(30), Square.sq(31), Square.sq(32));
        buildBoard(b, AFTERMOVE);
        assertEquals(b.toString(), a.toString());
    }

    @Test
    public void clea() {
        Board a = new Board();
        a.makeMove(Square.sq("j4"), Square.sq("f8"), Square.sq("f9"));
        assertEquals(a.get(9, 3), Piece.EMPTY);
    }


    @Test
    public void undoTest() {
        Board a = new Board();
        Board b = new Board();
        a.makeMove(Square.sq(30), Square.sq(31), Square.sq(32));
        a.undo();
        assertEquals(b.toString(), a.toString());
        a.undo();
        assertEquals(b.toString(), a.toString());
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

    static final Piece[][] AFTERMOVE = {
            { E, E, E, B, E, E, B, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { B, E, E, E, E, E, E, E, E, B },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, W, S, E, E, E, E, E, E, W },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, W, E, E, W, E, E, E },
    };
    static final Piece[][] AFTERMOVE2 = {
            { E, E, E, B, E, E, B, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { B, E, E, E, E, E, E, E, E, B },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { W, E, E, E, E, E, E, E, E, W },
            { E, E, E, S, E, E, E, E, E, E },
            { E, E, E, W, E, E, E, E, E, E },
            { E, E, E, E, E, E, W, E, E, E },
    };
    static final Piece[][] AFTERMOVE3 = {
            {E, E, S, B, B, S, E, E, E, E},
            {E, E, B, S, S, W, E, E, E, E},
            {E, S, S, W, E, E, E, E, E, E},
            {B, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {W, E, E, E, E, E, E, E, E, W},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
    };
    static final Piece[][] AFTERMOVE4 = {
            {E, E, S, B, B, S, E, E, E, E},
            {E, E, B, S, S, S, E, E, E, E},
            {E, S, S, W, E, E, E, E, E, E},
            {B, E, E, E, E, E, E, E, E, E},
            {E, E, W, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {W, E, E, E, E, E, E, E, E, W},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
    };
    static final Piece[][] AFTERMOVE5 = {
            {B, B, S, B, B, S, E, E, E, E},
            {S, S, S, S, S, S, E, E, E, E},
            {S, S, S, W, S, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {W, E, E, E, S, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {W, E, E, E, E, E, E, E, E, W},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
            {E, E, E, E, E, E, E, E, E, E},
    };
}
