import java.io.IOException;
import java.util.Scanner;

public class Main {
    //metodo auxiliar para mostrar o resultado da operação
    public static void mostrarResultado(boolean sucesso, ClienteFTP cliente) throws IOException {
        if (sucesso) {
            System.out.println("Operação realizada com sucesso!");
        } else {
            System.out.println("Erro: " + cliente.ultimaResposta());
        }
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ClienteFTP cliente = new ClienteFTP();

        try {
            //pede os dados do servidor
            System.out.println("Digite o host:");
            String host = teclado.nextLine();

            System.out.println("Digite a porta:");
            String portaTexto = teclado.nextLine();
            int porta = Integer.parseInt(portaTexto);

            System.out.println("Digite o usuário:");
            String user = teclado.nextLine();

            System.out.println("Digite a senha:");
            String pass = teclado.nextLine();

            //tenta conectar ao servidor
            boolean conectado = cliente.conectar(host, porta, user, pass);

            if (!conectado) {
                System.out.println("Falha na conexão: " + cliente.ultimaResposta());
                return;
            }
            System.out.println("Conectado!");

            //menu de opções
            int opcao = -1;

            while (opcao != 0) {

                System.out.println("\n--- MENU FTP ---");
                System.out.println("1 - Mostrar diretório atual");
                System.out.println("2 - Mostrar última resposta do servidor");
                System.out.println("3 - Listar arquivos");
                System.out.println("4 - Listar diretórios");
                System.out.println("0 - Sair");
                System.out.println("Escolha uma opção:");

                try {
                    opcao = Integer.parseInt(teclado.nextLine());
                    switch (opcao) {

                        case 1:
                            System.out.println("Diretório atual: " + cliente.diretorioAtual());
                            break;

                        case 2:
                            System.out.println(cliente.ultimaResposta());
                            break;

                        case 3:
                            System.out.println("\n--- ARQUIVOS ---");

                            for (String arquivo : cliente.listarArquivos()) {
                                System.out.println(arquivo);
                            }
                            break;

                        case 4:
                            System.out.println("\n--- DIRETÓRIOS ---");

                            for (String diretorio : cliente.listarDiretorios()) {
                                System.out.println(diretorio);
                            }
                            break;

                        case 0:
                            System.out.println("Encerrando o programa...");
                            break;

                        default:
                            System.out.println("Opção inválida!");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Digite um número válido!");
                }
            }

        } catch (IOException e) {
            System.out.println("Erro na conexão com o servidor: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Erro: a porta deve ser um número inteiro.");

        } finally {

            //tenta encerrar a conexão
            try {
                cliente.desconectar();
            } catch (IOException e) {
                System.out.println("Erro ao desconectar: " + e.getMessage());
            }
            teclado.close();
        }
    }
}
