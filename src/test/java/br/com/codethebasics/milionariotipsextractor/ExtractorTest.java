package br.com.codethebasics.milionariotipsextractor;


import br.com.codethebasics.milionariotipsextractor.model.Campeonato;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

class ExtractorTest {

    private static String milionariotips_url = "https://www.milionariotips.com.br/Api/view/CampeonatoHorarios/GOLSPAR/[]/[]";
    private static String cookie = "__cfduid=d7419225c226a6a2a4e5edcb29af50a791617020656; __RequestVerificationToken=AggGAI0jfUsSZba3NPWk8P0T72Sfxsj85739CjEj-udLvcEkXP7LbvYE8L1u91AWhdiHlt19jDqjad9GXKj6dxBzg0u3MWyjPbrjnXHyq_k1; .AspNet.ApplicationCookie=KVVDk7XO2iY9L3YXtirmPZVs0mwstI2K9TZ7eEJFcVgziLhpmozE5zNwyyquRpb2PJVzd_g6ya3LWhK8lc8AD-JwTnOljRN5FReO0RXrXBXXFGMP7X1wsN7FVJOA0vIeSAbgL5RigI7CwdpX20tAwf4A3W0tHVPv6uSd_jn9XpToStLF5ht6q4l4AJmVDDz-KpU1_g27p2lsNboOg7aJHoVgCDQXFAN58evsHL2fJkUZvrmYUp4WZexbtAlImi7J9quM3wpwFD5Ki5wAAro4KwVQ3D1mUDiwwqRnWIE9Sm8lz4ckexZtqV9cRHpfQWfBXNP4V93ofSUcVe00PmuUDwOXNWMlRSo40MNjHoceP--rNojkSDigF7SP1lJwPVp2ee9cL_cgDw4Usa_PjE-XR0412Z_naeaT0Az61mQloVfEfFemSDIZ_wkMlL3XVlc4dSDUbxQTYij5Q3BRnJmiVzuZBGniAG7Q8_SQyYJTbug";

    @Test
    public void extractTest() throws Exception {

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
        }
        catch (Exception e) {
            System.out.println("Erro durante o acesso ao site.");
            e.printStackTrace();
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
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss");
            System.out.println("C:\\Users\\55619\\Documents\\jogos-" + now.format(formatter) + ".txt");
            File txt = new File("C:\\Users\\55619\\Documents\\jogos-" + now.format(formatter) + ".txt");
            if (txt.exists()) {
                txt.delete();
            }
            if (!txt.createNewFile()) {
                throw new IOException("Erro ao criar arquivo.");
            }
            FileWriter writer = new FileWriter(txt);
            writer.write(output);
            writer.close();
            System.out.println("Arquivo escrito com sucesso!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}