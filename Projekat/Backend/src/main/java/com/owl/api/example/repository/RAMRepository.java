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
import com.owl.api.example.model.RAM;
import com.owl.api.example.model.RAMType;

@Repository
public class RAMRepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass ramClass;
	private OWLObjectProperty manufacturer;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	private OWLDataProperty type;
	private OWLDataProperty frequency;
	private OWLDataProperty voltage;
	private OWLDataProperty latency;
	private OWLDataProperty capacity;
	
	public RAMRepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.ramClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/RAM"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.type = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/ramJeTipa");
		this.frequency = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/ramImaRadnuFrekvencijuUMegahercima");
		this.voltage = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/ramPodrzavaNaponUVoltima");
		this.latency = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/ramImaLatencijuUCL");
		this.capacity = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/ramImaKapacitetUGigabajtima");
	}
	
	public List<RAM> getAll() {
		Set<OWLNamedIndividual> ramIndividuals = this.reasoner.getInstances(this.ramClass, false).getFlattened();
		List<RAM> rams = new ArrayList<>();
        for (OWLNamedIndividual individual : ramIndividuals) {
            rams.add(createRAMFromIndividual(individual));
        }
        return rams;
	}
	
	private RAM createRAMFromIndividual(OWLNamedIndividual ramIndividual) {
		RAM ram = new RAM();
		ram.setName(ramIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(ramIndividual, manufacturer).getFlattened().stream().findFirst().get();
		ram.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(ramIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		ram.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		ram.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		ram.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		ram.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		ram.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		ram.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	ram.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.type.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "DDR3": {
	            		ram.setType(RAMType.DDR3);
	            		break;
	            	}
	            	case "DDR4": {
	            		ram.setType(RAMType.DDR4);
	            		break;
	            	}
	            	case "DDR5": {
	            		ram.setType(RAMType.DDR5);
	            		break;
	            	}
	            }
            else if (this.frequency.equals(property))
                ram.setFrequency(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.latency.equals(property))
            	ram.setLatency(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.capacity.equals(property))
            	ram.setCapacity(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.voltage.equals(property))
            	ram.setVoltage(Double.parseDouble(assertion.getObject().getLiteral()));
        }
		return ram;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
