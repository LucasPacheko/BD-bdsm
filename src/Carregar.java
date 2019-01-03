import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Carregar {
	static BufferedReader in;
	static HashMap<Integer, String> metaDadosAtributos;

	static ArrayList<String> dados = new ArrayList<String>();
	static HashMap<Integer, String> nomesAtributos = new HashMap<Integer, String>();
	static ArrayList<Bloco> arquivo = new ArrayList<Bloco>();
	static int controle = 0;

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new FileReader("dados"));
		while (in.ready()) {
			dados.add(in.readLine());
		}
		metaDadosAtributos = new HashMap<Integer, String>();
		String linha = dados.get(0);
		String[] metaDados = linha.split("[|]");
		Tupla modelo = new Tupla();
		for (String string : metaDados) {
			string = string.replace("[", "#");
			String[] aux = string.split("#");
			nomesAtributos.put(controle, aux[0]);

			String meta = aux[1];
			meta = meta.replace("]", "");
			modelo.atributos.put(controle, aux[0]);
			metaDadosAtributos.put(controle, meta);
			modelo.metaDadosAtributos.put(controle++, meta);

		}
		System.out.println("alou");
		arquivo.add(Bloco.blocoControle(metaDadosAtributos));
		Bloco controle = new Bloco();
		arquivo.add(controle);

		System.out.println();
		dados.remove(0);
		int r=0;
		for (String line : dados) {
			if(r++==400){
				System.out.println();
			}
			Tupla tupla = modelo.newTupla();
			String[] rawData = line.split("[|]");
			for (int i = 0; i < rawData.length; i++) {
				tupla.addAtribute(i, rawData[i]);
			}
			if (controle.put(tupla)) {

			} else {
				System.out.println("Novo bloco");
				controle = new Bloco();
				arquivo.add(controle);
			}

		}

		System.out.println();
	}

}
