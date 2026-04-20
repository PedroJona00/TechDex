package org.pokemon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class PokemonInfos {
    private String name;
    private List<String> habilidadesNome;
    private List<String> tipos;
    private String id;
    private List<String> categoria;
    private String foto;

    public String getName() {
        return name;
    }

    public List<String> getHabilidadesNome() {
        return habilidadesNome;
    }

    public List<String> getTipos() {
        return tipos;
    }

    public String getId() {
        return id;
    }

    public List<String> getCategoria() {
        return categoria;
    }

    public String getFoto() {
        return foto;
    }

    public PokemonInfos(String name, List<String> habilidadesNome, List<String> tipos, String id, List<String> categoria, String foto) {
        this.name = name;
        this.habilidadesNome = habilidadesNome;
        this.tipos = tipos;
        this.id = id;
        this.categoria = categoria;
        this.foto = foto;
    }
}
