package com.cidadeinfo.cidadeinfoapi.apiHandling;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class ApiHandling {

    private String UF;
    public String getUF() {
        return UF;
    }

    public void setUF(String UF) throws Exception {
        try {
            this.UF = UF.toUpperCase();
            ufVerifier();
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Metodo que faz a conexão com a API
    protected URL apiConnect(String urlString) throws Exception {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200)
                throw new RuntimeException("HttpResponseCode: " + conn.getResponseCode());

            return url;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //Faz as Verificaçoes Basicas da Formataçao da UF
    public void ufVerifier() throws Exception {
        try {
            if(UF.isBlank() || UF.length()!= 2 || UF.matches("\\d\\d")){
                UF = null;
                throw new IllegalArgumentException("Valor de UF Invalido");
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Coleta os dado da UF digitada
    protected StringBuilder getData(String apiUrl) throws Exception {
        try {

            URL url = apiConnect(apiUrl);

            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();
            if (informationString.length() < 3) throw new Exception("Erro na busca dos dados");
            return informationString;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Transforma os dados em um array JSON
    protected JSONArray getArray(String apiUrl) throws Exception{
        try{
            StringBuilder informationString = getData(apiUrl);
            JSONParser parse = new JSONParser();
            return (JSONArray) parse.parse(String.valueOf(informationString));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Transforma os dados em um objeto JSON
    protected JSONObject getObject(String apiUrl) throws Exception {
        try{
            StringBuilder informationString = getData(apiUrl);
            JSONParser parse = new JSONParser();
            return (JSONObject) parse.parse(String.valueOf(informationString));
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //Trata os dados da API
    public JSONArray getCidades() throws Exception {
        try {
            String apiUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+UF+"/municipios";
            JSONArray data = getArray(apiUrl);
            JSONArray cidades = new JSONArray();
            //Retira do Json Bruto somente os dados das cidades e os coloca dentro de outro Json
            for(Object obj : data){
                JSONObject tempJson = new JSONObject();
                JSONObject ComplJson = (JSONObject) obj;

                tempJson.put("nome", ComplJson.get("nome"));
                tempJson.put("id", ComplJson.get("id"));
                cidades.add(tempJson);
            }

            return cidades;
        }catch (Exception e){
           throw new Exception(e.getMessage());
        }
    }

    public JSONObject getEstado() throws Exception{
        String apiUrl = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+UF;
        JSONObject data = getObject(apiUrl);
            JSONObject estados = new JSONObject();

            estados.put("nome", data.get("nome"));
            estados.put("sigla", data.get("sigla"));
            estados.put("id", data.get("id"));
        return estados;
    }

}
