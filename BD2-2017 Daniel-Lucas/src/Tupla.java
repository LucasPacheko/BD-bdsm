import java.util.HashMap;

public class Tupla{
	HashMap<String, String> atributos;
	public Tupla(){
		atributos = new HashMap<String,String>();
	}
	public void addAtribute(String nomeAtributo, String value){
		atributos.put( nomeAtributo, value);
	}
}