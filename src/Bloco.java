import java.awt.RenderingHints.Key;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

public class Bloco {
	static public final int size = 4096;
	public static int contID = 0;
	public int ID;
	public byte[] conteudo;
	public int filled;
	public int controle;
	public static void main(String[] args) {
		HashMap<Integer, String> nomesAtributos = new HashMap<Integer, String>();
		nomesAtributos.put(1, "I(6)");
		nomesAtributos.put(2, "A(25)");
		nomesAtributos.put(3, "A(25)");
		nomesAtributos.put(4, "A(25)");
		nomesAtributos.put(5, "A(25)");
		nomesAtributos.put(6, "A(25)");

		Bloco b = blocoControle(nomesAtributos);
		System.out.println("PAARAA");
	}

	public Bloco() {
		 controle=0;
		ID = ++contID;
		conteudo = new byte[size];
		conteudo[controle++] = (byte) 1;
		byte[] bytes = ByteBuffer.allocate(4).putInt(ID).array();
		int j = bytes.length - 1;
		for (int i = controle + 2; j > 0; i--) {
			conteudo[i] = bytes[j--];
			controle++;
		}
		conteudo[controle++]=1;
		byte[] bytesH = ByteBuffer.allocate(4).putInt(controle).array();
		int t = bytesH.length - 1;
		for (int i = controle + 2; t > 0; i--) {
			conteudo[i] = bytesH[t--];
			controle++;
		}
		

		// TODO Auto-generated constructor stub

	}
	public boolean put(Tupla tupla) {
		byte[] ab =tupla.tuplaToByte();
		int tamanho=ab.length;
		if(controle+ab.length>size)return false;
		filled=+tamanho;
		for (byte b : ab) {
			conteudo[controle++]=b;
		}
		System.out.println("controle: "+controle);
		return true;
	}
	static public Bloco blocoControle(HashMap<Integer, String> atributos) {
		Bloco block = new Bloco();
		int controle = 0;
		int tamanhoMeta = 0;

		Set<Integer> ks = atributos.keySet();
		tamanhoMeta = ks.size() * 3;
		

		byte[] metaDados = new byte[tamanhoMeta + 11];
		metaDados[controle] = (byte) 0;

		byte[] bytes = ByteBuffer.allocate(4).putInt(size).array();
		int j = bytes.length - 1;
		for (int i = controle + 3; j > 0; i--) {
			metaDados[i] = bytes[j--];
			controle++;
		}
		System.out.println("STRATEGIC STOP");
		controle++;
		metaDados[controle++] = (byte) 1;
		controle++;
		controle++;
		controle++;
		metaDados[controle++] = (byte) 1;
		byte[] bytesH = ByteBuffer.allocate(2).putShort((short) tamanhoMeta).array();
		for (byte b : bytesH) {
			metaDados[controle++] = b;
		}
		for (Integer key : ks) {
			String meta = atributos.get(key);
			String type = meta.substring(0, 1);
			byte[] auxArray = type.getBytes();
			metaDados[controle++] = auxArray[0];
			String[] auxTemp = meta.split("[(]");
			String newAuz = auxTemp[1];

			newAuz = newAuz.replace(")", "");
			int x = Integer.parseInt(newAuz);
			byte[] bytesAux = ByteBuffer.allocate(2).putShort((short) x).array();

			for (byte b : bytesAux) {
				metaDados[controle++] = b;
			}

		}
		block.conteudo = metaDados;

		return block;

	}

}