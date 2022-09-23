package br.com.dbaonline.uintegrator.logs;

import br.com.dbaonline.uintegrator.logs.client.IngestorClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
@RequiredArgsConstructor
public class PulseBeat extends Thread {

    private final IngestorClient.Credentials credentials;

    @Override
    public void run() {
        sendHeartBeat();
    }

    private void sendHeartBeat() {
        try {
            IngestorClient.pulse(credentials);
            sleep(60000);
            sendHeartBeat();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

