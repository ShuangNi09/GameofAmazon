package amazons;

import ucb.gui2.Pad;

import java.io.IOException;

import java.util.concurrent.ArrayBlockingQueue;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static amazons.Piece.*;
import static amazons.Square.sq;




/** A widget that displays an Amazons game.
 *  @author Shuang
 */
class BoardWidget extends Pad {

    /* Parameters controlling sizes, speeds, colors, and fonts. */

    /** Colors of empty squares and grid lines. */
    static final Color
        SPEAR_COLOR = new Color(0, 7, 10),
        LIGHT_SQUARE_COLOR = new Color(242, 226, 207),
        DARK_SQUARE_COLOR = new Color(250, 85, 107),
        POTENTIAL = new Color(213, 170, 170);

    /** Locations of images of white and black queens. */
    private static final String
        WHITE_QUEEN_IMAGE = "wq4.png",
        BLACK_QUEEN_IMAGE = "bq4.png";

    /** Size parameters. */
    private static final int
        SQUARE_SIDE = 30,
        BOARD_SIDE = SQUARE_SIDE * 10;

    /** A graphical representation of an Amazons board that sends commands
     *  derived from mouse clicks to COMMANDS.  */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        setMouseHandler("click", this::mouseClicked);
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);

        try {
            _whiteQueen = ImageIO.read(Utils.getResource(WHITE_QUEEN_IMAGE));
            _blackQueen = ImageIO.read(Utils.getResource(BLACK_QUEEN_IMAGE));
        } catch (IOException excp) {
            System.err.println("Could not read queen images.");
            System.exit(1);
        }
        _acceptingMoves = false;
    }

    /** Draw the bare board G.  */
    private void drawGrid(Graphics2D g) {
        g.setColor(LIGHT_SQUARE_COLOR);
        g.fillRect(0, 0, BOARD_SIDE, BOARD_SIDE);
        for (int i = 0; i < 10; i += 1) {
            for (int j = 0; j < 10; j += 1) {
                if ((i + j) % 2 == 1) {
                    g.setColor(LIGHT_SQUARE_COLOR);
                } else {
                    g.setColor(DARK_SQUARE_COLOR);
                }
                Square here = sq(i, 9 - j);
                if (here == _queen) {
                    g.setColor(POTENTIAL);
                }
                if (here == _next) {
                    g.setColor(POTENTIAL);
                }
                if (here == _spear) {
                    g.setColor(POTENTIAL);
                }
                g.fillRect(cx(i), cx(j), SQUARE_SIDE, SQUARE_SIDE);
            }
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        drawGrid(g);
        for (int i = 0; i < Board.SIZE * Board.SIZE; i++) {
            Piece thisPiece = _board.get(sq(i));
            if (thisPiece == WHITE) {
                drawQueen(g, sq(i), WHITE);
            } else if (thisPiece == BLACK) {
                drawQueen(g, sq(i), BLACK);
            } else if (thisPiece == SPEAR) {
                drawSpear(g, sq(i));
            }
        }

    }
    /** Draw a spear G for it to appear on board on S. */
    private void drawSpear(Graphics2D g, Square s) {
        g.setColor(SPEAR_COLOR);
        g.fillRect(cx(s.col()), cy(s.row()), SQUARE_SIDE, SQUARE_SIDE);
    }

    /** Draw a queen G for side PIECE at square S on G.  */
    private void drawQueen(Graphics2D g, Square s, Piece piece) {
        g.drawImage(piece == WHITE ? _whiteQueen : _blackQueen,
                    cx(s.col()) + 2, cy(s.row()) + 4, null);
    }

    /** Handle a click on S. */
    private void click(Square s) {
        _click += 1;
        if (_click == 1) {
            _queen = s;
            _move += s.toString();
            _move += " ";
        } else if (_click == 2) {
            _next = s;
            _move += s.toString();
            _move += " ";
        } else if (_click == 3) {
            _spear = s;
            _move += s.toString();
            _commands.offer(_move);
            _click = 0;
            _move = "";
            _queen = null;
            _next = null;
            _spear = null;
        }
        repaint();
    }

    /** Handle mouse click event E. */
    private synchronized void mouseClicked(String unused, MouseEvent e) {
        int xpos = e.getX(), ypos = e.getY();
        int x = xpos / SQUARE_SIDE,
            y = (BOARD_SIDE - ypos) / SQUARE_SIDE;
        if (_acceptingMoves
            && x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE) {
            click(sq(x, y));
        }
    }

    /** Revise the displayed board according to BOARD. */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /** Turn on move collection iff COLLECTING, and clear any current
     *  partial selection.   When move collection is off, ignore clicks on
     *  the board. */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /** Return x-pixel coordinate of the left corners of column X
     *  relative to the upper-left corner of the board. */
    private int cx(int x) {
        return x * SQUARE_SIDE;
    }

    /** Return y-pixel coordinate of the upper corners of row Y
     *  relative to the upper-left corner of the board. */
    private int cy(int y) {
        return (Board.SIZE - y - 1) * SQUARE_SIDE;
    }

    /** Return x-pixel coordinate of the left corner of S
     *  relative to the upper-left corner of the board. */
    private int cx(Square s) {
        return cx(s.col());
    }

    /** Return y-pixel coordinate of the upper corner of S
     *  relative to the upper-left corner of the board. */
    private int cy(Square s) {
        return cy(s.row());
    }

    /** Queue on which to post move commands (from mouse clicks). */
    private ArrayBlockingQueue<String> _commands;
    /** Board being displayed. */
    private final Board _board = new Board();

    /** Image of white queen. */
    private BufferedImage _whiteQueen;
    /** Image of black queen. */
    private BufferedImage _blackQueen;
    /** Queen position if clicked. */
    private Square _queen;
    /** Next position if clicked. */
    private Square _next;
    /** Spear position if clicked. */
    private Square _spear;
    /** Track the number of clicks I take. */
    private int _click = 0;
    /** Feed the controller with the move. */
    private String _move = "";
    /** True iff accepting moves from user. */
    private boolean _acceptingMoves;
}
