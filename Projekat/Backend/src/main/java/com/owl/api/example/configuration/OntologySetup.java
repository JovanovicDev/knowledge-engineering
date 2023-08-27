package com.owl.api.example.configuration;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OntologySetup {
    private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;

	public OntologySetup() throws OWLOntologyCreationException {
    	OWLOntology ontology1 = loadOntology(new File("data/knowledge-base-dj.ttl"));
    	OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
    	this.dataFactory = ontologyManager.getOWLDataFactory();
    	this.ontology = ontologyManager.createOntology(ontology1.getOntologyID().getOntologyIRI().get());
    	this.ontologyIRI = this.ontology.getOntologyID().getOntologyIRI().get();
    	ontologyManager.addAxioms(this.ontology, ontology1.getAxioms());
    	Configuration config = new Configuration();
        config.ignoreUnsupportedDatatypes = false;
        OWLReasonerFactory reasonerFactory = new ReasonerFactory();
        this.reasoner = reasonerFactory.createReasoner(ontology, config);
    }

    public OWLOntology getOntology() {
        return ontology;
    }

	public void setOntology(OWLOntology ontology) {
        this.ontology = ontology;
    }
    
    public IRI getOntologyIRI() {
        return ontologyIRI;
    }

    public void setOntologyIRI(IRI ontologyIRI) {
        this.ontologyIRI = ontologyIRI;
    }

    public OWLReasoner getReasoner() {
        return reasoner;
    }

    public void setReasoner(OWLReasoner reasoner) {
        this.reasoner = reasoner;
    }

    public OWLDataFactory getDataFactory() {
		return dataFactory;
	}

	public void setDataFactory(OWLDataFactory dataFactory) {
		this.dataFactory = dataFactory;
	}
    
    private static OWLOntology loadOntology(File file) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology o = manager.loadOntologyFromOntologyDocument(file);
        return o;
    }

}