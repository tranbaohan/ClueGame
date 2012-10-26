package ClueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import ClueGame.RoomCell.DoorDirection;

public class Board {
		private ArrayList<BoardCell> cells;
		private Map<Character, String> rooms;
		private int numRows;
		private int numColumns;
		private Set<BoardCell> targets;
		private Map<Integer, LinkedList<Integer>> adjacencies;
		private LinkedList<Integer> seen;
		
		private ArrayList<ComputerPlayer> computer;
		private HumanPlayer human;
		private ArrayList<Card> cards;
		private Solution solution;
		
		public Board() {
			cells = new ArrayList<BoardCell>();
			rooms = new HashMap<Character, String>();
			adjacencies = new HashMap<Integer, LinkedList<Integer>>();
			targets = new HashSet<BoardCell>();	        
	        seen = new LinkedList<Integer>();
	        LoadConfigFiles();
			calcAdjacencies();
			
			human = new HumanPlayer();
			computer = new ArrayList<ComputerPlayer>();
			cards = new ArrayList<Card>();
			solution = new Solution();
		}
		
		public void LoadConfigFiles() {
			loadLegend();
			loadLayout();
		}
		
		public void loadLegend() {
			String legendFile = "NewLegend.txt";
			try {
				Scanner in = new Scanner(new FileReader(legendFile));
				while(in.hasNext()) {
					String[] input;
					input = in.nextLine().split(",");
					if(input.length != 2) {
						throw new BadConfigFormatException();
					}
					rooms.put(input[0].charAt(0), input[1].trim());
				}
			} catch(FileNotFoundException e) {
				System.out.println("File not Found: "+ legendFile);
			} catch(BadConfigFormatException e) {
				System.out.println(e.getMessage());
			}
		}
		
		public void loadLayout() {
			String layoutFile = "NewBoard.csv";
			try {
				Scanner in  = new Scanner(new FileReader(layoutFile));
				int cols = 0;
				int rows = 0;
				while(in.hasNext()) {
					String[] input;
					input = in.nextLine().split(",");
					cols = input.length;
					rows++;
					for(String s : input) {
						if(s.equals("W")) {
							cells.add(new WalkwayCell());
						}
						else if( s.length() == 2) {
							if(s.charAt(1) == 'R' && rooms.containsKey(s.charAt(0))) {
								cells.add(new RoomCell(s.charAt(0), DoorDirection.RIGHT));
							}
							else if(s.charAt(1) == 'L' && rooms.containsKey(s.charAt(0))) {
								cells.add(new RoomCell(s.charAt(0), DoorDirection.LEFT));
							}
							else if(s.charAt(1) == 'U' && rooms.containsKey(s.charAt(0))) {
								cells.add(new RoomCell(s.charAt(0), DoorDirection.UP));
							}
							else if(s.charAt(1) == 'D' && rooms.containsKey(s.charAt(0))) {
								cells.add(new RoomCell(s.charAt(0), DoorDirection.DOWN));
							} else {
								throw new BadConfigFormatException();
							}
						}
						else if ((s.length() == 1) && (rooms.containsKey(s.charAt(0)))) 
								cells.add(new RoomCell(s.charAt(0), DoorDirection.NONE));
						else
							throw new BadConfigFormatException();
					}
					
				}
				numRows = rows;
				numColumns = cols;
			} catch(FileNotFoundException e) {
				System.out.println("File not found: " + layoutFile);
			} catch(BadConfigFormatException e) {
				System.out.println(e.getMessage());
			}
		}
		
		public int calcIndex(int row, int col) {
			return (row * numColumns) + col;
		}
		
		public BoardCell getCellAt(int index) {
			return cells.get(index);
		}
		
		public RoomCell getRoomCellAt(int row, int col) {
			BoardCell cell = getCellAt(calcIndex(row, col));
			if (cell.isRoom())
				return ((RoomCell) cell);
			return null;
		}

