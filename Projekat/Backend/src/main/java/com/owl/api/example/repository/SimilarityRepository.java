package com.owl.api.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.api.example.configuration.CsvConnector;
import com.owl.api.example.dto.ComputerSimilarityDto;
import com.owl.api.example.model.SimilarComputer;

import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.*;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.EqualsStringIgnoreCase;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SimilarityRepository implements StandardCBRApplication {

    @Autowired
    private ComputerRepository computerRepository;

    private Connector _connector;
    private CBRCaseBase _caseBase;

    private NNConfig simConfig;

    public SimilarityRepository() {
        _connector =  new CsvConnector();
        _caseBase = new LinealCaseBase();
        simConfig = new NNConfig();
    }

    public List<SimilarComputer> getSimilar(ComputerSimilarityDto dto) {
        List<SimilarComputer> similar = new ArrayList<>();
        CBRQuery query = new CBRQuery();
        query.setDescription(dto);

        try {
            this.configure();
            this.preCycle();
            Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
            eval = SelectCases.selectTopKRR(eval, 5);
            for (RetrievalResult selection : eval) {
                ComputerSimilarityDto description = (ComputerSimilarityDto) selection.get_case().getDescription();
                similar.add(new SimilarComputer(computerRepository.getByName(description.getName()), selection.getEval()));
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        return similar;
    }

    @Override
    public void configure() throws ExecutionException {
        simConfig.setDescriptionSimFunction(new Average());
        simConfig.addMapping(new Attribute("cpuNumOfCores", ComputerSimilarityDto.class), new Equal());
        simConfig.addMapping(new Attribute("cpuFrequency", ComputerSimilarityDto.class), new Interval(50));
        simConfig.addMapping(new Attribute("gpuMemory", ComputerSimilarityDto.class), new Interval(100));
        simConfig.addMapping(new Attribute("gpuMemoryBus", ComputerSimilarityDto.class), new Equal());
        simConfig.addMapping(new Attribute("ramCapacity", ComputerSimilarityDto.class), new Equal());
        simConfig.addMapping(new Attribute("ramType", ComputerSimilarityDto.class), new EqualsStringIgnoreCase());
        simConfig.addMapping(new Attribute("diskCapacity", ComputerSimilarityDto.class), new Equal());
    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        java.util.Collection<CBRCase> cases = _caseBase.getCases();
        return _caseBase;
    }

    @Override
    public void cycle(CBRQuery cbrQuery) throws ExecutionException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), cbrQuery, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval)
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
    }

    @Override
    public void postCycle() throws ExecutionException {

    }
}
