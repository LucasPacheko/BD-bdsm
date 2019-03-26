package controle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import models.Bloco;

public class Principal {
	public static void main(String[] args) throws Exception {
		Buffer buffer =  new Buffer(6000);
		
		int hit=0;
		int miss=0;
		BufferedReader in =  new BufferedReader(new FileReader("req.txt"));
		String request = null;
		Recap.carregar();
		while((request=in.readLine())!=null) {
			if(buffer.contains(request)){
				hit++;
			}else {
				miss++;
//				Bloco bloc =Recap.search(request);
				Bloco bloc = Recap.EnhancedSearch(request);
				buffer.put(bloc);
			}
		}
		System.out.println("Hit : "+hit+"("+(hit*100/(hit+miss))+"%)");
//		System.out.println("Hit : "+hit);
		System.out.println("Miss : "+miss);
		
	}
}
