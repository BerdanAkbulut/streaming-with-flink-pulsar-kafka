package com.berdanakbulut.services.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClickEvent {
    private String id;
    private String href;
    private String tag;
    private String text;
}
