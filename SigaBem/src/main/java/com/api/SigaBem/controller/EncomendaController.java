package com.api.SigaBem.controller;

import com.api.SigaBem.model.Encomenda;
import com.api.SigaBem.service.EncomendasService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class EncomendaController {

    EncomendasService encomendasService;

    @ApiOperation(value = "Validando cep com api viacep.com.br e Cadastrando uma nova encomenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Encomenda criada com sucesso"),
            @ApiResponse(code = 500, message = "Houve um erro ao criar a encomenda, verifique o cep e as informações")
    })
    @PostMapping("/encomendas")
    @ResponseStatus(HttpStatus.CREATED)
    public Encomenda createEncomenda (@RequestBody Encomenda encomenda){
        log.info("Criando uma nova encomenda com as informações [{}]", encomenda);
        return encomendasService.createEncomenda(encomenda);
    }

    @ApiOperation(value = "Listando todas as tarefas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encomendas listadas com sucesso"),
            @ApiResponse(code = 500, message = "Houve um erro ao listar as encomendas")
    })
    @GetMapping("/encomendas")
    @ResponseStatus(HttpStatus.OK)
    public List<Encomenda> getAllEncomendas () {
        log.info("Listando todas as encomendas cadastradas");
        return encomendasService.listAllEncomendas();
    }

    @ApiOperation(value = "Buscando uma encomenda por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encomenda encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Houve um erro ao buscar a encomenda")
    })
    @GetMapping("/encomendas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Encomenda> getEncomendasById (@PathVariable (value = "id") Long id){
        log.info("Listando as encomendas com o id [{}]",id);
        return encomendasService.findEncomendaById(id);
    }

    @ApiOperation(value = "Atualizando as informações de uma encomenda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encomenda atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Houve um erro ao atualizar a encomenda")
    })
    @PutMapping("/encomendas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Encomenda> updateEncomendaById (@PathVariable (value = "id") Long id,
                                                          @RequestBody Encomenda encomenda){
        log.info("Atualizando a encomenda com id [{}] as novas informações são: [{}]", id, encomenda);
        return encomendasService.updateEncomenda(encomenda,id);
    }

    @ApiOperation(value = "Excluimdo uma encomenda")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Encomenda ecluida com sucesso"),
            @ApiResponse(code = 404, message = "Houve um erro ao excluir uma encomenda")
    })
    @DeleteMapping("/encomendas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteEncomendaById(@PathVariable (value = "id") Long id){
        log.info("Excluindo uma encomenda com o id [{}]", id);
        return encomendasService.deleteById(id);
    }
}
