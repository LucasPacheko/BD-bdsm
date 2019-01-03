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
	static RandomAccessFile in;
	public static void main(String[] args) throws IOException {
		ArrayList<Bloco> arqDados = new ArrayList<Bloco>();
		RandomAccessFile in = new RandomAccessFile("dados", "r");

		byte[] bytes = new byte[4096];

		in.read(bytes, 0, 4096);

		Bloco blocoControle = new Bloco();
		// (0000*0000)*(0001*0000)*(0000*0000)
		blocoControle.conteudo[1] = (byte) 0;
		blocoControle.conteudo[2] = (byte) 16;
		blocoControle.conteudo[3] = (byte) 0;
		blocoControle.conteudo[4] = (byte) 0;
		/*
		 * ID do proximo bloco livre 
		 * blocoControle.conteudo[5]=
		 * blocoControle.conteudo[6]=
		 * blocoControle.conteudo[7]=
		 * blocoControle.conteudo[8]=
		 */
		/*
		 * tamanho do HEADER
		blocoControle.conteudo[9]=
		blocoControle.conteudo[10=
		 */
		int cochete=0;
		for (int i = 0; i < bytes.length; i++) {
			if(bytes[i]==(byte)133){
				cochete=i;
				break;
			}
			
		}
		ArrayList<Byte> c=separarMetaDadoColuna();
		String metaTado="";
		for (Byte b : c) {
			metaTado=metaTado+b.toString();
		}
		//NOME[0][1][2][3][4][5][6][7][8][9] | tipo [10] |tamanho [11]
		//bytes[cochete]
		
		System.out.println();

	}
	static private ArrayList<Byte> separarMetaDadoColuna() throws IOException {
		ArrayList<Byte> coluna= new ArrayList<Byte>();
		byte c;
		boolean cont=true;
		while(cont){
			c=in.readByte();
			if(c!=124){
				coluna.add(c);
			}else{
				cont=false;
			}
			
		}
		return coluna;
	}
}
