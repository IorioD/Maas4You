package com.maas4you.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class dtoNuovoViaggio {
    private String tratta;
    private String mezzo;
    private String data;
    private String description;
    private Double costo;
}