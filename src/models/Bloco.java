package models;

import java.nio.ByteBuffer;

public class Bloco {
	static int id = 0;
	byte[] conteudo;
	int p;

	public static void main(String[] args) {
		Bloco teste = new Bloco(16000);
	}

	public Bloco(int tamanho) {
		conteudo = new byte[tamanho];
		p = 0;
		conteudo[p] = (byte) id++;

		byte[] bytes = ByteBuffer.allocate(4).putInt(tamanho).array();
		int j = bytes.length - 1;
		for (int i = p + 2; j > 0; i--) {
			conteudo[i] = bytes[j--];
			p++;
		}
		// conteudo[controle++]=1;
		// byte[] bytesH = ByteBuffer.allocate(4).putInt(controle).array();
		// int t = bytesH.length - 1;
		// for (int i = controle + 2; t > 0; i--) {
		// conteudo[i] = bytesH[t--];
		// controle++;
		// }
	}
}
