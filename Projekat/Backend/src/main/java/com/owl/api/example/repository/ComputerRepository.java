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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owl.api.example.configuration.OntologySetup;
import com.owl.api.example.model.Computer;
import com.owl.api.example.model.Purpose;

@Repository
public class ComputerRepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass computerClass;
	private OWLObjectProperty motherboard;
	private OWLObjectProperty gpu;
	private OWLObjectProperty cpu;
	private OWLObjectProperty ram;
	private OWLObjectProperty boxCase;
	private OWLObjectProperty powerSupply;
	private OWLObjectProperty ssd;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	
    @Autowired
    private MotherboardRepository motherboardRepository;
	
    @Autowired
    private GPURepository gpuRepository;
    
	@Autowired
    private CPURepository cpuRepository;

    @Autowired
    private RAMRepository ramRepository;
    
    @Autowired
    private CaseRepository caseRepository;
    
    @Autowired
    private PowerSupplyRepository psuRepository;

    @Autowired
    private SSDRepository ssdRepository;
	
	public ComputerRepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.computerClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/Konfiguracija"));
		this.motherboard = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaMaticnuPlocu");
		this.gpu = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaGraficku");
		this.cpu = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaProcesor");
		this.ram = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaRAMMemoriju");
		this.boxCase = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaKuciste");
		this.powerSupply = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaNapajanje");
		this.ssd = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/konfiguracijaImaMemoriju");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
	}
	
	public List<Computer> getAll() {
		Set<OWLNamedIndividual> computerIndividuals = this.reasoner.getInstances(this.computerClass, false).getFlattened();
		List<Computer> computers = new ArrayList<>();
        for (OWLNamedIndividual individual : computerIndividuals) {
        	computers.add(createComputerFromIndividual(individual));
        }
        return computers;
	}
	
	private Computer createComputerFromIndividual(OWLNamedIndividual computerIndividual) {
		Computer computer = new Computer();
		computer.setName(computerIndividual.getIRI().getShortForm());
		
		for (OWLObjectPropertyAssertionAxiom assertion : this.ontology.getObjectPropertyAssertionAxioms(computerIndividual)) {
            OWLObjectProperty property = assertion.getProperty().asOWLObjectProperty();
            OWLNamedIndividual component = assertion.getObject().getIndividualsInSignature().stream().findFirst().get();
            if (motherboard.equals(property)) {
                computer.setMotherboard(motherboardRepository.createMotherboardFromIndividual(component));
            }
            if (gpu.equals(property)) {
                computer.setGpu(gpuRepository.createGPUFromIndividual(component));
            }
            if (cpu.equals(property)) {
                computer.setCpu(cpuRepository.createCPUFromIndividual(component));
            }
            if (ram.equals(property)) {
                computer.setRam(ramRepository.createRAMFromIndividual(component));
            }
            if (boxCase.equals(property)) {
                computer.setBoxCase(caseRepository.createCaseFromIndividual(component));
            }
            if (powerSupply.equals(property)) {
                computer.setPowerSupply(psuRepository.createPowerSupplyFromIndividual(component));
            }
            if (ssd.equals(property)) {
                computer.setSsd(ssdRepository.createSSDFromIndividual(component));
            }
        }
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(computerIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		computer.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		computer.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		computer.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		computer.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		computer.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		computer.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	computer.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
        }
		return computer;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
