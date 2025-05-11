package main.servidor_grpc.service;

import com.exemplo.grpc.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import main.servidor_grpc.model.Cidade;
import main.servidor_grpc.storage.CidadeStorage;
import net.devh.boot.grpc.server.service.GrpcService;
import weather.Weather;
import weather.WeatherServiceGrpc;


import java.util.List;

@GrpcService
public class WeatherServiceImpl extends WeatherServiceGrpc.WeatherServiceImplBase {

    private final CidadeStorage storage = new CidadeStorage();

    @Override
    public void cadastrarCidade(CidadeRequest request, StreamObserver<Weather.MensagemResponse> responseObserver) {
        storage.adicionarCidade(request.getNome());
        Weather.MensagemResponse response = Weather.MensagemResponse.newBuilder()
                .setMensagem("Cidade cadastrada com sucesso: " + request.getNome())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void listarCidades(Empty request, StreamObserver<Weather.CidadeListResponse> responseObserver) {
        Weather.CidadeListResponse response = Weather.CidadeListResponse.newBuilder()
                .addAllCidades(storage.getCidades())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void obterPrevisao5Dias(CidadeRequest request,
                                   StreamObserver<PrevisaoResponse> responseObserver) {
        List<Float> previsoes = storage.gerarPrevisao5DiasParaCidade(request.getNome());
        PrevisaoResponse response = PrevisaoResponse.newBuilder()
                .setCidade(request.getNome())
                .addAllTemperaturas(previsoes)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void obterEstatisticas(CidadeRequest request,
                                  StreamObserver<EstatisticasResponse> responseObserver) {
        Cidade cidade = storage.getCidade(request.getNome());
        if (cidade == null) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription("Cidade não encontrada")
                            .asRuntimeException()
            );
            return;
        }
        EstatisticasResponse response = EstatisticasResponse.newBuilder()
                .setCidade(cidade.getNome())
                .setMedia(cidade.calcularMedia())
                .setMinima(cidade.getMinima())
                .setMaxima(cidade.getMaxima())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void obterTemperaturaAtual(CidadeRequest request, StreamObserver<TemperaturaResponse> responseObserver) {
        float temperatura = storage.gerarTemperaturaAtualParaCidade(request.getNome());

        TemperaturaResponse response = TemperaturaResponse.newBuilder()
                .setCidade(request.getNome())
                .setTemperatura(temperatura)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void obterEstatisticas(CidadeRequest request, StreamObserver<EstatisticasResponse> responseObserver) {
        Cidade cidade = storage.getCidade(request.getNome());
        if (cidade == null) {
            responseObserver.onError(Status.NOT_FOUND.withDescription("Cidade não encontrada").asRuntimeException());
            return;
        }

        EstatisticasResponse response = EstatisticasResponse.newBuilder()
                .setCidade(cidade.getNome())
                .setMedia(cidade.calcularMedia())
                .setMinima(cidade.getMinima())
                .setMaxima(cidade.getMaxima())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
