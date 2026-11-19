import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

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
}
