package com.owl.api.example.configuration;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OntologySetup {
    private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner;

	public OntologySetup() throws OWLOntologyCreationException {
    	this.ontology = loadOntology(new File("data/knowledge-base-dj.ttl"));
    	this.ontologyIRI = this.ontology.getOntologyID().getOntologyIRI().get();
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

    private static OWLOntology loadOntology(File file) throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology o = manager.loadOntologyFromOntologyDocument(file);
        return o;
    }

}