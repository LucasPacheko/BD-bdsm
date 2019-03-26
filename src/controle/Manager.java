package controle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import jdk.nashorn.internal.ir.BlockLexicalContext;
import models.Bloco;

public class Manager {

	public static HashMap<Integer, Bloco> arquivoDeDados;

	static final int  tamanhoBlocos = 4096;

	public static void main(String[] args) throws IOException {
		arquivoDeDados = new HashMap<Integer, Bloco>();
//		BufferedReader in = new BufferedReader(new FileReader("Arquivo/forn-tpch.txt"));
		BufferedReader in = new BufferedReader(new FileReader("Arquivo/cli-tpch.txt"));
		// Primeira linha do arquivo é o header
		Bloco controle = new Bloco(tamanhoBlocos, in.readLine());
		// Bloco de controle e o primeiro
		arquivoDeDados.put(0, controle);
		Bloco novoBloco =  new Bloco(tamanhoBlocos);
		controle.setProxBlocoLivre(novoBloco);
		arquivoDeDados.put(novoBloco.getIdBlocoInt(), novoBloco);
		String line = "";
		while ((line = in.readLine()) != null) {
			addTupla(line);
		}
		RandomAccessFile raw =  new RandomAccessFile(new File("dados.bin"), "rw");
		for ( Entry<Integer, Bloco> entry : arquivoDeDados.entrySet()) {
			byte[] b =  entry.getValue().getConteudo();
			raw.write(b);
		}
		
		
	}
	static void carregar() {
		arquivoDeDados = new HashMap<Integer, Bloco>();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("Arquivo/cli-tpch.txt"));
		 
		// Primeira linha do arquivo é o header
		Bloco controle = new Bloco(tamanhoBlocos, in.readLine());
		// Bloco de controle e o primeiro
		arquivoDeDados.put(0, controle);
		Bloco novoBloco =  new Bloco(tamanhoBlocos);
		controle.setProxBlocoLivre(novoBloco);
		arquivoDeDados.put(novoBloco.getIdBlocoInt(), novoBloco);
		String line = "";
		while ((line = in.readLine()) != null) {
			addTupla(line);
		}
		RandomAccessFile raw =  new RandomAccessFile(new File("dados.bin"), "rw");
		for ( Entry<Integer, Bloco> entry : arquivoDeDados.entrySet()) {
			byte[] b =  entry.getValue().getConteudo();
			raw.write(b);
		}
		
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static void addTupla(String tupla) {
		String[] data = tupla.split("\\|");
		Bloco controle = arquivoDeDados.get(0);
		int proxBloco = controle.getProximoBlocoID();
		Bloco bloco = arquivoDeDados.get(controle.getProximoBlocoID());
	
		if (!bloco.putData(data)) {
			arquivoDeDados.put(controle.getProximoBlocoID() + 1, new Bloco(tamanhoBlocos));
			bloco = arquivoDeDados.get(controle.getProximoBlocoID() + 1);
			controle.setProxBlocoLivre(bloco);
		}
	}

}
