package br.com.loja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.loja.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
