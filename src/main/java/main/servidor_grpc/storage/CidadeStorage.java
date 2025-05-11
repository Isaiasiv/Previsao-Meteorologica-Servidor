package main.servidor_grpc.storage;

import main.servidor_grpc.model.Cidade;

import java.util.*;

public class CidadeStorage {

    private final Set<String> cidades = new HashSet<>();
    private final Map<String, Cidade> cidadeMap = new HashMap<>();
    private final Random random = new Random();

    public void adicionarCidade(String nome) {
        if (!cidades.contains(nome)) {
            cidades.add(nome);
            cidadeMap.put(nome, new Cidade(nome));
        }
    }

    public List<String> getCidades() {
        return new ArrayList<>(cidades);
    }

    public float gerarTemperatura() {
        return 15 + random.nextFloat() * 20; // Temperatura entre 15 e 35
    }

    public float gerarTemperaturaParaCidade(String nome) {
        Cidade cidade = cidadeMap.get(nome);
        if (cidade != null) {
            float temp = gerarTemperatura();
            cidade.adicionarTemperatura(temp);
            return temp;
        }
        return -273.15f; // Temperatura inválida para indicar erro
    }

    public List<Float> gerarPrevisao5DiasParaCidade(String nome) {
        Cidade cidade = cidadeMap.get(nome);
        List<Float> previsao = new ArrayList<>();
        if (cidade != null) {
            for (int i = 0; i < 5; i++) {
                float temp = gerarTemperatura();
                previsao.add(temp);
                cidade.adicionarTemperatura(temp);
            }
        }
        return previsao;
    }

    public Cidade getCidade(String nome) {
        return cidadeMap.get(nome);
    }
}
