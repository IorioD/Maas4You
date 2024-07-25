package com.maas4you.app.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class modificaViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="viaggio_id",referencedColumnName = "id")
    private viaggio vecchioViaggio;

    private String nuovaTratta;
    private String nuovoMezzo;
    private LocalDateTime nuovaData;
    private String nuovaDescrizione;
    private Double nuovoCosto;

    public modificaViaggio(viaggio vecchioViaggio,String nuovaTratta, String nuovoMezzo, LocalDateTime nuovaData, String nuovaDescrizione, Double nuovoCosto){
        this.vecchioViaggio = vecchioViaggio;
        this.nuovaTratta = nuovaTratta;
        this.nuovoMezzo = nuovoMezzo;
        this.nuovaData = nuovaData;
        this.nuovaDescrizione=nuovaDescrizione;
        this.nuovoCosto=nuovoCosto;
    }

    public String print(){
        String TotaleMod = null;
        String MezzoMod = null;
        String DataMod = null; 
        String DescMod = null;

        if(nuovaTratta == vecchioViaggio.getTratta()){
            TotaleMod="";
        }else{
            TotaleMod= "| Modifica Tratta: " + nuovaTratta;
        }

        if(nuovoMezzo == vecchioViaggio.getMezzo()){
            MezzoMod="";
        }else{
            MezzoMod= "| Modifica Mezzo di Trasporto: " + nuovoMezzo;
        }

        if(nuovaData.isEqual(vecchioViaggio.getData())){
            DataMod="";
        }else{
            DataMod=  "| Modifica Data: " +nuovaData.toString();
        }

        if(nuovaDescrizione.compareTo(vecchioViaggio.getDescription())==0){
            DescMod ="";
        }else{
            DescMod = "| Modifica Descrizione: " +nuovaDescrizione;
        }
        
        return "INSERITA NUOVA MODIFICA = USERNAME : " + vecchioViaggio.getUsername() +
                "| ID_VIAGGIO : " + vecchioViaggio.getId()  
                + TotaleMod + MezzoMod + DataMod + DescMod;
    }
}