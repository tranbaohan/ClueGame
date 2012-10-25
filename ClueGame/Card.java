package ClueGame;

public class Card {
	private String name;
	public enum CardType {PERSON, WEAPON, ROOM};
	private CardType type;
	
	public Card(String str, CardType type){
		this.name = str;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CardType getType() {
		return type;
	}
	public void setType(CardType type) {
		this.type = type;
	}
	
	public boolean equals(Object other){
		if (other instanceof Card)
			return (name.equalsIgnoreCase(((Card) other).getName()) && type == ((Card) other).getType());
		else
			return false;
	}
	
	public int hashCode(){
		int hash;
		if (type == CardType.PERSON)
			hash = 1;
		else if (type == CardType.ROOM)
			hash = 2;
		else 
			hash = 3;
		return 41 * (41 * name.hashCode() + hash);
	}
}
