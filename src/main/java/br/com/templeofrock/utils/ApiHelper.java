package br.com.templeofrock.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class ApiHelper {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public static String getBandIdByName(String name) {
        if (name == null) return null;

        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String url = "http://localhost:9090/api/bands/search?name=" + encodedName;

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(10))
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

                int status = response.statusCode();
                String body = response.body();

                System.out.println("[ApiHelper] attempt " + attempt + " - GET " + url + " -> status: " + status);
                // só para debug rápido (pode ser removido depois)
                System.out.println("[ApiHelper] body: " + (body == null ? "null" : (body.length() > 1000 ? body.substring(0,1000) + "..." : body)));

                if (status != 200 || body == null || body.isBlank()) {
                    // aguarda e tenta novamente
                    Thread.sleep(800);
                    continue;
                }

                JsonElement element = JsonParser.parseString(body);

                // Se a API retornar lista:
                if (element.isJsonArray()) {
                    JsonArray array = element.getAsJsonArray();
                    if (array.size() > 0) {
                        JsonObject bandObj = array.get(0).getAsJsonObject();
                        if (bandObj.has("_id")) {
                            return bandObj.get("_id").getAsString();
                        }
                    }
                }

                // Se a API retornar um único objeto:
                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    if (obj.has("_id")) {
                        return obj.get("_id").getAsString();
                    }
                }

                // se chegou aqui, resposta não tem _id
                return null;

            } catch (Exception e) {
                System.out.println("[ApiHelper] attempt " + attempt + " failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                try { Thread.sleep(800); } catch (InterruptedException ignored) {}
            }
        }

        return null;
    }
}
