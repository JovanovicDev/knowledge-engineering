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
import com.owl.api.example.model.CPU;
import com.owl.api.example.model.CPUSocket;
import com.owl.api.example.model.Manufacturer;
import com.owl.api.example.model.Purpose;

@Repository
public class CPURepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass cpuClass;
	private OWLObjectProperty manufacturer;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	private OWLDataProperty socket;
	private OWLDataProperty frequency;
	private OWLDataProperty thermicPower;
	private OWLDataProperty cores;
	private OWLDataProperty threads;
	private OWLDataProperty fabricationProcess;
	private OWLDataProperty canOverclock;
	private OWLDataProperty hasIntegratedGraphics;
	
	public CPURepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.cpuClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/Procesor"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.socket = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaPodnozje");
		this.frequency = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaRadnuFrekvencijuUGigahercima");
		this.thermicPower = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaTermickuSnaguUVatima");
		this.cores = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaBrojJezgara");
		this.threads = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaBrojNiti");
		this.fabricationProcess = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaTehnologijuIzradeUNanometrima");
		this.canOverclock = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorSeMozeOverclockovati");
		this.hasIntegratedGraphics = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/procesorImaIntegrisanuGrafiku");
	}
	
	public List<CPU> getAll() {
		Set<OWLNamedIndividual> cpuIndividuals = this.reasoner.getInstances(this.cpuClass, false).getFlattened();
		List<CPU> cpus = new ArrayList<>();
        for (OWLNamedIndividual individual : cpuIndividuals) {
            cpus.add(createCPUFromIndividual(individual));
        }
        return cpus;
	}
	
	public CPU createCPUFromIndividual(OWLNamedIndividual cpuIndividual) {
		CPU cpu = new CPU();
		cpu.setName(cpuIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(cpuIndividual, manufacturer).getFlattened().stream().findFirst().get();
        cpu.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(cpuIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		cpu.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		cpu.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		cpu.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		cpu.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		cpu.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		cpu.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
                cpu.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.socket.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "AMD_AM4": {
	            		cpu.setSocket(CPUSocket.AMD_AM4);
	            		break;
	            	}
	            	case "AMD_AM5": {
	            		cpu.setSocket(CPUSocket.AMD_AM5);
	            		break;
	            	}
	            	case "Intel_1200": {
	            		cpu.setSocket(CPUSocket.INTEL_1200);
	            		break;
	            	}
	            	case "Intel_1700": {
	            		cpu.setSocket(CPUSocket.INTEL_1700);
	            		break;
	            	}
	            }
            else if (this.frequency.equals(property))
                cpu.setFrequency(Double.parseDouble(assertion.getObject().getLiteral()));
            else if (this.thermicPower.equals(property))
            	cpu.setThermicPower(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.cores.equals(property))
            	cpu.setCores(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.threads.equals(property))
            	cpu.setThreads(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.fabricationProcess.equals(property))
            	cpu.setFabricationProcess(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.canOverclock.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		cpu.setCanOverclock(false);
            	else
            		cpu.setCanOverclock(true);
            else if (this.hasIntegratedGraphics.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		cpu.setHasIntegratedGraphics(false);
            	else
            		cpu.setHasIntegratedGraphics(true);
        }
		return cpu;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
