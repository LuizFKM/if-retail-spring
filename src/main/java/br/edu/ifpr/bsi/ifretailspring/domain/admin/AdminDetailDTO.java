package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoResponseDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record AdminDetailDTO(
        Long ID,
        String name,
        String cpf,
        EnderecoResponseDTO endereco,
        List<ContatoResponseDTO> contatoList,
        String matricula,
        String setor,
        String cargo,
        LocalDate dataAdmissao,
        boolean status
) {}
