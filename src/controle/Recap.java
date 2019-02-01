package controle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;

import sun.security.action.GetBooleanAction;

public class Recap {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("dados.bin", "rw");
		byte[] b = new byte[11];

		raf.read(b, 0, 11);
		System.out.println();
		byte[] s =  {b[9],b[10]};
		int sizeControle = ByteBuffer.wrap(s).getShort();
		b =  new byte[106];
		raf.read(b,0,106);
		for (int i = 11; i < sizeControle; i++) {
		}
		System.out.println(new String(b));
		getBloco(raf,106);
	}
	public static byte[] getBloco(RandomAccessFile raf , int pointer) throws IOException{
		byte[] b = new byte[8];
		raf.read(b, 0, 8);
		System.out.println();
		return null ;
	}
}
