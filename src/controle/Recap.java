package controle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;

import models.Bloco;
import sun.security.action.GetBooleanAction;

public class Recap {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("dados.bin", "rw");
		byte[] b = new byte[11];
		ArrayList<Bloco> blocos =  new ArrayList<Bloco>(); 
				raf.read(b, 0, 11);
		System.out.println();
		byte[] s =  {b[9],b[10]};
		int sizeControle = ByteBuffer.wrap(s).getShort();
		byte[] blocks = {b[0],b[1],b[2],b[3]};
		int sizeBlocos =  ByteBuffer.wrap(blocks).getInt();
		System.out.println("Size controle: "+sizeControle);
		System.out.println("Size blocos: "+sizeBlocos);
		int point = sizeControle;
		boolean passe = true;
		while(passe){
			byte[] cont = new byte[sizeBlocos];
			raf.read(cont, point, point+sizeBlocos);
			blocos.add(new Bloco(cont));
		}
		System.out.println();
		System.out.println(new String(b));
		
	}

}
