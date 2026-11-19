import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado= new Scanner(System.in);
        ClienteFTP cliente = new ClienteFTP();

        try {
            //pede os dados do servidor
            System.out.println("Digite o host:");
            String host=teclado.nextLine();

            System.out.println("Digite a porta:");
            String portaTexto= teclado.nextLine();
            int porta =Integer.parseInt(portaTexto);

            System.out.println("Digite o usuario:");
            String user= teclado.nextLine();

            System.out.println("Digite a senha:");
            String pass= teclado.nextLine();

            boolean conectado = cliente.conectar(host, porta, user, pass); //tenta conectar ao servidor

            if (!conectado) {
                System.out.println("Falha na conexão: " +cliente.ultimaResposta());
                return;
            }

            System.out.println("Conectado!");
            System.out.println("Diretóriio atual: " +cliente.diretorioAtual());

        } catch (IOException e) {
            System.out.println("Erro na conexão com o servidor: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Erro: a porta deve ser um número inteiro");

        } finally {
            //tenta encerrar a conexão
            try {
                cliente.desconectar();
            } catch (IOException e) {
                System.out.println("Erro ao desconectar:" + e.getMessage());
            }
            teclado.close();
        }
    }
}
