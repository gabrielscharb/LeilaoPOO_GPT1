
package leilao.util;

import java.io.*;
import java.util.ArrayList;

public class ArquivoUtil {

    public static <T> void salvarDados(ArrayList<T> dados, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(dados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> ArrayList<T> carregarDados(String nomeArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (ArrayList<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
