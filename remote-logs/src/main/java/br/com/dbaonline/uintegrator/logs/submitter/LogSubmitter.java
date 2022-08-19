package br.com.dbaonline.uintegrator.logs.submitter;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import lombok.NonNull;

public interface LogSubmitter {

    void submit(@NonNull String message, @NonNull LogLevel logLevel);

    /**
     * @return A log submitter with all submitters available.
     */
    static LogSubmitter submitters() {
        return new Submitters();
    }
}
