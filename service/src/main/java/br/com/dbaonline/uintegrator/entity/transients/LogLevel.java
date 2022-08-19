package br.com.dbaonline.uintegrator.entity.transients;

import com.diogonunes.jcolor.Attribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogLevel {
    ERROR(Attribute.RED_TEXT()),
    WARNING(Attribute.YELLOW_TEXT()),
    INFO(Attribute.BLUE_TEXT()),
    TRACE(Attribute.BLACK_TEXT()),
    DEBUG(Attribute.BLACK_TEXT());

    @Getter
    private final Attribute textColor;
}
