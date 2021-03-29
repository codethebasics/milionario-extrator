package br.com.codethebasics.milionariotipsextractor;

import br.com.codethebasics.milionariotipsextractor.model.Campeonato;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class Extractor extends Thread {

    private static String milionariotips_url = "https://www.milionariotips.com.br/Api/view/CampeonatoHorarios/GOLSPAR/[]/[]";
    private String cookie;
    private String outputDir;

    public Extractor(String outputDir, String cookie) {
        this.outputDir = outputDir;
        this.cookie = cookie;
    }

    @Override
    public void run() {
        while (true) {
            try {
                URL urlObject = new URL(milionariotips_url);
                HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                connection.setRequestProperty("cookie", cookie);

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {

                    String inputLine;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((inputLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(inputLine + "\n");
                    }
                    writeFile(parseToJson(stringBuilder.toString()));
                    Thread.sleep(60000);
                }
                catch (Exception e) {
                    System.out.println("Erro durante o acesso ao site.");
                    e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String parseToJson(String jsonData) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        Campeonato[] campeonatos = objectMapper.readValue(jsonData, Campeonato[].class);
        Arrays.stream(campeonatos).forEach(c -> {
            sb.append(c.getDescricao() + "\n");
            Arrays.stream(c.getLines()).forEach(l -> {
                Arrays.stream(l.getJogos()).forEach(j -> {
                    sb.append(j.getResult() == null ? "" : j.getResult() + ";");
                });
                sb.append("\n");
            });
        });
        return sb.toString();
    }

    private void writeFile(String output) {
        try {
            File txt = new File(outputDir + "\\jogos.txt");
            if (txt.exists()) {
                txt.delete();
            }
            if (!txt.createNewFile()) {
                throw new IOException("Erro ao criar arquivo.");
            }
            FileWriter writer = new FileWriter(txt);
            writer.write(output);
            writer.close();
            System.out.println("Arquivo escrito com sucesso! " + outputDir);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
