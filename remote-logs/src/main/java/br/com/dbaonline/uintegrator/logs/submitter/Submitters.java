package br.com.dbaonline.uintegrator.logs.submitter;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import lombok.NonNull;

import java.util.List;

/**
 * A class-based access for propagate logs sending to all submitters.
 *
 * @author Leonardo Elias
 */
public class Submitters implements LogSubmitter {

    private static LogSubmitter httpSubmitter() {
        return new HttpLogSubmitter();
    }

    private static LogSubmitter localSubmitter() { return new LocalLogSubmitter(); }

    private static List<LogSubmitter> submitters() {
        return List.of(httpSubmitter(), localSubmitter());
    }

    @Override
    public void submit(@NonNull String message, @NonNull LogLevel logLevel) {
        submitters().forEach(logSubmitter -> logSubmitter.submit(message, logLevel));
    }
}
