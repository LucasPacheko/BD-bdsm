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
		p += 5;
		char[] arrayChar = header.toCharArray();
		bytes = ByteBuffer.allocate(2).putShort((short) arrayChar.length).array();
		for (byte b : bytes) {
			conteudo[p++] = b;
		}
		for (int i = 0; i < arrayChar.length; i++) {
			conteudo[p + i] = (byte) arrayChar[i];
		}
	}

	public static void main(String[] args) {
		Bloco bloc = new Bloco(150, "testando");
		System.out.println(bloc.getTamanho());
		System.out.println(bloc.getHeader());
	}
	public void setProxBlocoLivre(Bloco proxBloco){
		byte[] b = proxBloco.getIdBlocoArray();
		for (int i = 1; i < b.length; i++) {
			conteudo[4+i]=(byte)b[i];
		}
	}
	public int getIdBlocoInt(){
		byte[] b = {(byte)0,conteudo[1],conteudo[2],conteudo[3]};
		return ByteBuffer.wrap(b).getInt();
	}
	byte[] getIdBlocoArray(){
		byte[] b = {(byte)0,conteudo[1],conteudo[2],conteudo[3]};
		return b;
	}
	public int getTamanho() {
		byte[] b = { (byte) 0, conteudo[1], conteudo[2], conteudo[3] };
		final ByteBuffer bb = ByteBuffer.wrap(b);

		return bb.getInt();
	}
	public int getProximoBloco() {
		byte[] b = { conteudo[5], conteudo[6], conteudo[7], conteudo[8] };
		return ByteBuffer.wrap(b).getInt();
	}
	public int getSizeheader() {
		byte[] b = { (byte) 0, (byte) 0, conteudo[9], conteudo[10] };
		int size = ByteBuffer.wrap(b).getInt();
		return size;
	}
	public String getHeader() {
		int size = getSizeheader();
		char[] arrayChar = new char[size];
		for (int i = 0; i < size; i++) {
			arrayChar[i] = (char) conteudo[11 + i];
		}
		String a = new String(arrayChar);
		return a;
	}
	public boolean isFull(){
		if(conteudo[4]!=0){
			return true;
		}
		return false;
	}
	public boolean putData(String[] data) {
		// TODO Auto-generated method stub
		int sizeTupla=0;
		int written= getBytesUsados();
		int sizeBloco = getTamanho();
		for (int i = 0; i < data.length; i++) {
			sizeTupla=data[i].length();
		}
		if(sizeBloco+sizeTupla>sizeBloco)return false;
		written++;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length(); j++) {
				conteudo[written++]=(byte)data[i].charAt(j);
			}
		}
	}
	int getBytesUsados(){
		byte[] b = {(byte)0,conteudo[5],conteudo[6],conteudo[7]};
		return ByteBuffer.wrap(b).getInt();
	}
}
