import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class Principal {
public static void main(String[] args) throws IOException {
	ArrayList<Bloco> arqDados =new ArrayList<Bloco>();
	RandomAccessFile in = new RandomAccessFile("dados", "r");
	
	
	byte[] bytes = new byte[4096];
	
	in.read(bytes, 0, 4096);

	
	
	
	
	System.out.println();
	
}



}
