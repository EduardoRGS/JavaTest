package com.api.SigaBem.service;

import com.api.SigaBem.VaidatorViaCep.ValidatorViaCep;
import com.api.SigaBem.model.Encomenda;
import com.api.SigaBem.repository.EncomendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EncomendasService {

    private EncomendaRepository encomendaRepository;

    public Encomenda createEncomenda (Encomenda encomenda){
        validatorCep(encomenda);
        return encomendaRepository.save(encomenda);
    }

    public List<Encomenda> listAllEncomendas(){
        return encomendaRepository.findAll();
    }

    public ResponseEntity<Encomenda> findEncomendaById (Long id) {
        return encomendaRepository.findById(id)
                .map(encomenda -> ResponseEntity.ok().body(encomenda))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Encomenda> updateEncomenda (Encomenda encomenda, Long id){
        return encomendaRepository.findById(id)
                .map(encomendaUpdate -> {
                    encomendaUpdate.setPeso(encomenda.getPeso());
                    encomendaUpdate.setCepOrigem(encomenda.getCepOrigem());
                    encomendaUpdate.setCepDestino(encomenda.getCepDestino());
                    encomendaUpdate.setNomeDestinatario(encomenda.getNomeDestinatario());
                    encomendaUpdate.setNomeDestinatario(encomenda.getNomeDestinatario());
                    encomendaUpdate.setDataPrevistaEntrega(encomenda.getDataPrevistaEntrega());
                    encomendaUpdate.setDataConsulta(encomenda.getDataConsulta());
                    Encomenda updated = encomendaRepository.save(encomendaUpdate);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deleteById (long id){
        return encomendaRepository.findById(id)
                .map(encomendaDelete -> {
                    encomendaRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public void validatorCep (Encomenda encomenda) {

        String url = "https://viacep.com.br/ws/"+encomenda.getCepDestino()+"json";
        RestTemplate restTemplate = new RestTemplate();
        ValidatorViaCep validatorViaCep = restTemplate.getForObject(url, ValidatorViaCep.class);

        Date date = new Date();

        if(encomenda.getDdd().equals(validatorViaCep.getDdd())){
            encomenda.setVlTotalFrete(encomenda.getPeso() * 0.5);
            encomenda.setDataPrevistaEntrega(Date.from(date.toInstant().plusSeconds(86400)));
        }
        else if(encomenda.getUf().equals(validatorViaCep.getUf())){
            encomenda.setVlTotalFrete(encomenda.getPeso() * 0.75);
            encomenda.setDataPrevistaEntrega(Date.from(date.toInstant().plusSeconds(86400 * 3)));
        }
        else {
            encomenda.getPeso();
        }
    }
}
