package com.maas4you.app.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "viaggi")
public class viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="username",nullable = false)
    private String username;

    @Column(name="tratta",nullable = false)
    private String tratta;

    @Column(name="mezzo",nullable = false)
    private String mezzo;

    @Column(name="data",nullable = false)
    private LocalDateTime data;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="costo",nullable = false)
    private Double costo;

    public viaggio(String  username, String tratta, String mezzo, String description, double costo, int giorno, int mese, int anno, int ora, int minuti){
        LocalDate date = LocalDate.of(anno, mese, giorno);
        LocalTime time = LocalTime.of(ora, minuti);
        this.data = LocalDateTime.of(date, time);
        this.username=username;
        this.tratta=tratta;
        this.mezzo=mezzo;
        this.description=description;
        this.costo=costo;
    }

    public viaggio(String  username, String tratta, String mezzo, String description, double costo, LocalDateTime data){
        this.data=data;
        this.username=username;
        this.tratta=tratta;
        this.mezzo=mezzo;
        this.description=description;
        this.costo=costo;
    }

    public String print(){

        return "NUOVO VIAGGIO AGGIUNTO = Utente : " + username + " | Tratta : " + tratta + " | Mezzo di trasporto : " + mezzo +
                                                                  "| Data : " + data.toString() + "| Descrizione : " + description;
    }

}