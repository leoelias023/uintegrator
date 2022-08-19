package br.com.dbaonline.uintegrator.logs.submitter;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import com.diogonunes.jcolor.Ansi;
import lombok.NonNull;

/**
 * Log submitter responsible for local logging in console.
 *
 * @author Leonardo Elias
 */
public class LocalLogSubmitter implements LogSubmitter {
    @Override
    public void submit(@NonNull String message, @NonNull LogLevel logLevel) {
        System.out.println(Ansi.colorize(
                String.format(
                        "[ %s ] - %s", logLevel.name(), message
                )
                , logLevel.getTextColor()));
    }
}
