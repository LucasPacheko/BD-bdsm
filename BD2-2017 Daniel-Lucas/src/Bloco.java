
public  class Bloco{
	final int size = 4096;
	static int contID=0;
	int ID;
	public byte[] conteudo;
	public Bloco(int tipo) {
		ID=contID++;
		conteudo= new byte[size];
		conteudo[0]=Byte.parseByte(ID+"");
		
		// TODO Auto-generated constructor stub
	
	}
	
}