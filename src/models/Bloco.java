package models;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import com.sun.xml.internal.stream.util.BufferAllocator;

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
		bytes = ByteBuffer.allocate(2).putShort((short) (arrayChar.length + 10)).array();
		for (byte b : bytes) {
			conteudo[p++] = b;
		}
		for (int i = 0; i < arrayChar.length; i++) {
			conteudo[p + i] = (byte) arrayChar[i];
		}
	}

	public Bloco(byte[] cont) {
		conteudo = cont;
	}

	public Bloco(int tamanho) {
		conteudo = new byte[tamanho];//
		conteudo[0] = 0;// id conteiner
		p = 1;

		byte[] bytes = ByteBuffer.allocate(4).putInt(id++).array();
		int j = bytes.length - 1;
		for (int i = p + 2; j > 0; i--) {
			conteudo[i] = bytes[j--];
			p++;
		}
		conteudo[4] = 0; // tipo de bloco

		conteudo[5] = 0;// TD
		conteudo[6] = 0;// tamanho tuple directory

		bytes = ByteBuffer.allocate(2).putShort((short) tamanho).array();

		conteudo[7] = bytes[0];// entederco ultimo byte
		conteudo[8] = bytes[1];

	}

	

	public void setProxBlocoLivre(Bloco proxBloco) {
		byte[] b = proxBloco.getIdBlocoArray();
		for (int i = 0; i < b.length; i++) {
			conteudo[5 + i] = (byte) b[i];
		}
	}

	public int getIdBlocoInt() {
		byte[] b = { (byte) 0, conteudo[1], conteudo[2], conteudo[3] };
		return ByteBuffer.wrap(b).getInt();
	}

	byte[] getIdBlocoArray() {
		byte[] b = { (byte) 0, conteudo[1], conteudo[2], conteudo[3] };
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

	public boolean isFull() {
		if (conteudo[4] != 0) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		String a = "4195|Supplier#000004195|NHCu,qYwb21TrXtL8iXEI4QZ|11|21-437-493-6911|s. furiously special requests are. ironically regular packages doubt alongside o|";
		String data[] = a.split("\\|");
		Bloco bloc =  new Bloco(4092);
		bloc.putData(data);
		bloc.getConteudo();
		ArrayList<String[]> list =bloc.toArrayList();
		for (int i = 0; i < list.size(); i++) {
			String[] str = list.get(i);
			for (int j = 0; j < str.length; j++) {
				System.out.println(str[j]);	
			}
			
		}
		System.out.println();
	}
	public boolean putData(String[] data) {
		// TODO Auto-generated method stub
		int sizeTupla = 0;
		int sizeTD = getSizeTupleDirectory();
		int endByte = getUltimoByteTuplaUsado();
		int sizeBloco = conteudo.length;
		for (int i = 0; i < data.length; i++) {
			sizeTupla = data[i].length() + 2;// +2 para o tamanho da variavel
		}
		sizeTupla += (data.length * 2) + 4;
		if (9 + sizeTD + 2 > endByte - sizeTupla)
			return false;
		int p = endByte - sizeTupla;
		// marca o edereço  dentro do bloco
		byte[] tup = ByteBuffer.allocate(2).putShort((short) p).array();
		for (byte b : tup) {
			conteudo[9 + sizeTD++] = b;
		}
		tup = ByteBuffer.allocate(4).putInt(p).array();
		for (byte b : tup) {
			conteudo[p++] = b;
		}
		for (String s : data) {
			int tam = s.getBytes().length;
			tup = ByteBuffer.allocate(2).putShort((short) tam).array();
			for (byte b : tup) {
				conteudo[p++] = b;
			}
			tup = s.getBytes();
			for (byte b : tup) {
				conteudo[p++]=b;
			}
		}
		return true;
	}

	private int getUltimoByteTuplaUsado() {
		byte[] b = { conteudo[7], conteudo[8] };
		return (int) ByteBuffer.wrap(b).getShort();
	}

	public byte[] getConteudo() {
		// TODO Auto-generated method stub

		return conteudo;
	}
	public ArrayList<String[]> toArrayList(){
		ArrayList<String[]> list =  new ArrayList<String[]>();
		byte[] b = {conteudo[5],conteudo[6]};
		int tamTD = ByteBuffer.wrap(b).getInt();
		for (int i = 9; i < tamTD; i+=2) {
			ArrayList<String> strings = new ArrayList<String>();

			byte[] a = {conteudo[i],conteudo[i+1]};
		
			int end = (int)ByteBuffer.wrap(a).getShort();
			byte[] c ={conteudo[end],conteudo[end+1],conteudo[end+2],conteudo[end+3]};
			int tamanhoTupla = ByteBuffer.wrap(c).getInt();
			for (int j = end+4; j < tamanhoTupla;) {
				byte[] d = {conteudo[j++],conteudo[j++]};
				int x =(short)ByteBuffer.wrap(d).getShort();
				byte dado[] = new byte[x];
				for (int k = 0; k < dado.length; k++) {
					dado[k]=conteudo[j++];
				}
				String s =  new String(dado);
				strings.add(s);
			}
			String[] stringArray =  new String[strings.size()];
			for (int j = 0; j < stringArray.length; j++) {
				stringArray[j] = strings.get(j);
			}
			list.add(stringArray);
		}
		return list;
	}

	int getSizeTupleDirectory() {
		byte[] b = { conteudo[5], conteudo[6] };
		return (int) ByteBuffer.wrap(b).getShort() * 2;
	}
}
