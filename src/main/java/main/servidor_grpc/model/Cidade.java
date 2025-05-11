package main.servidor_grpc.model;

import java.util.ArrayList;
import java.util.List;

public class Cidade {

    private String nome;
    private List<Float> historicoTemperaturas;

    public Cidade(String nome) {
        this.nome = nome;
        this.historicoTemperaturas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarTemperatura(float temperatura) {
        historicoTemperaturas.add(temperatura);
    }

    public List<Float> getHistoricoTemperaturas() {
        return historicoTemperaturas;
    }

    public float calcularMedia() {
        if (historicoTemperaturas.isEmpty()) return 0;
        float soma = 0;
        for (float temp : historicoTemperaturas) {
            soma += temp;
        }
        return soma / historicoTemperaturas.size();
    }

    public float getMinima() {
        return historicoTemperaturas.stream().min(Float::compare).orElse(0f);
    }

    public float getMaxima() {
        return historicoTemperaturas.stream().max(Float::compare).orElse(0f);
    }
}

