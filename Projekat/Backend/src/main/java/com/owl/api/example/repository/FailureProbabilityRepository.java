package com.owl.api.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.owl.api.example.dto.FailureProbabilityDto;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

@Repository
public class FailureProbabilityRepository {
    private ProbabilisticNetwork net;
    private IInferenceAlgorithm algorithm;

    public FailureProbabilityRepository() throws IOException {
        BaseIO io = new NetIO();
        net = (ProbabilisticNetwork) io.load(new File("data/bayes.net"));
    }
    public List<FailureProbabilityDto> getFailureCauseProbabilities(List<String> failures) throws Exception {
        algorithm = new JunctionTreeAlgorithm();
        algorithm.setNetwork(net);
        algorithm.run();

        for(String failure : failures){
            ProbabilisticNode factNode = (ProbabilisticNode)net.getNode(failure);
            int stateIndex = 0;
            if(factNode == null)
                throw new Exception("Unknown cause of malfunction!");
            factNode.addFinding(stateIndex);
        }

        try {
            net.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<FailureProbabilityDto> causeProbabilityDTOs = new ArrayList<>();

        for(String failure : failures){
            for (Node parent : net.getNode(failure).getParents()){
            	FailureProbabilityDto causeProbabilityDTO1 = new FailureProbabilityDto();
                causeProbabilityDTO1.setCauseName(parent.getName());
                causeProbabilityDTO1.setProbability(Math.round(((ProbabilisticNode) parent).getMarginalAt(0) * 100));
                causeProbabilityDTOs.add(causeProbabilityDTO1);
                for (Node grandparent : net.getNode(parent.getName()).getParents()){
                	FailureProbabilityDto causeProbabilityDTO2 = new FailureProbabilityDto();
                    causeProbabilityDTO2.setCauseName(grandparent.getName());
                    causeProbabilityDTO2.setProbability(Math.round(((ProbabilisticNode) grandparent).getMarginalAt(0) * 100));
                    causeProbabilityDTOs.add(causeProbabilityDTO2);

                }
            }
        }

        if(causeProbabilityDTOs.isEmpty())
            throw new Exception("Unknown cause of malfunction!");

        causeProbabilityDTOs = causeProbabilityDTOs.stream().distinct().collect(Collectors.toList());
        for(String failure : failures) {
            Iterator<FailureProbabilityDto> iterator = causeProbabilityDTOs.iterator();
            while (iterator.hasNext()) {
            	FailureProbabilityDto obj = iterator.next();
                if (obj.getCauseName().equals(failure)) {
                    iterator.remove();
                }
            }
        }
        Collections.sort(causeProbabilityDTOs);
        return causeProbabilityDTOs.subList(0, Math.min(causeProbabilityDTOs.size(), 5));
    }
}
