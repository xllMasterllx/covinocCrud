package com.covinoc.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import com.covinoc.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
