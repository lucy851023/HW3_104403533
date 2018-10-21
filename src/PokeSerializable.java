import java.io.Serializable;

public class PokeSerializable implements Serializable{

	private String nickname;
	private String monster;
	private int candy;

        public PokeSerializable(){
		this("","",0);
	}

	public PokeSerializable(String n,String m,int c){
	
		nickname = n;
		monster = m;
		candy = c;
	}

 	public void setNickname(String n){nickname = n;}
	public void setMonster(String m){monster = m;}
	public void setCandy(int c){candy = c;}
	public String getNickname(){return nickname;}
	public String getMonster(){return monster;}
	public int getCandy(){return candy;}
}