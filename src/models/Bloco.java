package models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import sun.security.jca.GetInstance;

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
		p+=7;
		char[] arrayChar =  header.toCharArray();
		for (int i = 0; i < arrayChar.length; i++) {
			conteudo[p+i]=(byte)arrayChar[i];
		}
	}

	public static void main(String[] args) {
		Bloco bloc = new Bloco(150, "testando");
		System.out.println(bloc.getTamanho());
		System.out.println(bloc.getHeader());
	}

	int getTamanho() {
		byte[] b = { (byte) 0, conteudo[1], conteudo[2], conteudo[3] };
		final ByteBuffer bb = ByteBuffer.wrap(b);

		return bb.getInt();
	}

	int getProximoBloco() {
		byte[] b = { conteudo[5], conteudo[6], conteudo[7], conteudo[8] };
		return ByteBuffer.wrap(b).getInt();
	}

	String getHeader() {
		byte[] b = { (byte) 0, (byte) 0, conteudo[9], conteudo[10] };
		int size = ByteBuffer.wrap(b).getInt();
		char[] arrayChar =  new char[size];
		for (int i=0 ; i < 11+size; i++) {
			arrayChar[i]=(char)conteudo[11+i];
		}
		return String.copyValueOf(arrayChar);
	}
}
