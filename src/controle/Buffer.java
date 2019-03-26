package controle;

import java.util.ArrayList;
import java.util.LinkedList;

import models.Bloco;

public class Buffer {
	LinkedList<Bloco> fifoBd;
	int size;
	public Buffer(int tamanho) {
		fifoBd = new LinkedList<Bloco>();
		size = tamanho;
	}

	public boolean put(Bloco bloco) {
		//Se ja estiver no buffer
		for (Bloco b : fifoBd) {
			if(bloco.getIdBlocoInt()==b.getIdBlocoInt()) {
				fifoBd.offerFirst(b);
				fifoBd.remove(fifoBd.lastIndexOf(b));
				return true;
			}
		}
		//se o buffer estiver cheio
		if(fifoBd.size()==size) {
			fifoBd.removeLast();
			fifoBd.addFirst(bloco);
			return false;
		}
		// se o buffer nao estiver cheio
		fifoBd.addFirst(bloco);
		
		return false;
	}
	
	public boolean contains(String request) {
		String[] data =request.split("#");
		int idBloco = Integer.parseInt(data[1]);
		
		for (Bloco bloco : fifoBd) {
			if(bloco.getIdBlocoInt()==idBloco) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String[] a1 = {"Lucas","Renata"};
		String[] a2 = {"Mateus","Nagila"};
		String[] a3 = {"M","xxxxx"};
		String[] a4 = {"Mat","ila"};
		Bloco a = new Bloco(32);
		Bloco b = new Bloco(32);
		Bloco c = new Bloco(32);
		Bloco d =  new Bloco(32);
		a.putData(a1);
		b.putData(a2);
		c.putData(a3);
		d.putData(a4);
		Buffer buf =  new Buffer(2);
		buf.put(a);
		buf.put(b);
		buf.put(a);
		buf.put(d);
		System.out.println();
	}


}
