package com.unsa.etf.OrderService.logging;

import com.unsa.etf.SystemEventsService.LogRequest;
import com.unsa.etf.SystemEventsService.LoggingServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrpcSystemEvents {
    @GrpcClient("SystemEventsService")
    LoggingServiceGrpc.LoggingServiceBlockingStub client;

    public void logEvent(String microserviceName, String username, String actionType, String resourceName, String responseType){
        try{
            var request = LogRequest.newBuilder()
                    .setMicroserviceName(microserviceName)
                    .setActionType(actionType)
                    .setUsername(username)
                    .setResponseType(responseType)
                    .setResourceName(resourceName)
                    .build();

            var response = client.loggingService(request);
            System.out.println("Logged event");
        }catch (Exception e){
            System.out.println("An error has occurred: " + e.getMessage());
        }

    }
}
