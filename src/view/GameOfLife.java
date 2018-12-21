package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.*;

public class GameOfLife extends JFrame implements ActionListener, KeyListener {
	private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(800, 600);
	private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(800, 600);
	private static final int BLOCK_SIZE = 10;

	private int secondentakt = 3;
	private GameBoard spielfeld;
	private Thread thspiel;

	public GameOfLife() {
		spielfeld = new GameBoard();
		add(spielfeld);
	}

	public void setGameBeingPlayed(boolean isAmLaufen) {
		if (isAmLaufen) {
			thspiel = new Thread(spielfeld);
			thspiel.start();
		} else {
			thspiel.interrupt();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
	}

	private class GameBoard extends JPanel
			implements ComponentListener, MouseListener, MouseMotionListener, KeyListener, Runnable {
		private static final long serialVersionUID = 1L;
		int index = 1;
		Integer[] secondOptions = { 1, 2, 3, 4, 5, 10, 15, 20 };
		private Dimension d_gameBoardSize = null;
		private ArrayList<Point> point = new ArrayList<Point>(0);

		public GameBoard() {
			addComponentListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			setFocusable(true);
		}

		private void updateArraySize() {
			ArrayList<Point> removeList = new ArrayList<Point>(0);
			for (Point current : point) {
				if ((current.x > d_gameBoardSize.width - 1) || (current.y > d_gameBoardSize.height - 1)) {
					removeList.add(current);
				}
			}
			point.removeAll(removeList);
			repaint();
		}

		public void addPoint(int x, int y) {
			if (!point.contains(new Point(x, y))) {
				point.add(new Point(x, y));
			}
			repaint();
		}

		public void addPoint(MouseEvent me) {
			int x = me.getPoint().x / BLOCK_SIZE - 1;
			int y = me.getPoint().y / BLOCK_SIZE - 1;
			if ((x >= 0) && (x < d_gameBoardSize.width) && (y >= 0) && (y < d_gameBoardSize.height)) {
				addPoint(x, y);
			}
		}

		public void resetFeld() {
			point.clear();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				for (Point newPoint : point) {
					g.setColor(Color.blue);
					g.fillRect(BLOCK_SIZE + (BLOCK_SIZE * newPoint.x), BLOCK_SIZE + (BLOCK_SIZE * newPoint.y),
							BLOCK_SIZE, BLOCK_SIZE);
				}
				g.setColor(Color.BLACK);
				for (int i = 0; i <= d_gameBoardSize.width; i++) {
					g.drawLine(((i * BLOCK_SIZE) + BLOCK_SIZE), BLOCK_SIZE, (i * BLOCK_SIZE) + BLOCK_SIZE,
							BLOCK_SIZE + (BLOCK_SIZE * d_gameBoardSize.height));
				}
				for (int i = 0; i <= d_gameBoardSize.height; i++) {
					g.drawLine(BLOCK_SIZE, ((i * BLOCK_SIZE) + BLOCK_SIZE), BLOCK_SIZE * (d_gameBoardSize.width + 1),
							((i * BLOCK_SIZE) + BLOCK_SIZE));
				}
			} catch (ConcurrentModificationException cme) {
			} catch (NullPointerException e) {
			}
		}

		@Override
		public void componentResized(ComponentEvent e) {
			d_gameBoardSize = new Dimension(getWidth() / BLOCK_SIZE - 2, getHeight() / BLOCK_SIZE - 2);
			updateArraySize();
		}

		@Override
		public void componentMoved(ComponentEvent e) {
		}

		@Override
		public void componentShown(ComponentEvent e) {
		}

		@Override
		public void componentHidden(ComponentEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			addPoint(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			addPoint(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void run() {
			boolean[][] spielfeld = new boolean[d_gameBoardSize.width + 2][d_gameBoardSize.height + 2];
			for (Point current : point) {
				spielfeld[current.x + 1][current.y + 1] = true;
			}
			ArrayList<Point> lebendezellen = new ArrayList<Point>(0);
			for (int i = 1; i < spielfeld.length - 1; i++) {
				for (int j = 1; j < spielfeld[0].length - 1; j++) {
					int umfeld = 0;
					if (spielfeld[i - 1][j - 1]) {
						umfeld++;
					}
					if (spielfeld[i - 1][j]) {
						umfeld++;
					}
					if (spielfeld[i - 1][j + 1]) {
						umfeld++;
					}
					if (spielfeld[i][j - 1]) {
						umfeld++;
					}
					if (spielfeld[i][j + 1]) {
						umfeld++;
					}
					if (spielfeld[i + 1][j - 1]) {
						umfeld++;
					}
					if (spielfeld[i + 1][j]) {
						umfeld++;
					}
					if (spielfeld[i + 1][j + 1]) {
						umfeld++;
					}
					if (spielfeld[i][j]) {
						if ((umfeld == 2) || (umfeld == 3)) {
							lebendezellen.add(new Point(i - 1, j - 1));
						}
					} else {
						if (umfeld == 3) {
							lebendezellen.add(new Point(i - 1, j - 1));
						}
					}
				}
			}
			resetFeld();
			point.addAll(lebendezellen);
			repaint();
			try {
				Thread.sleep(1000 / secondentakt);
				run();
			} catch (InterruptedException ex) {
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				spielfeld.resetFeld();
				spielfeld.repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				setGameBeingPlayed(true);
			} else if (e.getKeyCode() == KeyEvent.VK_P) {
				setGameBeingPlayed(false);
			} else if (e.getKeyCode() == KeyEvent.VK_PLUS) {
				index++;
				if (index > 0 && index < 7) {
					secondentakt = secondOptions[index];
				}
			} else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
				index--;
				if (index > 0 && index < 7) {
					secondentakt = secondOptions[index];
				}
			}if (e.getKeyCode() == KeyEvent.VK_H) {
				dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
