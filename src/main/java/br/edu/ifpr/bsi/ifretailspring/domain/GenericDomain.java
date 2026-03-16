package br.edu.ifpr.bsi.ifretailspring.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class GenericDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
}
