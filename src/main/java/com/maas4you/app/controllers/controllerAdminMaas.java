package com.maas4you.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.maas4you.app.model.dtoViaggioConUsername;
import com.maas4you.app.model.viaggio;
import com.maas4you.app.repository.viaggioRepo;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controllerAdminMaas {
    
    private ModelAndView mv;


    @Autowired
    private viaggioRepo viaggioRepo;

    public controllerAdminMaas(){
        mv = new ModelAndView();
    }

    @GetMapping(value="/adminhome")
    public ModelAndView getAdminHomeMaas() {
        mv.setViewName("adminhome");
        return mv;
    }
    
    @GetMapping(value="/listaViaggiAdmin")
    public ModelAndView getListaViaggiAdminMaas() {
        return componiListaViaggi(false);     
    }
  
    private ModelAndView componiListaViaggi(boolean errore){
        List<viaggio> allviaggi = viaggioRepo.findAll();
        List<dtoViaggioConUsername> allviaggiousername = new ArrayList<>();
        Integer contatoreViaggi = 0;

        for (viaggio viaggio : allviaggi) {
            dtoViaggioConUsername dto = new dtoViaggioConUsername(viaggio.getId(), 
                                                                    Encode.forHtml(viaggio.getTratta()),
                                                                    Encode.forHtml(viaggio.getMezzo()),
                                                                    viaggio.getData(),
                                                                    viaggio.getUsername(),
                                                                    Encode.forHtml(viaggio.getDescription()),
                                                                    viaggio.getCosto());
            allviaggiousername.add(dto);
            contatoreViaggi++;
        }
        mv.addObject("error_modifica_pending",errore);
        mv.addObject("contatore",contatoreViaggi);
        mv.addObject("lista_viaggi", allviaggiousername);
        mv.setViewName("listaViaggiAdmin");
        return mv;  
    }
}