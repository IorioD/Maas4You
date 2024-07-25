package com.maas4you.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class dtoModificaViaggio {
    private Long idvecchioViaggio;
    private String nuovaTratta;
    private String nuovoMezzo;
    private String nuovaData;
    private String nuovaDescrizione;
    private Double nuovoCosto;
}

