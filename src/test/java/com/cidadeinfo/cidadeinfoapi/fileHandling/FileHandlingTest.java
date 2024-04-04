package com.cidadeinfo.cidadeinfoapi.fileHandling;

import com.cidadeinfo.cidadeinfoapi.FileHandling.FileHandling;
import com.cidadeinfo.cidadeinfoapi.apiHandling.ApiHandling;
import org.json.simple.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.*;

public class FileHandlingTest {
    private ApiHandling apiHandling;
    private File file;
    @Before
    public void setUp() throws Exception {
        apiHandling = new ApiHandling();
        FileHandling fileHandling = new FileHandling(apiHandling);
        apiHandling.setUF("SP");
        String UF = apiHandling.getUF();
        fileHandling.createCSV();
        file = new File("src\\CSV_Files\\ListaCidades"+ UF +".csv");
    }

    @Test
    public void CreateCsvSucess() {
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void FormatoCsv() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                assertEquals(2, parts.length);
                assertFalse(parts[0].isEmpty());
                assertFalse(parts[1].isEmpty());
            }
            //Verifica se o ID ou Nome do Municipio estao nulos
    }

    @Test
    public void TituloCsv() throws Exception {
        assertTrue(file.getName().startsWith("ListaCidades"));
    }

    @Test
    public void QuantidadeUfCsv() throws Exception{
        JSONArray cidades = apiHandling.getCidades();
        int count = 0;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while(reader.readLine() !=null) count++;
        assertEquals(cidades.size(), count);
        //Verifica se o Numero de cidades do arquivo é o mesmo do numero de cidades no JSON
    }

}
