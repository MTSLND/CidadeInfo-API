package com.cidadeinfo.cidadeinfoapi.fileHandling;

import com.cidadeinfo.cidadeinfoapi.apiHandling.ApiHandling;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileHandling {
    private final ApiHandling apiHandling;

    public FileHandling(ApiHandling apiHandling) {
        this.apiHandling = apiHandling;
    }

    public void createCSV() throws Exception {
        try{
            if (apiHandling.getUF() == null || apiHandling.getUF().isEmpty()) throw new Exception("Erro ao criar o arquivo CSV");
            JSONArray cidades = apiHandling.getCidades();
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(
                            "src\\CSV_Files\\ListaCidades"+apiHandling.getUF().toUpperCase()+".csv"
                    ));

            for(Object obj: cidades){
                JSONObject newObj = (JSONObject) obj;
                String id = newObj.get("id").toString();
                String cidade = newObj.get("nome").toString();
                writer.write(id+", "+cidade+"\n");
            }
            writer.close();

        }catch (IOException e) {
            throw new IOException("Erro ao criar o arquivo CSV: " + e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
