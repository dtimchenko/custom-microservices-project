package com.example.fraud;

import com.example.commons.protobuf.fraud.FraudServiceGrpc;
import com.example.commons.protobuf.fraud.FraudUserCreationRequest;
import com.example.commons.protobuf.fraud.FraudUserCreationResponse;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class FraudGrpcService extends FraudServiceGrpc.FraudServiceImplBase {

    private final FraudUserRepository fraudUserRepository;

    @Override
    public void addFraud(FraudUserCreationRequest request, StreamObserver<FraudUserCreationResponse> responseObserver) {
        log.info("add fraudster user request received: [{}]", request);

        FraudUser fraudUser = FraudUser.builder()
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        FraudUser savedFraudster = fraudUserRepository.save(fraudUser);

        Instant instantCreateAt = savedFraudster.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant();

        Timestamp create_at = Timestamp.newBuilder()
                .setSeconds(instantCreateAt.getEpochSecond())
                .setNanos(instantCreateAt.getNano()).build();

        responseObserver.onNext(
                com.example.commons.protobuf.fraud.FraudUserCreationResponse
                        .newBuilder()
                        .setId(savedFraudster.getId())
                        .setEmail(savedFraudster.getEmail())
                        .setCreatedAt(create_at)
                        .build()
        );

        responseObserver.onCompleted();
    }
}
