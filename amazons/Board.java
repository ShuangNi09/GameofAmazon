package amazons;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Stack;

import static amazons.Piece.*;
import static amazons.Utils.error;


/** The state of an Amazons Game.
 *  @author Shuang Ni
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 10; j += 1) {
                this._allpieces[i][j] = model._allpieces[i][j];
            }
        }
        _turn = model._turn;
        _winner = model._winner;
    }

    /** Clears the board to the initial position. */
    void init() {
        _turn = WHITE;
        _winner = EMPTY;
        for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 10; j += 1) {
                _allpieces[i][j] = EMPTY;
            }
        }
        put(BLACK, 3, 9);
        put(BLACK, 6, 9);
        put(BLACK, 0, 6);
        put(BLACK, 9, 6);
        put(WHITE, 'a', '4');
        put(WHITE, 'j', '4');
        put(WHITE, 'd', '1');
        put(WHITE, 'g', '1');
    }

    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return _donemoves.size();
    }

    /** flip of the current side. */
    public void flip() {
        this._turn = this._turn.opponent();
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        Iterator<Move> currentplayer = legalMoves();
        if (!currentplayer.hasNext() && this.turn() == WHITE) {
            return BLACK;
        } else if (!currentplayer.hasNext() && this.turn() == BLACK) {
            return WHITE;
        } else {
            return null;
        }
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return get(s.col(), s.row());
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        return _allpieces[col][row];
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        put(p, s.col(), s.row());
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        _allpieces[col][row] = p;
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }


    /** Definitions of direction for queenMove.  DIR[k] = (dcol, drow)
     *  means that to going one step from (col, row) in direction k,
     *  brings us to (col + dcol, row + drow). */
    private static final int[][] DIR = {
            { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
            { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        if (!from.isQueenMove(to)) {
            return false;
        } else if (get(to) == EMPTY || to == asEmpty) {
            int dir = from.direction(to);
            int[] realdir = DIR[dir];
            while (from != to) {
                int newcol = from.col() + realdir[0];
                int newrow = from.row() + realdir[1];
                from = Square.sq(newcol, newrow);
                if (from != to && get(from) != EMPTY && from != asEmpty) {
                    return false;
                }
            } return true;
        } else {
            return false;
        }
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        return get(from) == _turn;
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        return isUnblockedMove(from, to, null);
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        if (isUnblockedMove(from, to, null)) {
            return isUnblockedMove(to, spear, from);
        }
        return false;
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to(), move.spear());
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move. */
    void makeMove(Square from, Square to, Square spear) {
        if (!isLegal(from, to, spear)) {
            throw error("Invalid move. Please try again.");
        } else if (get(from) != turn()) {
            throw error("Invalid move. Please try again.");
        }
        Move made = Move.mv(from, to, spear);
        _donemoves.push(made);
        put(EMPTY, from);
        put(turn(), to);
        put(SPEAR, spear);
        flip();
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        if (move == null) {
            throw error("Invalid move. Please try again.");
        }
        makeMove(move.from(), move.to(), move.spear());
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        if (!_donemoves.empty()) {
            Move undomove = _donemoves.pop();
            put(EMPTY, undomove.spear());
            Piece store = get(undomove.to());
            put(store, undomove.from());
            put(EMPTY, undomove.to());
            if (_turn == BLACK) {
                _turn = WHITE;
            } else {
                _turn = BLACK;
            }
        }
    }

    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {

        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = 0;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            Square nextmove = _from.queenMove(_dir, _steps);
            toNext();
            return nextmove;
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. */
        private void toNext() {
            Square nextmove = _from.queenMove(_dir, _steps + 1);
            if (nextmove != null) {
                if (nextmove == _asEmpty || get(nextmove) == EMPTY) {
                    _steps += 1;
                } else {
                    if (_dir < 8) {
                        _steps = 0;
                        _dir += 1;
                        toNext();
                    }
                }
            } else {
                if (_dir < 8) {
                    _steps = 0;
                    _dir += 1;
                    toNext();
                }
            }
        }

        /** Starting square. */
        private Square _from;
        /** Current direction. */
        private int _dir;
        /** Current distance. */
        private int _steps;
        /** Square treated as empty. */
        private Square _asEmpty;
    }

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThr = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return (!(get(_start) == EMPTY))
                    && hasNext;
        }

        @Override
        public Move next() {
            Move next =  Move.mv(_start, _nextSquare, _spearThr.next());
            toNext();
            return next;
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            if (!_spearThr.hasNext()) {
                if (_pieceMoves.hasNext()) {
                    _nextSquare = _pieceMoves.next();
                    _spearThr = reachableFrom(_nextSquare, _start);
                } else {
                    while (_startingSquares.hasNext()) {
                        _start = _startingSquares.next();
                        while (get(_start) != _fromPiece
                                && _startingSquares.hasNext()) {
                            _start = _startingSquares.next();
                        }
                        if (get(_start) == _fromPiece) {
                            _pieceMoves = reachableFrom(_start, null);
                            if (_pieceMoves.hasNext()) {
                                _nextSquare = _pieceMoves.next();
                                _spearThr = reachableFrom(_nextSquare, _start);
                                return;
                            }
                        }
                    }
                    hasNext = false;
                }
            }
        }

        /** Color of side whose moves we are iterating. */
        private Piece _fromPiece;
        /** Current starting square. */
        private Square _start;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _startingSquares;
        /** Current piece's new position. */
        private Square _nextSquare;
        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThr;
        /** Keep track in order to avoid nullpointer. */
        private boolean hasNext = true;
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        for (int i = 9; i >= 0; i -= 1) {
            out.format("  ");
            for (int j = 0; j < 10; j += 1) {
                if (_allpieces[j][i] == null) {
                    out.format(" -");
                } else {
                    out.format(" " + _allpieces[j][i].toString());
                }
            }
            out.format("%n");
        }
        return out.toString();
    }

    /** An empty iterator for initialization. */
    private static final Iterator<Square> NO_SQUARES =
        Collections.emptyIterator();

    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;

    /** A 2d array representation of the board having pieces. */
    private Piece[][] _allpieces = new Piece[10][10];

    /**Stores the moves that have not been undone. */
    private Stack<Move> _donemoves = new Stack<>();
}
