package Desafio3.repository;

import Desafio3.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Estoque findByProdutoId(Long produtoId);
}

