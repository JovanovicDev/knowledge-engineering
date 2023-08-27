package com.owl.api.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.vocab.OWLFacet;
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
	
	public List<SSD> findSSDs(int thickness, int capacity, int readSpeed, int writeSpeed) {
		List<SSD> allSSDs = getAll();
		List<SSD> filteredSSDs = new ArrayList<>();
		for(SSD s : allSSDs) {
			if(s.getThickness() == thickness && s.getCapacity() == capacity && s.getReadSpeed() == readSpeed && s.getWriteSpeed() == writeSpeed) {
				filteredSSDs.add(s);
			}
		}
		return filteredSSDs;
	}
	
	public SSD findUpgrade(SSD ssd) {
        Set<OWLLiteral> typeLiterals = reasoner.getDataPropertyValues(dataFactory.getOWLNamedIndividual(IRI.create(this.ontologyIRI + "/" + ssd.getName())), type);
        OWLLiteral type = typeLiterals.stream().findFirst().orElse(null);
		
        OWLDataRange capacity = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(ssd.getCapacity()));
        
        OWLDataRange readSpeed = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(ssd.getReadSpeed()));
        
        OWLDataRange writeSpeed = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(ssd.getWriteSpeed()));

        OWLClassExpression ssdQuery = dataFactory.getOWLObjectIntersectionOf(
                this.ssdClass,
                dataFactory.getOWLDataHasValue(this.type, type),
                dataFactory.getOWLDataSomeValuesFrom(this.capacity, capacity),
                dataFactory.getOWLDataSomeValuesFrom(this.readSpeed, readSpeed),
                dataFactory.getOWLDataSomeValuesFrom(this.writeSpeed, writeSpeed)
        );

        for (OWLNamedIndividual ssdIndividual : reasoner.getInstances(ssdQuery, true).getFlattened())
            return createSSDFromIndividual(ssdIndividual);
        
        return ssd;
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
