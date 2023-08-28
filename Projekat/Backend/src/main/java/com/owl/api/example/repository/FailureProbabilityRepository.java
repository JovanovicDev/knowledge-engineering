package com.owl.api.example.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	private ProbabilisticNetwork network;
	private IInferenceAlgorithm algorithm;
	
	public FailureProbabilityRepository() {
		BaseIO io = new NetIO();
		try {
			this.network = (ProbabilisticNetwork) io.load(new File("data/bayes.net"));
			this.algorithm = new JunctionTreeAlgorithm(this.network);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<FailureProbabilityDto> getFailureCauseProbabilities(List<String> failures) {
		this.algorithm.run();
		for(String failure : failures) {
			ProbabilisticNode node = (ProbabilisticNode) network.getNode(failure);
			node.addFinding(0);
		}
		
		try {
			network.updateEvidences();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<FailureProbabilityDto> causes = new ArrayList<>();
		for (String failure : failures) {
            for (Node childNode : this.network.getNode(failure).getChildren()) {
            	causes.add(new FailureProbabilityDto(childNode.getName(), ((ProbabilisticNode) childNode).getMarginalAt(0) * 100));
                for (Node grandchildNode : this.network.getNode(childNode.getName()).getChildren()) {
                    causes.add(new FailureProbabilityDto(grandchildNode.getName(), ((ProbabilisticNode) grandchildNode).getMarginalAt(0) * 100));
                }
            }
        }
		
		Collections.sort(causes, new Comparator<FailureProbabilityDto>(){
			   public int compare(FailureProbabilityDto dto1, FailureProbabilityDto dto2){
			      return Double.compare(dto2.getProbability(), dto1.getProbability());
			   }
			});
		
		return causes;
	}
}
