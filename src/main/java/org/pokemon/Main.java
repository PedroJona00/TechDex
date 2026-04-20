package org.pokemon;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        JFrame frame = new JFrame("Minha Pokédex");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        JPanel caixaCentral = new JPanel();
        caixaCentral.setLayout(new BoxLayout(caixaCentral, BoxLayout.Y_AXIS));

        String[] vetorDePokemons;

        try {
            System.out.println("Baixando a lista da internet...");
            List<String> escolhas = ApiServico.ChamadoApiPokemonLista();
            vetorDePokemons = escolhas.toArray(new String[0]);
            System.out.println("Lista carregada com sucesso!");
        } catch(Exception err) {
            System.out.println("Erro ao carregar a lista: " + err.getMessage());
            vetorDePokemons = new String[]{"Pikachu"};
        }

        JComboBox<String> escolhaPokemon = new JComboBox<>(vetorDePokemons);
        JButton procurar = new JButton("Buscar Pokémon");
        JLabel labelFoto = new JLabel();
        JLabel labelNome = new JLabel("Nome: ---");
        JLabel labelId = new JLabel("ID: ---");
        JLabel labelTipos = new JLabel("Tipos: ---");
        JLabel labelHabilidades = new JLabel("Habilidades: ---");

        escolhaPokemon.setAlignmentX(Component.CENTER_ALIGNMENT);
        procurar.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelId.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTipos.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelHabilidades.setAlignmentX(Component.CENTER_ALIGNMENT);

        procurar.addActionListener(e -> {
            String pokemonEscolhido = escolhaPokemon.getSelectedItem().toString();

            try {
                PokemonInfos meuPokemon = ApiServico.FormatandoApi(pokemonEscolhido);

                labelNome.setText("Nome: " + meuPokemon.getName().toUpperCase());
                labelId.setText("ID: #" + meuPokemon.getId());
                labelTipos.setText("Tipos: " + meuPokemon.getTipos());
                labelHabilidades.setText("Habilidades: " + meuPokemon.getHabilidadesNome());

                URL urlDaImagem = new URL(meuPokemon.getFoto());
                Image imagemBaixada = ImageIO.read(urlDaImagem);
                Image imagemRedimensionada = imagemBaixada.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                labelFoto.setIcon(new ImageIcon(imagemRedimensionada));

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, "Erro ao buscar: " + erro.getMessage());
            }
        });

        caixaCentral.add(escolhaPokemon);
        caixaCentral.add(Box.createVerticalStrut(10));
        caixaCentral.add(procurar);
        caixaCentral.add(Box.createVerticalStrut(20));
        caixaCentral.add(labelFoto);
        caixaCentral.add(Box.createVerticalStrut(15));
        caixaCentral.add(labelId);
        caixaCentral.add(labelNome);
        caixaCentral.add(labelTipos);
        caixaCentral.add(labelHabilidades);

        frame.add(caixaCentral);
        frame.setVisible(true);
    }
}