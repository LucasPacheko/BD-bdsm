import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class Tupla {
	public static void main(String[] args) {
		Tupla t = new Tupla();
		t.addAtribute(1, "1");
		t.metaDadosAtributos.put(1, "I(6)");
		t.addAtribute(2, "one");
		t.metaDadosAtributos.put(2, "A(25)");
		t.addAtribute(3, "UM");
		t.metaDadosAtributos.put(3, "A(25)");
		t.addAtribute(4, "dois");
		t.metaDadosAtributos.put(4, "A(25)");
		byte[] a = t.tuplaToByte();
		System.out.println();
		Tupla tuplaNova = t.arrayToTupla(a);
		System.out.println("done");
	}

	HashMap<Integer, String> atributos;
	ArrayList<Integer> chaves;
	HashMap<Integer, String> metaDadosAtributos;

	public Tupla() {
		atributos = new HashMap<Integer, String>();
		chaves = new ArrayList<Integer>();
		metaDadosAtributos = new HashMap<Integer, String>();
	}

	public void addAtribute(int numeroAtributo, String value) {
		chaves.add(numeroAtributo);
		atributos.put(numeroAtributo, value);
	}

	public Tupla newTupla() {

		return this;
	}

	private Tupla arrayToTupla(byte[] data) {
		Tupla retorno = new Tupla();
		int atributo = 0;
		int controle = 4;
		while (controle != data.length) {
			byte[] x = { data[controle++], data[controle++] };
			ByteBuffer wrapped = ByteBuffer.wrap(x);
			short num = wrapped.getShort();

			byte[] aux = new byte[num];
			for (int i = 0; i < aux.length; i++) {
				aux[i] = data[controle++];
			}
			retorno.addAtribute(atributo++, new String(aux));
		}
		System.out.println();
		return retorno;

	}

	public byte[] tuplaToByte() {
		String dados = "";
		int controle = 0;
		int tamanho = 4;
		for (Integer value : chaves) {
			String meta=metaDadosAtributos.get(value);
			if(meta.contains("I"))tamanho=tamanho+4;
			if(meta.contains("A"))tamanho=tamanho+atributos.get(value).length();
			tamanho=tamanho+2;
			
		}
		System.out.println();
		
		
		int s = tamanho;

		byte[] retorno = new byte[s + 4];
		int tam = retorno.length;
		byte[] bytes = ByteBuffer.allocate(4).putInt(tam).array();
		ByteBuffer wraper  =  ByteBuffer.wrap(bytes);
		int pra=wraper.getInt();
		for (byte b : bytes) {
			retorno[controle++] = b;
		}
		for (Integer value : chaves) {

			dados = atributos.get(value);
			byte[] bData = dados.getBytes();
			String metaDado = metaDadosAtributos.get(value);
			if (metaDado.charAt(0) == 'I') {
				byte[] sizeBA = ByteBuffer.allocate(2).putShort((short) 4).array();
				String aaa = new String(bData);

				byte[] data = ByteBuffer.allocate(4).putInt(Integer.parseInt(aaa)).array();
				for (byte b : sizeBA) {
					retorno[controle++] = b;
				}
				for (byte b : data) {
					retorno[controle++] = b;

				}
			} else if (metaDado.charAt(0) == 'A') {
				String[] auxTemp = metaDado.split("[(]");
				String newAuz = auxTemp[1];
				newAuz = newAuz.replace(")", "");
				newAuz = newAuz.replace("A", "");
				short sizeAtributo = Short.parseShort(newAuz);
				// condicional de tamanho

				byte[] sizeBA = ByteBuffer.allocate(2).putShort((short) dados.length()).array();
				for (byte b : sizeBA) {
					retorno[controle++] = b;
				}
				for (byte b : bData) {
					retorno[controle++] = b;
				}

				// change =
				// ByteBuffer.allocate(sizeBA.length).put(wrapped).array();
			
			

			}

		}

		return retorno;
	}
}