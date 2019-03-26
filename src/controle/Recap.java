package controle;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.sun.corba.se.impl.encoding.ByteBufferWithInfo;
import com.sun.org.apache.bcel.internal.generic.NEW;

import models.Bloco;
import sun.security.action.GetBooleanAction;

public class Recap {
	static ArrayList<Bloco> blocos = new ArrayList<Bloco>();

	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("dados.bin", "rw");
		byte[] b = new byte[11];

		raf.read(b, 0, 11);
		List<String> lista = new ArrayList<String>();
		byte[] s = { b[9], b[10] };// tamanho do descritor (header)
		int sizeControle = ByteBuffer.wrap(s).getShort();
		byte[] blocks = { b[0], b[1], b[2], b[3] };
		int sizeBlocos = ByteBuffer.wrap(blocks).getInt();
		// System.out.println("Size controle: " + sizeControle);
		// System.out.println("Size blocos: " + sizeBlocos);
		int point = sizeControle;
		boolean control = true;
		int off = sizeBlocos;
		int len = off + sizeBlocos;
		while (control) {
			raf = new RandomAccessFile("dados.bin", "rw");
			byte bloc[] = new byte[sizeBlocos];
			raf.seek(off);
			if (raf.read(bloc, 0, sizeBlocos) == -1) {
				break;
			}
			blocos.add(new Bloco(bloc));
			off = off + sizeBlocos;
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter("req.txt"));
		// raf = new RandomAccessFile("req.bin", "rw");
		for (Bloco bloquinho : blocos) {
			for (String[] tupla : bloquinho.toArrayList()) {
				StringBuilder sb = new StringBuilder();
				for (String string : tupla) {
					sb.append(string).append("|");
				}

				// System.out.println(sb);
			}

			for (String string : bloquinho.getOffSets()) {
				// byte[] da = new byte[6];
				// da[0]=bloquinho.getConteudo()[0];
				// da[1]=bloquinho.getConteudo()[1];
				// da[2]=bloquinho.getConteudo()[2];
				// da[3]=bloquinho.getConteudo()[3];
				// byte[] oof =
				// ByteBuffer.allocate(2).putShort((short)Integer.parseInt(string)).array();
				// da[4]=oof[0];
				// da[5]=oof[1];
				// raf.write(blocks);
				// bw.write(bloquinho.getIdConteiner()+"#"+bloquinho.getIdBlocoInt()+"#"+string+"\n");
				lista.add(bloquinho.getIdConteiner() + "#" + bloquinho.getIdBlocoInt() + "#" + string);
			}

		}
		System.out.println("Tamanho da lsita : "+lista.size());
		Collections.shuffle(lista);
		for (String s1 : lista) {
			bw.write(s1 + "\n");
			bw.flush();
		}

	}

	public static void carregar() throws Exception {
		RandomAccessFile raf = new RandomAccessFile("dados.bin", "rw");
		byte[] b = new byte[11];

		raf.read(b, 0, 11);
		List<String> lista = new ArrayList<String>();
		byte[] s = { b[9], b[10] };// tamanho do descritor (header)
		int sizeControle = ByteBuffer.wrap(s).getShort();
		byte[] blocks = { b[0], b[1], b[2], b[3] };
		int sizeBlocos = ByteBuffer.wrap(blocks).getInt();
		System.out.println("Size controle: " + sizeControle);
		System.out.println("Size blocos: " + sizeBlocos);
		int point = sizeControle;
		boolean control = true;
		int off = sizeBlocos;
		int len = off + sizeBlocos;
		while (control) {
			raf = new RandomAccessFile("dados.bin", "rw");
			byte bloc[] = new byte[sizeBlocos];
			raf.seek(off);
			if (raf.read(bloc, 0, sizeBlocos) == -1) {
				break;
			}
			blocos.add(new Bloco(bloc));
			off = off + sizeBlocos;
		}
	}

	public static Bloco EnhancedSearch(String req) {
		String[] rowId = req.split("#");
		int bId = Integer.parseInt(rowId[1]);
		for (Bloco bloco : blocos) {

			if (bloco.getIdBlocoInt() == bId) {
				return bloco;
			}
		}
		return null;
	}

	public static Bloco search(String req) {
		String[] rowId = req.split("#");
		int bId = Integer.parseInt(rowId[1]);
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile("dados.bin", "rw");

			byte[] b = new byte[11];
			ArrayList<Bloco> blocos = new ArrayList<Bloco>();
			raf.read(b, 0, 11);
			List<String> lista = new ArrayList<String>();
			byte[] s = { b[9], b[10] };// tamanho do descritor (header)
			int sizeControle = ByteBuffer.wrap(s).getShort();
			byte[] blocks = { b[0], b[1], b[2], b[3] };
			int sizeBlocos = ByteBuffer.wrap(blocks).getInt();
			int point = sizeControle;
			boolean control = true;
			int off = sizeBlocos;
			int len = off + sizeBlocos;
			while (control) {
				raf = new RandomAccessFile("dados.bin", "rw");
				byte bloc[] = new byte[sizeBlocos];
				raf.seek(off);
				if (raf.read(bloc, 0, sizeBlocos) == -1) {
					break;
				}
				byte[] aux = { 0, bloc[1], bloc[2], bloc[3] };
				int idAux = ByteBuffer.wrap(aux).getInt();
				if (idAux == bId) {
					return new Bloco(bloc);
				}
				off = off + sizeBlocos;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

// PARA GERAR AS REQUISIÇOES
