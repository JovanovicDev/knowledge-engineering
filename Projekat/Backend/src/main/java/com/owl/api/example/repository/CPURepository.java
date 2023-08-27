package com.owl.api.example.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWLFacet;
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
    private OWLOntologyManager manager;
    
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
	
	public List<CPU> findCPUs(int thermicPower, int cores, int threads, int fabricationProcess, double frequency, boolean canOverclock, boolean hasIntegratedGraphics) {
		Set<OWLNamedIndividual> cpuIndividuals = this.reasoner.getInstances(this.cpuClass, false).getFlattened();
		List<CPU> filteredCPUs = new ArrayList<>();
        for (OWLNamedIndividual individual : cpuIndividuals) {
            List<OWLLiteral> tpFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.thermicPower), this.ontology).toList();
            int thermicPowerValue = Integer.parseInt(tpFromIndividual.get(0).getLiteral());
            
            List<OWLLiteral> coresFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.cores), this.ontology).toList();
            int coresValue = Integer.parseInt(coresFromIndividual.get(0).getLiteral());
            
            List<OWLLiteral> threadsFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.threads), this.ontology).toList();
            int threadsValue = Integer.parseInt(threadsFromIndividual.get(0).getLiteral());
            
            List<OWLLiteral> fabricationProcessFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.fabricationProcess), this.ontology).toList();
            int fabricationProcessValue = Integer.parseInt(fabricationProcessFromIndividual.get(0).getLiteral());
            
            List<OWLLiteral> frequencyFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.frequency), this.ontology).toList();
            double frequencyValue = Double.parseDouble(frequencyFromIndividual.get(0).getLiteral());
            
            List<OWLLiteral> canOverclockFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.canOverclock), this.ontology).toList();
            boolean canOverclockValue;
            if(canOverclockFromIndividual.get(0).getLiteral().equals("true")) {
            	canOverclockValue = true;
            } else {
            	canOverclockValue = false;
            }

            List<OWLLiteral> hasIntegratedGraphicsFromIndividual = EntitySearcher.getDataPropertyValues(individual, this.manager.getOWLDataFactory().getOWLDataProperty(this.hasIntegratedGraphics), this.ontology).toList();
            boolean hasIntegratedGraphicsValue;
            if(hasIntegratedGraphicsFromIndividual.get(0).getLiteral().equals("true")) {
            	hasIntegratedGraphicsValue = true;
            } else {
            	hasIntegratedGraphicsValue = false;
            }
            
            if (thermicPowerValue == thermicPower && coresValue == cores && threadsValue == threads && fabricationProcessValue == fabricationProcess && frequencyValue == frequency && canOverclockValue == canOverclock && hasIntegratedGraphicsValue == hasIntegratedGraphics) {
            	filteredCPUs.add(createCPUFromIndividual(individual));
            }
        }
        return filteredCPUs;
	}
	
	public CPU findUpgrade(CPU cpu) {
        Set<OWLLiteral> socketLiterals = reasoner.getDataPropertyValues(dataFactory.getOWLNamedIndividual(IRI.create(this.ontologyIRI + "/" + cpu.getName())), socket);
        OWLLiteral socket = socketLiterals.stream().findFirst().orElse(null);
		
        OWLDataRange numOfCores = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(cpu.getCores()));
        
        OWLDataRange numOfThreads = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(cpu.getThreads()));

        OWLClassExpression cpuQuery = dataFactory.getOWLObjectIntersectionOf(
                this.cpuClass,
                dataFactory.getOWLDataHasValue(this.socket, socket),
                dataFactory.getOWLDataSomeValuesFrom(this.cores, numOfCores),
                dataFactory.getOWLDataSomeValuesFrom(this.threads, numOfThreads)
        );

        for (OWLNamedIndividual cpuIndividual : reasoner.getInstances(cpuQuery, true).getFlattened())
            return createCPUFromIndividual(cpuIndividual);
        
        return cpu;
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
		this.manager = OWLManager.createOWLOntologyManager();
		try {
			this.ontology = this.manager.loadOntologyFromOntologyDocument(new File("data/knowledge-base-dj.ttl"));
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
}
