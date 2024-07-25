package com.maas4you.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class dtoModificaViaggioDettaglio {
    private Long id_modifica;
    private String tratta_modifica;
    private LocalDateTime data_modifica;
    private String descrizione_modifica;

    private Long id_viaggio;
    private String tratta_viaggio;
    private LocalDateTime data_viaggio;
    private String descrizione_viaggio;
}
