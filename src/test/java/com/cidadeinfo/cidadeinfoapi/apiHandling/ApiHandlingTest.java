package com.cidadeinfo.cidadeinfoapi.apiHandling;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApiHandlingTest {
    private ApiHandling apiHandling;

    @Before
    public void setUp() {
        apiHandling = new ApiHandling();
    }

    @Test
    public void testGetCidades() throws Exception {
        apiHandling.setUF("SP");
        JSONArray cidades = apiHandling.getCidades();
        assertNotNull(cidades);
        assertEquals(645, cidades.size());
    }

    @Test
    public void testGetEstado() throws Exception {
        apiHandling.setUF("SP");
        JSONObject estado = apiHandling.getEstado();
        assertNotNull(estado);
        assertEquals("São Paulo", estado.get("nome"));
    }

    @Test
    public void testGetEstadoId() throws Exception{
        apiHandling.setUF("sp");
        JSONObject estado = apiHandling.getEstado();
        assertNotNull(estado);
        assertEquals(35L, estado.get("id"));
    }

    @Test(expected = RuntimeException.class)
    public void testApiConnectInvalidURL() throws Exception {
        apiHandling.apiConnect("invalid_url");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUfVerifierBlank() throws Exception {
        apiHandling.setUF("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUfVerifierInvalidLength() throws Exception {
        apiHandling.setUF("ABC");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUfVerifierNumeric() throws Exception {
        apiHandling.setUF("12");
    }

    @Test(expected = Exception.class)
    public void testGetDataIOException() throws Exception {
        apiHandling.setUF("SP");
        apiHandling.getData("invalid_api_url");
    }

    @Test(expected = Exception.class)
    public void testGetArrayParseException() throws Exception {
        apiHandling.setUF("SP");
        apiHandling.getArray("invalid_api_url");
    }

    @Test(expected = Exception.class)
    public void testGetObjectParseException() throws Exception {
        apiHandling.setUF("SP");
        apiHandling.getObject("invalid_api_url");
    }

    @Test(expected = Exception.class)
    public void testGetCidadesEmptyResult() throws Exception {
        apiHandling.setUF("ZZ"); // Uma UF inexistente
        System.out.println(apiHandling.getCidades());
    }

}