		public void calcAdjacencies() {
	        for( int r = 0; r < numRows; r++) {
	            for(int c = 0; c < numColumns; c++) {
	                int currentIndex = calcIndex(r, c);
	                BoardCell currentCell = getCellAt(currentIndex);
	                
	                LinkedList<Integer> adj = new LinkedList<Integer>();
	                
	                if (currentCell.isRoom()) {
	                	RoomCell currentRoom = (RoomCell) currentCell;
	                	if (currentRoom.isDoorway()) {
	                		if (currentRoom.getDoorDirection() == DoorDirection.DOWN)
	                			adj.add(calcIndex(r + 1, c));
	                		else if (currentRoom.getDoorDirection() == DoorDirection.LEFT)
	                			adj.add(calcIndex(r, c - 1));
	                		else if (currentRoom.getDoorDirection() == DoorDirection.RIGHT)
	                			adj.add(calcIndex(r, c + 1));
	                		else if (currentRoom.getDoorDirection() == DoorDirection.UP)
	                			adj.add(calcIndex(r - 1, c));
	                	}	                	
	                } else {
	                	if (r > 0) {
	                		int northIndex = calcIndex(r -1, c);
	                		BoardCell northCell = getCellAt(northIndex);
	                		if (northCell.isWalkaway())
	                			adj.add(northIndex);
	                		else if (northCell.isRoom() && ((RoomCell) northCell).getDoorDirection() == DoorDirection.DOWN)
	                			adj.add(northIndex);
	                	}
	                	if (c > 0) {
	                		int westIndex = calcIndex(r, c -1);
	                		BoardCell westCell = getCellAt(westIndex);
	                		if (westCell.isWalkaway())
	                			adj.add(westIndex);
	                		else if (westCell.isRoom() && ((RoomCell) westCell).getDoorDirection() == DoorDirection.RIGHT)
	                			adj.add(westIndex);
	                	}
	                	if (r < (numRows -1)) {
	                		int southIndex = calcIndex(r + 1, c);
	                		BoardCell southCell = getCellAt(southIndex);
	                		if (southCell.isWalkaway())
	                			adj.add(southIndex);
	                		else if (southCell.isRoom() && ((RoomCell) southCell).getDoorDirection() == DoorDirection.UP)
	                			adj.add(southIndex);
	                	}
	                	if (c < (numColumns -1)) {
	                		int eastIndex = calcIndex(r, c +1);
	                		BoardCell eastCell = getCellAt(eastIndex);
	                		if (eastCell.isWalkaway())
	                			adj.add(eastIndex);
	                		else if (eastCell.isRoom() && ((RoomCell) eastCell).getDoorDirection() == DoorDirection.LEFT)
	                			adj.add(eastIndex);
	                	}
	                }
	                adjacencies.put(currentIndex, adj);
	            }
	        }
	    }
		
		public void calcTargets(int start, int numSteps) {
			targets.clear();
			seen.clear();
			generateTargets(start, numSteps);
		}
		
	    public void generateTargets(int start, int numSteps) {
	    	seen.push(start);
	    	if (numSteps == 0) {
	    		targets.add(getCellAt(start));
	    		if(cells.get(start).isWalkaway() || cells.get(start).isDoorway()) {
	    			targets.add(cells.get(start));
	    			return;
	    		}
	    		return;
	    	}
	    	LinkedList<Integer> adjList = getAdjList(start);
	    	
	    	for (int i : adjList) {
	    		if (!seen.contains(i)) {
	    			if(cells.get(i).isDoorway()) {
	    				generateTargets(i, 0);
	    			} else {
	    				generateTargets(i, numSteps -1);
	    			}
	    			seen.pop();
	    		}
	    	}
	    }
	    
	    public void clearTargets() {
	    	targets.clear();
	    	seen.clear();
	    }
	    
	    public Set<BoardCell> getTargets() {
	        return targets;       
	    }
	   
	    public LinkedList<Integer> getAdjList(int whichAdjList) {
	        return adjacencies.get(whichAdjList);      
	    }
	    
		public ArrayList<BoardCell> getCells() {
			return cells;
		}

		public Map<Character, String> getRooms() {
			return rooms;
		}

		public int getNumRows() {
			return numRows;
		}

		public int getNumColumns() {
			return numColumns;
		}
		
		/////////////////////////////////////////////////////////////////////
		public void selectAnswer(){
			
		}
		
		public void deal(ArrayList<String> cardList){
			
		}
		
		public void deal(){
			
		}
		
		public boolean checkAccusation(String person, String weapon, String room){
			return true;
		}
		
		public Card handleSuggestion(Suggestion suggest, Player currentPlayer){
			return new Card("", Card.CardType.PERSON);
		}
		
		public void loadCards(){
			
		}
		
		public void loadPlayers(){
			
		}
		
		public ArrayList<ComputerPlayer> getComputer() {
			return computer;
		}

		public HumanPlayer getHuman() {
			return human;
		}

		public ArrayList<Card> getCards() {
			return cards;
		}

		public Solution getSolution() {
			return solution;
		}

		public void setSolution(Solution solution) {
			this.solution = solution;
		}
		
}
