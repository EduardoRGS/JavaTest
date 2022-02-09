package com.api.SigaBem.controller;

import com.api.SigaBem.model.Encomenda;
import com.api.SigaBem.service.EncomendasService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EncomendaController {

    EncomendasService encomendasService;

    @PostMapping("/encomendas")
    public Encomenda createEncomenda (@RequestBody Encomenda encomenda){
        return encomendasService.createEncomenda(encomenda);
    }

    @GetMapping("/encomendas")
    public List<Encomenda> getAllEncomendas () {
        return encomendasService.listAllEncomendas();
    }

    @GetMapping("/encomendas/{id}")
    public ResponseEntity<Encomenda> getEncomendasById (@PathVariable (value = "id") Long id){
        return encomendasService.findEncomendaById(id);
    }

    @PutMapping("/encomendas/{id}")
    public ResponseEntity<Encomenda> updateEncomendaById (@PathVariable (value = "id") Long id,
                                                          @RequestBody Encomenda encomenda){
        return encomendasService.updateEncomenda(encomenda,id);
    }

    public ResponseEntity<Object> deleteEncomendaById(@PathVariable (value = "id") Long id){
        return encomendasService.deleteById(id);
    }
}
