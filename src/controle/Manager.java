package controle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import models.Bloco;

public class Manager {
	

	static HashMap<Integer,Bloco> arquivoDeDados;

	
	static int tamanhoBlocos = 32768;
	
	public static void main(String[] args) throws IOException {
		arquivoDeDados= new HashMap<Integer,Bloco>();
		BufferedReader in = new BufferedReader(new FileReader("dados"));
		//Primeira linha do arquivo é o header
		Bloco controle = new Bloco(tamanhoBlocos, in.readLine());
		arquivoDeDados.put(0, controle);
		while (in.ready()) {
			
		}

	}
	static void addTupla(String tupla){
		String[] data = tupla.split("\\|");
		Bloco controle = arquivoDeDados.get(0);
		Bloco bloco = arquivoDeDados.get(controle.getProximoBloco());
		if(bloco)
		if(bloco.putData(data)){
			
		}
	}

	

}
