package br.com.dbaonline.uintegrator.logs.submitter;

import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import com.diogonunes.jcolor.Ansi;
import lombok.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Log submitter responsible for local logging in console.
 *
 * @author Leonardo Elias
 */
public class LocalLogSubmitter implements LogSubmitter {

    private final DateFormat dateFormat = new SimpleDateFormat();

    @Override
    public void submit(@NonNull String message, @NonNull LogLevel logLevel) {
        System.out.println(Ansi.colorize(
                String.format(
                        "%s [ %s ] - %s", dateFormat.format(new Date()), logLevel.name(), message
                )
                , logLevel.getTextColor()));
    }
}
