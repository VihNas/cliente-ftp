import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

public class ClienteFTP {
    private final FTPClient ftp = new FTPClient(); // objeto responsável pela conexão ftp

    public boolean conectar(String host, int porta, String user, String pass) throws IOException {
        ftp.connect(host, porta);
        int resposta = ftp.getReplyCode(); // verifica se o servidor aceitou a conexão

        if (!FTPReply.isPositiveCompletion(resposta)) {
            ftp.disconnect();
            return false;
        }

        boolean login = ftp.login(user, pass); // faz o login no servidor
        if (!login) {
            ftp.disconnect();
            return false;
        }
        // configura o modo passivo e o tipo de arquivo
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        return true;
    }

    public void desconectar() throws IOException {
        if (ftp.isConnected()) {
            ftp.logout();
            ftp.disconnect();
        }
    }

    public String diretorioAtual() throws IOException {
        return ftp.printWorkingDirectory();
    }

    public String ultimaResposta() {
        return ftp.getReplyString();
    }


    //lista somente os arquivos do diretório atual
    public List<String> listarArquivos() throws IOException {
        FTPFile[] arquivos = ftp.listFiles();
        List<String> lista = new ArrayList<>();

        for (FTPFile arquivo : arquivos) {
            if (arquivo.isFile()) {
                lista.add(arquivo.getName()+ " - " +arquivo.getSize()+ " bytes");
            }
        }
        return lista;
    }

    //lista somente os diretorios do diretorio atual
    public List<String> listarDiretorios() throws IOException {
        FTPFile[] arquivos=ftp.listFiles();
        List<String> lista= new ArrayList<>();

        for (FTPFile arquivo : arquivos) {
            if (arquivo.isDirectory()) {
                lista.add(arquivo.getName());
            }
        }
        return lista;
    }

    public boolean entrarDiretorio(String nomePasta) throws IOException {
        return ftp.changeWorkingDirectory(nomePasta);
    }

    public boolean voltarDiretorio() throws IOException {
        return ftp.changeToParentDirectory();
    }

    public boolean criarDiretorio(String nomePasta) throws IOException {
        return ftp.makeDirectory(nomePasta);
    }

    public boolean renomearDiretorio(String nomeAtual, String novoNome) throws IOException {
        return ftp.rename(nomeAtual, novoNome);
    }

    public boolean removerDiretorio(String nomePasta) throws IOException {
        return ftp.removeDirectory(nomePasta);
    }

}
