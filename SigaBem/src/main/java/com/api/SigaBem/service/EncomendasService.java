package com.api.SigaBem.service;

import com.api.SigaBem.VaidatorViaCep.ValidatorViaCep;
import com.api.SigaBem.model.Encomenda;
import com.api.SigaBem.repository.EncomendaRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class EncomendasService {

    private EncomendaRepository encomendaRepository;

    public Encomenda createEncomenda (Encomenda encomenda){
        this.validatorCep(encomenda);
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
                    encomendaUpdate.setDdd(encomenda.getDdd());
                    encomendaUpdate.setUf(encomenda.getUf());
                    encomendaUpdate.setNomeDestinatario(encomenda.getNomeDestinatario());
                    encomendaUpdate.setVlTotalFrete(encomenda.getVlTotalFrete());
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
        String url = "https://viacep.com.br/ws/"+encomenda.getCepDestino()+"/json";
        RestTemplate restTemplate = new RestTemplate();
        ValidatorViaCep validatorViaCep = restTemplate.getForObject(url, ValidatorViaCep.class);
        Date date = new Date();  double desconto;

        if(encomenda.getDdd().equals(validatorViaCep.getDdd())){
            desconto = 50;  desconto = desconto / 100;
            encomenda.setVlTotalFrete((encomenda.getPeso() - (desconto * encomenda.getPeso()))/ 1000);
            encomenda.setDataPrevistaEntrega(Date.from(date.toInstant().plusSeconds(86400)));
        }
        else if (encomenda.getUf().equals(validatorViaCep.getUf())){
            desconto = 75; desconto = desconto / 100;
            encomenda.setVlTotalFrete((encomenda.getPeso() - (desconto * encomenda.getPeso()))/ 1000);
            encomenda.setDataPrevistaEntrega(Date.from(date.toInstant().plusSeconds(86400 * 3)));
        }
        else{
            encomenda.setVlTotalFrete(encomenda.getPeso() / 1000);
            encomenda.setDataPrevistaEntrega(Date.from(date.toInstant().plusSeconds(86400 * 10)));
        }
    }
}
