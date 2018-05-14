package ChatCliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	private String host;
	private int porta;

	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public void executa() throws UnknownHostException, IOException {
		try(Socket cliente = new Socket(this.host, this.porta); 
				// lê msgs do teclado e manda pro servidor
				Scanner teclado = new Scanner(System.in); 
				PrintStream saida = new PrintStream(cliente.getOutputStream())) {
			System.out.println("O cliente se conectou ao servidor!");
			// thread para receber mensagens do servidor
			TrocaMensagem r = new TrocaMensagem(cliente.getInputStream());
			new Thread(r).start();
	
			while (teclado.hasNextLine()) {
				saida.println(teclado.nextLine());
			}
		}
	}
}