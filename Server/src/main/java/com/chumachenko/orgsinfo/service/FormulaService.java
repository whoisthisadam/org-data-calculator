package com.chumachenko.orgsinfo.service;

import com.chumachenko.orgsinfo.repository.formules.FormulesRepoImpl;
import com.chumachenko.orgsinfo.repository.formules.FormulesRepository;
import entities.Formula;

import java.util.List;

public class FormulaService {

    FormulesRepository formulesRepository=new FormulesRepoImpl();

    public List<Formula>findAll(){
        return formulesRepository.findAll();
    }
}
