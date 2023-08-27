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
import com.owl.api.example.model.PowerSupply;
import com.owl.api.example.model.PowerSupplyType;
import com.owl.api.example.model.Purpose;

@Repository
public class PowerSupplyRepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass psuClass;
	private OWLObjectProperty manufacturer;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	private OWLDataProperty type;
	private OWLDataProperty sataConnectors;
	private OWLDataProperty molexConnectors;
	private OWLDataProperty exitPower;
	private OWLDataProperty fanDiameter;
	
	public PowerSupplyRepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.psuClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/Napajanje"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.type = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/napajanjeJeTipa");
		this.sataConnectors = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/napajanjeImaBrojSATAKonektora");
		this.molexConnectors = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/napajanjeImaBrojMOLEXKonektora");
		this.exitPower = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/napajanjeImaIzlaznuSnaguUVatima");
		this.fanDiameter = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/napajanjeImaPrecnikVentilatoraUCentimetrima");
	}
	
	public List<PowerSupply> getAll() {
		Set<OWLNamedIndividual> psuIndividuals = this.reasoner.getInstances(this.psuClass, false).getFlattened();
		List<PowerSupply> psus = new ArrayList<>();
        for (OWLNamedIndividual individual : psuIndividuals) {
        	psus.add(createPowerSupplyFromIndividual(individual));
        }
        return psus;
	}
	
	private PowerSupply createPowerSupplyFromIndividual(OWLNamedIndividual psuIndividual) {
		PowerSupply psu = new PowerSupply();
		psu.setName(psuIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(psuIndividual, manufacturer).getFlattened().stream().findFirst().get();
		psu.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(psuIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		psu.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		psu.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		psu.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		psu.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		psu.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		psu.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	psu.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.type.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "Modularno": {
	            		psu.setType(PowerSupplyType.MODULAR);
	            		break;
	            	}
	            	case "Standardno": {
	            		psu.setType(PowerSupplyType.STANDARD);
	            		break;
	            	}
	            }
            else if (this.sataConnectors.equals(property))
            	psu.setSataConnectors(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.molexConnectors.equals(property))
            	psu.setMolexConnectors(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.exitPower.equals(property))
            	psu.setExitPower(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.fanDiameter.equals(property))
            	psu.setFanDiameter(Integer.parseInt(assertion.getObject().getLiteral()));
        }
		return psu;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
