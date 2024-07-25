package com.maas4you.app.controllers;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.maas4you.app.model.dtoModificaViaggio;
import com.maas4you.app.model.dtoNuovoViaggio;
import com.maas4you.app.model.modificaViaggio;
import com.maas4you.app.model.viaggio;
import com.maas4you.app.repository.modificaViaggioRepo;
import com.maas4you.app.repository.viaggioRepo;

import lombok.extern.apachecommons.CommonsLog;

@Controller
@CommonsLog
public class controllerUtenteMaas {

    private ModelAndView mv;

    @Autowired
    private viaggioRepo viaggioRepo;

    @Autowired
    private modificaViaggioRepo modificaViaggioRepo;

    public controllerUtenteMaas() {
        mv = new ModelAndView();
    }

    @GetMapping("/listaViaggi")
    public ModelAndView getListaViaggiMaas( Principal principal) {
        if (principal != null) {
            mv.addObject("error_modifica_pending",false);
            mv.addObject("lista_viaggi", viaggioRepo.findAllByUsername(principal.getName()));
            mv.setViewName("listaViaggi");
            return mv;
        } 
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/formAddViaggio")
    public ModelAndView getFormAddViaggioMaas() {
        dtoNuovoViaggio viaggio = new dtoNuovoViaggio();
        mv.addObject("viaggio", viaggio);
        mv.setViewName("formAddViaggio");
        return mv;
    }

    @PostMapping("/saveViaggio")
    public ModelAndView saveViaggio(Principal principal, @ModelAttribute("viaggio") dtoNuovoViaggio dtoviaggio) {
        if (principal != null) {
            StringTokenizer tokenizer = new StringTokenizer(dtoviaggio.getData(), "T");
            String sdata = tokenizer.nextToken();
            String sora = tokenizer.nextToken();

            String fulldate = sdata + " " + sora;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
            LocalDateTime data = LocalDateTime.parse(fulldate, formatter);

            Double costoV=calcolaCosto(dtoviaggio);

            viaggio viaggio = new viaggio(
                            principal.getName(), 
                            Encode.forHtml(dtoviaggio.getTratta()),
                            Encode.forHtml(dtoviaggio.getMezzo()),
                            Encode.forHtml(dtoviaggio.getDescription()),
                            costoV,
                            data);
            viaggioRepo.save(viaggio);

            log.info("[UTENTE " + principal.getName() + "]" + viaggio.print());
            return getListaViaggiMaas(principal);
        }
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/formModificaViaggio/{id}")
    public ModelAndView getFormRichiestaModificaMaas(Principal principal,@PathVariable("id") Long id_viaggio) {
        if (principal != null){
        viaggio viaggio = viaggioRepo.getReferenceById(id_viaggio);

        if(!modificaViaggioRepo.existsByVecchioViaggio(viaggio)){
            dtoModificaViaggio modificaViaggio= new dtoModificaViaggio(id_viaggio, viaggio.getTratta(), viaggio.getMezzo(), 
                                                                        viaggio.getData().toString(), viaggio.getDescription(), viaggio.getCosto());
            mv.addObject("vecchio", viaggio);
            mv.addObject("nuovo", modificaViaggio);
            mv.setViewName("formModificaViaggio");
            return mv;
        }
            mv.addObject("lista_viaggi", viaggioRepo.findAllByUsername(principal.getName()));
            mv.addObject("error_modifica_pending",true);
            mv.setViewName("listaViaggi");
            return mv;
        }
        mv.setViewName("index");
        return mv;
    }

    @PostMapping("/saveModifica")
    public ModelAndView saveRichiestaModificaMaas(Principal principal, @ModelAttribute("nuovo") dtoModificaViaggio dtoModificaViaggio) {
        if (principal != null) {
           
            StringTokenizer tokenizer = new StringTokenizer(dtoModificaViaggio.getNuovaData(), "T");
            String sdata = tokenizer.nextToken();
            String sora = tokenizer.nextToken();
            String fulldate = sdata + " " + sora;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
            LocalDateTime data = LocalDateTime.parse(fulldate, formatter);

            viaggio vecchioViaggio = viaggioRepo.getReferenceById(dtoModificaViaggio.getIdvecchioViaggio());

            modificaViaggio modificaViaggio = new modificaViaggio(vecchioViaggio,
                                                                    Encode.forHtml(dtoModificaViaggio.getNuovaTratta()),
                                                                    Encode.forHtml(dtoModificaViaggio.getNuovoMezzo()),
                                                                    data,
                                                                    Encode.forHtml(dtoModificaViaggio.getNuovaDescrizione()),
                                                                    riCalcolaCosto(dtoModificaViaggio));

            viaggio viaggio_aggiornato = new viaggio(
                                            modificaViaggio.getVecchioViaggio().getId(), 
                                            modificaViaggio.getVecchioViaggio().getUsername(), 
                                            modificaViaggio.getNuovaTratta(),
                                            modificaViaggio.getNuovoMezzo(),
                                            modificaViaggio.getNuovaData(),
                                            modificaViaggio.getNuovaDescrizione(),
                                            modificaViaggio.getNuovoCosto());

            modificaViaggioRepo.delete(modificaViaggio);
            viaggioRepo.delete(modificaViaggio.getVecchioViaggio());
            viaggioRepo.save(viaggio_aggiornato);

            log.info("[UTENTE " + principal.getName() + "]" + modificaViaggio.print());

            return getListaViaggiMaas(principal);

        }
        mv.setViewName("index");
        return mv;
    }

    @PostMapping(value="cancellaViaggio/{id}")
    public ModelAndView rimuoviViaggio(@PathVariable("id") Long id_viaggio,Principal principal) {
        if(modificaViaggioRepo.existsByVecchioViaggio(viaggioRepo.getReferenceById(id_viaggio))){
            return getListaViaggiMaas(principal);
        }
        viaggioRepo.delete(viaggioRepo.getReferenceById(id_viaggio));
        log.warn("[UTENTE " + principal.getName() + "] VIAGGIO ID : "+ id_viaggio + " Cancellato");
        return getListaViaggiMaas(principal);
    }


    /*
         * Calcolo del costo del viaggio:
         * Treno
         * Pullman
         * Aereo
         * eMobil (mobilità green: monopattini, bici,...) (ALTRO)
         * eAuto
         * Auto
    */
    
    private Double calcolaCosto(dtoNuovoViaggio dtoviaggio){

        Double costoViaggio=0.0;
    
        Double costoBase=4.0;
        Double costoBenzina=8.3;
        Double costoRicarica=7.0;
        Double costoNoleggio=6.0;
        Double costoTratta=5.4;
        Double costoTrattaAereo=8.0;
       
        switch((dtoviaggio.getMezzo()).toLowerCase()){
            case "auto":
                costoViaggio=costoBase*costoBenzina;//33.2
                break;
            case "auto elettrica":
                costoViaggio=costoBase*costoRicarica;//28
                break;
            case "altro":
                costoViaggio=costoBase*costoNoleggio;//24
                break;
            case "aereo":
                costoViaggio=costoBase*costoTrattaAereo;//32
                break;
            case "pullman":
                costoViaggio=costoBase*costoTratta;//21,6
                break;
            case "treno":
                costoViaggio=costoBase*costoTratta;//21,6
                break;
            default:
                costoViaggio=700.0;
        }
        
        return costoViaggio;
    }
    
    private Double riCalcolaCosto(dtoModificaViaggio dtoModificaViaggio){
    
        Double newCostoViaggio=0.0;
    
        Double newCostoBase=4.0;
        Double newCostoBenzina=8.3;
        Double newCostoRicarica=7.0;
        Double newCostoNoleggio=6.0;
        Double newCostoTratta=5.4;
        Double newCostoTrattaAereo=8.0;
       
        switch((dtoModificaViaggio.getNuovoMezzo()).toLowerCase()){
            case "auto":
                newCostoViaggio=newCostoBase*newCostoBenzina;
                break;
            case "auto elettrica":
                newCostoViaggio=newCostoBase*newCostoRicarica;
                break;
            case "altro":
                newCostoViaggio=newCostoBase*newCostoNoleggio;
                break;
            case "aereo":
                newCostoViaggio=newCostoBase*newCostoTrattaAereo;
                break;
            case "pullman":
                newCostoViaggio=newCostoBase*newCostoTratta;
                break;
            case "treno":
                newCostoViaggio=newCostoBase*newCostoTratta;
                break;
            default:
                newCostoViaggio=700.0;
        }
        
        return newCostoViaggio;
    }
}