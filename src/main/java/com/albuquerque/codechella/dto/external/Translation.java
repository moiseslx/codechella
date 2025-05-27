package com.albuquerque.codechella.dto.external;

import java.util.List;

public record Translation(List<Text> translations) {

    public String getText() {
        return translations.getFirst().text();
    }
}
