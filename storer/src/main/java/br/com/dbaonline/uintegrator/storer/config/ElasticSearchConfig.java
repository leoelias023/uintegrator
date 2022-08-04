package br.com.dbaonline.uintegrator.storer.config;

import lombok.*;

@Data
public class ElasticSearchConfig {

    @Data
    public static class Credentials {
        String username;
        String password;
    }

    /** Basic credentials for login into Elastic */
    Credentials credentials;

    /** Host without protocol */
    String host;

    /** Listening port of ElasticSearch instance */
    Integer port;

    /** Protocol used for communication (HTTP or HTTPS). */
    String scheme;
}

