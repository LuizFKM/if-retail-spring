package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;

import java.time.LocalDate;
import java.util.List;

public record AdminRequestDTO(
        String name,
        String cpf,
        String password,
        EnderecoRequestDTO endereco,
        List<ContatoRequestDTO> contatoList,
        String matricula,
        String setor,
        String cargo,
        LocalDate dataAdmissao
) {}
