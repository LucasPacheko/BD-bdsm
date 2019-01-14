package models;

import java.nio.ByteBuffer;

public class Bloco {
	static int id = 0;
	byte[] conteudo;
	int p;

	public Bloco(int tamanho, String header) {
		conteudo = new byte[tamanho];
		p = 0;
		conteudo[p++] = (byte) id++;

		byte[] bytes = ByteBuffer.allocate(4).putInt(tamanho).array();
		int j = bytes.length - 1;
		for (int i = p + 2; j > 0; i--) {
			conteudo[i] = bytes[j--];
			p++;
		}
		conteudo[p++] = 1;
		bytes = ByteBuffer.allocate(4).putInt(0).array();
		// int t = bytesH.length - 1;
		// for (int i = controle + 2; t > 0; i--) {
		// conteudo[i] = bytesH[t--];
		// controle++;
		// }
	}
	int getTamanho(){
		byte[] size = {conteudo[1],conteudo[2],conteudo[3]}
		return Integer
	}
}
