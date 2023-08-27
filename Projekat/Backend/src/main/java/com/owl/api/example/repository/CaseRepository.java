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
import com.owl.api.example.model.Case;
import com.owl.api.example.model.CaseType;
import com.owl.api.example.model.Manufacturer;
import com.owl.api.example.model.Purpose;

@Repository
public class CaseRepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass caseClass;
	private OWLObjectProperty manufacturer;
	private OWLDataProperty caseType;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	private OWLDataProperty pciCardSlots;
	private OWLDataProperty hasPowerSupply;
	
	public CaseRepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.caseClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/Kuciste"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.caseType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/kucisteJeTipa");
		this.pciCardSlots = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/kucisteImaMestaZaPCIKartice");
		this.hasPowerSupply = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/kucisteImaNapajanje");
	}
	
	public List<Case> getAll() {
		Set<OWLNamedIndividual> caseIndividuals = this.reasoner.getInstances(this.caseClass, false).getFlattened();
		List<Case> cases = new ArrayList<>();
        for (OWLNamedIndividual individual : caseIndividuals) {
        	cases.add(createCaseFromIndividual(individual));
        }
        return cases;
	}
	
	private Case createCaseFromIndividual(OWLNamedIndividual caseIndividual) {
		Case boxCase = new Case();
		boxCase.setName(caseIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(caseIndividual, manufacturer).getFlattened().stream().findFirst().get();
		boxCase.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(caseIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		boxCase.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		boxCase.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		boxCase.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		boxCase.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		boxCase.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		boxCase.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	boxCase.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.caseType.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "FullTower": {
	            		boxCase.setType(CaseType.FULL_TOWER);
	            		break;
	            	}
	            	case "MidTower": {
	            		boxCase.setType(CaseType.MID_TOWER);
	            		break;
	            	}
	            	case "MiniTower": {
	            		boxCase.setType(CaseType.MINI_TOWER);
	            		break;
	            	}
	            }
            else if (this.pciCardSlots.equals(property))
                boxCase.setPciSlots(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.hasPowerSupply.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		boxCase.setHasPowerSupply(false);
            	else
            		boxCase.setHasPowerSupply(true);
        }
		return boxCase;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
