package com.owl.api.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.springframework.stereotype.Repository;

import com.owl.api.example.configuration.OntologySetup;
import com.owl.api.example.model.Manufacturer;
import com.owl.api.example.model.Purpose;
import com.owl.api.example.model.SSD;
import com.owl.api.example.model.SSDType;

@Repository
public class SSDRepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass ssdClass;
	private OWLObjectProperty manufacturer;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	private OWLDataProperty type;
	private OWLDataProperty readSpeed;
	private OWLDataProperty writeSpeed;
	private OWLDataProperty capacity;
	private OWLDataProperty thickness;
	
	public SSDRepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.ssdClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/SSD"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.type = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/SSDJeTipa");
		this.readSpeed = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/SSDImaBrzinuCitanjaUMBPS");
		this.writeSpeed = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/SSDImaBrzinuPisanjaUMBPS");
		this.capacity = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/SSDImaKapacitetUGigabajtima");
		this.thickness = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/SSDImaDebljinuUMilimetrima");
	}
	
	public List<SSD> getAll() {
		Set<OWLNamedIndividual> ssdIndividuals = this.reasoner.getInstances(this.ssdClass, false).getFlattened();
		List<SSD> ssds = new ArrayList<>();
        for (OWLNamedIndividual individual : ssdIndividuals) {
        	ssds.add(createSSDFromIndividual(individual));
        }
        return ssds;
	}
	
	public SSD createSSDFromIndividual(OWLNamedIndividual ssdIndividual) {
		SSD ssd = new SSD();
		ssd.setName(ssdIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(ssdIndividual, manufacturer).getFlattened().stream().findFirst().get();
		ssd.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(ssdIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		ssd.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		ssd.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		ssd.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		ssd.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		ssd.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		ssd.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	ssd.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.type.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "M.2": {
	            		ssd.setType(SSDType.M2);
	            		break;
	            	}
	            	case "mSATA": {
	            		ssd.setType(SSDType.mSATA);
	            		break;
	            	}
	            }
            else if (this.readSpeed.equals(property))
                ssd.setReadSpeed(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.writeSpeed.equals(property))
            	ssd.setWriteSpeed(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.capacity.equals(property))
            	ssd.setCapacity(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.thickness.equals(property))
            	ssd.setThickness(Integer.parseInt(assertion.getObject().getLiteral()));
        }
		return ssd;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}