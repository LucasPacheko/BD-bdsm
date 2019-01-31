package controle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Recap {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf =  new RandomAccessFile("dados.bin", "rw");
		for (int i = 0; i <raf.length(); i++) {
			
		}
	}
}
