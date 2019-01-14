import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import models.Bloco;

public class Manager {
	static BufferedReader in;
	static HashMap<Integer, String> metaDadosAtributos;

	static ArrayList<Bloco> arquivoDeDados = new ArrayList<Bloco>();
	static int controle = 0;
	static Bloco currentBlock = null;
	static int tamanhoBlocos = 32768;
	static int status = 0;

	public static void main(String[] args) throws IOException {
		System.out.println("Vamo q vamo");
		currentBlock = new Bloco(tamanhoBlocos);
		in = new BufferedReader(new FileReader("dados"));

		setControle(in.readLine());

		while (in.ready()) {

		}

	}

	private static void setControle(String readLine) {
	}

}
