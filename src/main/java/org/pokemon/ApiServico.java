package org.pokemon;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiServico {
    public static String ChamadoApiPokemon(String pokemonEscolhido) throws IOException, InterruptedException {
        var cliente = HttpClient.newHttpClient();
        var requisicao = HttpRequest.newBuilder(URI.create("https://pokeapi.co/api/v2/pokemon/" + pokemonEscolhido)).build();

        var response = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
        var api = response.body();
        return api;
    }
    public static String ChamadoApiPokemonSpecies(String pokemonEscolhido) throws IOException, InterruptedException {
        var cliente = HttpClient.newHttpClient();
        var requisicao = HttpRequest.newBuilder(URI.create("https://pokeapi.co/api/v2/pokemon-species/" + pokemonEscolhido)).build();

        var response = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
        var api = response.body();
        return api;
    }

    public static List<String> ChamadoApiPokemonLista() throws IOException, InterruptedException {
        var cliente = HttpClient.newHttpClient();
        var requisicao = HttpRequest.newBuilder(URI.create("https://pokeapi.co/api/v2/pokemon-species?limit=1100")).build();

        var response = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
        var api = response.body();
        Gson gson = new Gson();
        JsonObject jsonPokemonListe = gson.fromJson(api, JsonObject.class);
        JsonArray results = jsonPokemonListe.getAsJsonArray("results");

        List<String> escolhas = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject item = results.get(i).getAsJsonObject();

            String escolha = item.get("name").getAsString();

            String escolhaFormatada = escolha.substring(0, 1).toUpperCase() + escolha.substring(1);

            escolhas.add(escolhaFormatada);
        }
        return escolhas;
    }

    public static PokemonInfos FormatandoApi(String pokemonEscolhido) throws IOException, InterruptedException {
        Gson gson = new Gson();
        String pokemonMinusculo = pokemonEscolhido.toLowerCase();
        JsonObject apiSpecies = gson.fromJson(ChamadoApiPokemonSpecies(pokemonEscolhido), JsonObject.class);
        JsonArray variaties = apiSpecies.getAsJsonArray("varieties");
        String nomeFormaDefault = pokemonMinusculo;
        for (int i = 0;i < variaties.size(); i++){
            JsonObject item = variaties.get(i).getAsJsonObject();
            boolean isDefault = item.get("is_default").getAsBoolean();

            if (isDefault){
                JsonObject pokemonDaLista = item.getAsJsonObject("pokemon");
                nomeFormaDefault = pokemonDaLista.get("name").getAsString();
                break;
            }
        }

        JsonObject jsonPokemon = gson.fromJson(ChamadoApiPokemon(nomeFormaDefault), JsonObject.class);
        JsonObject jsonPokemonSpecies = gson.fromJson(ChamadoApiPokemonSpecies(pokemonEscolhido), JsonObject.class);
        JsonArray genera = jsonPokemonSpecies.getAsJsonArray("genera");
        JsonArray habilidadesBruto = jsonPokemon.getAsJsonArray("abilities");
        JsonArray types = jsonPokemon.getAsJsonArray("types");
        String foto = jsonPokemon.getAsJsonObject("sprites")
                                        .getAsJsonObject("other")
                                        .getAsJsonObject("official-artwork")
                                        .get("front_default").getAsString();
        String nome = jsonPokemon.get("name").getAsString();
        String id = jsonPokemon.get("id").getAsString();



        List<String> tipos = new ArrayList<>();

        for (int i = 0; i < types.size(); i++) {
            JsonObject item = types.get(i).getAsJsonObject();

            JsonObject type = item.getAsJsonObject("type");

            String tipo = type.get("name").getAsString();

            tipos.add(tipo);
        }

        List<String> categoria = new ArrayList<>();

        for (int i = 0; i < genera.size(); i++) {
            JsonObject item = genera.get(i).getAsJsonObject();

            String idioma = item.getAsJsonObject("language").get("name").getAsString();

            if (idioma.equals("en")) {
                String textoCategoria = item.get("genus").getAsString();
                categoria.add(textoCategoria);
            }
        }

        List<String> habilidadesNome = new ArrayList<>();

        for (int i = 0; i < habilidadesBruto.size(); i++) {
            JsonObject item = habilidadesBruto.get(i).getAsJsonObject();

            JsonObject ability = item.getAsJsonObject("ability");

            String nomeHabilidade = ability.get("name").getAsString();
            habilidadesNome.add(nomeHabilidade);
        }
        return new PokemonInfos(nome, habilidadesNome, habilidadesBruto, tipos, id, categoria, foto);
    }
}
