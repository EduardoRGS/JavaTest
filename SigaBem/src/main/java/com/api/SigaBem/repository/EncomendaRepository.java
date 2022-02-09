package com.api.SigaBem.repository;

import com.api.SigaBem.model.Encomenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncomendaRepository  extends JpaRepository<Encomenda,Long> {
}
