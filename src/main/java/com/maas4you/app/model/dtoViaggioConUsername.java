package com.maas4you.app.model;

import java.time.LocalDateTime;

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
public class dtoViaggioConUsername {
    private Long id;
    private String tratta;
    private String mezzo;
    private LocalDateTime data;
    private String username;
    private String descrizione;
    private Double costo;
}



