package pcshop;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class Main {

	private final static String baseOntologyIRI = "http://www.semanticweb.org/duka/ontologies/2023/4/untitled-ontology-4/";
	private static OWLDataFactory df;
	private static OWLReasoner r;
	
	public static void main(String[] args) throws OWLOntologyCreationException {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		File baseOntology = new File("data/knowledge-base-module.ttl");
		//File extendedOntology = new File("data/knowledge-extended-module.ttl");
		OWLOntology o = man.loadOntologyFromOntologyDocument(baseOntology);
		df = o.getOWLOntologyManager().getOWLDataFactory();
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		r = reasonerFactory.createReasoner(o);
		System.out.println(findCPU(24, 32, 3));
	}

	private static OWLNamedIndividual findCPU(int cores, int threads, float frequency) {
		OWLClass cpu = df.getOWLClass(IRI.create(baseOntologyIRI + "Procesor"));
		
		OWLDataProperty numberOfCores = df.getOWLDataProperty(IRI.create(baseOntologyIRI + "procesorImaBrojJezgara"));
		OWLLiteral numberOfCoresLiteral = df.getOWLLiteral(cores);
		
		OWLDataProperty numberOfThreads = df.getOWLDataProperty(IRI.create(baseOntologyIRI + "procesorImaBrojNiti"));
		OWLLiteral numberOfThreadsLiteral = df.getOWLLiteral(threads);
		
		OWLDataProperty cpuFrequency = df.getOWLDataProperty(IRI.create(baseOntologyIRI + "procesorImaRadnuFrekvencijuUGigahercima"));
		OWLLiteral cpuFrequencyLiteral = df.getOWLLiteral(frequency);		
		
		OWLClassExpression query = df.getOWLObjectIntersectionOf(
				cpu,
				df.getOWLDataHasValue(numberOfCores, numberOfCoresLiteral),
				df.getOWLDataHasValue(numberOfThreads, numberOfThreadsLiteral),
				df.getOWLDataHasValue(cpuFrequency, cpuFrequencyLiteral)
		);
		
		for(OWLNamedIndividual cpuIndividual : r.getInstances(query, true).getFlattened()) return cpuIndividual;
		return null;
	}
	
}
