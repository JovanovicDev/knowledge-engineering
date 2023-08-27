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
import org.springframework.web.bind.annotation.RequestParam;

import com.owl.api.example.configuration.OntologySetup;
import com.owl.api.example.model.GPU;
import com.owl.api.example.model.GPUMemoryType;
import com.owl.api.example.model.Manufacturer;
import com.owl.api.example.model.Motherboard;
import com.owl.api.example.model.Purpose;

@Repository
public class GPURepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass gpuClass;
	private OWLObjectProperty manufacturer;
	private OWLObjectProperty chipManufacturer;
	private OWLDataProperty purpose;
	private OWLDataProperty price;
	private OWLDataProperty capacity;
	private OWLDataProperty memoryBus;
	private OWLDataProperty pciExpressInterfaceVersion;
	private OWLDataProperty memoryType;
	private OWLDataProperty hasDVIInterface;
	private OWLDataProperty hasHDMIInterface;
	private OWLDataProperty hasUSBCInterface;
	private OWLDataProperty hasDisplayPortInterface;
	private OWLDataProperty hasVGAInterface;
	
	public GPURepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.gpuClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/GrafickaKartica"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.chipManufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/grafickaImaProizvodjacaCipa");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.memoryType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaTipMemorije");
		this.capacity = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaKolicinuMemorijeUGigabajtima");
		this.memoryBus = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaMagistraluMemorijeUBitima");
		this.pciExpressInterfaceVersion = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaPCIExpressInterfejsVerzije");
		this.hasDVIInterface = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaDVIInterfejs");
		this.hasHDMIInterface = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaHDMIInterfejs");
		this.hasUSBCInterface = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaUSBCInterfejs");
		this.hasDisplayPortInterface = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaDisplayPortInterfejs");
		this.hasVGAInterface  = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/grafickaImaVGAInterfejs");
	}
	
	public List<GPU> getAll() {
		Set<OWLNamedIndividual> gpuIndividuals = this.reasoner.getInstances(this.gpuClass, false).getFlattened();
		List<GPU> gpus = new ArrayList<>();
        for (OWLNamedIndividual individual : gpuIndividuals) {
            gpus.add(createGPUFromIndividual(individual));
        }
        return gpus;
	}
	
	public List<GPU> findGPUs(int memoryInGigabytes, int memoryBusInBits, int pciExpressVersion, boolean hasDVIInterface, boolean hasUSBCInterface, boolean hasHDMIInterface, boolean hasDisplayPortInterface, boolean hasVGAInterface) {
		List<GPU> allGPUs = getAll();
		List<GPU> filteredGPUs = new ArrayList<>();
		for(GPU g : allGPUs) {
			if(g.getMemoryInGigabytes() == memoryInGigabytes && g.getMemoryBusInBits() == memoryBusInBits && g.getPciExpressVersion() == pciExpressVersion && g.isHasDVIInterface() == hasDVIInterface && g.isHasUSBCInterface() == hasUSBCInterface && g.isHasHDMIInterface() == hasHDMIInterface && g.isHasDisplayPortInterface() == hasDisplayPortInterface && g.isHasVGAInterface() == hasVGAInterface) {
				filteredGPUs.add(g);
			}
		}
		return filteredGPUs;
	}
	
	public GPU createGPUFromIndividual(OWLNamedIndividual gpuIndividual) {
		GPU gpu = new GPU();
		gpu.setName(gpuIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(gpuIndividual, manufacturer).getFlattened().stream().findFirst().get();
        gpu.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
        
        OWLNamedIndividual chipManufacturerIndividual = this.reasoner.getObjectPropertyValues(gpuIndividual, chipManufacturer).getFlattened().stream().findFirst().get();
        gpu.setChipManufacturer(new Manufacturer(chipManufacturerIndividual.getIRI().getShortForm()));
		
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(gpuIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		gpu.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		gpu.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		gpu.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		gpu.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		gpu.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		gpu.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	gpu.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.memoryType.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "GDDR3": {
	            		gpu.setMemoryType(GPUMemoryType.GDDR3);
	            		break;
	            	}
	            	case "GDDR4": {
	            		gpu.setMemoryType(GPUMemoryType.GDDR4);
	            		break;
	            	}
	            	case "GDDR5": {
	            		gpu.setMemoryType(GPUMemoryType.GDDR5);
	            		break;
	            	}
	            	case "GDDR6": {
	            		gpu.setMemoryType(GPUMemoryType.GDDR6);
	            		break;
	            	}
	            }
            else if (this.capacity.equals(property))
                gpu.setMemoryInGigabytes(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.memoryBus.equals(property))
            	gpu.setMemoryBusInBits(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.pciExpressInterfaceVersion.equals(property))
            	gpu.setPciExpressVersion(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.hasDVIInterface.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		gpu.setHasDVIInterface(false);
            	else
            		gpu.setHasDVIInterface(true);
            else if (this.hasHDMIInterface.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		gpu.setHasHDMIInterface(false);
            	else
            		gpu.setHasHDMIInterface(true);
            else if (this.hasUSBCInterface.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		gpu.setHasUSBCInterface(false);
            	else
            		gpu.setHasUSBCInterface(true);
            else if (this.hasDisplayPortInterface.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		gpu.setHasDisplayPortInterface(false);
            	else
            		gpu.setHasDisplayPortInterface(true);
            else if (this.hasVGAInterface.equals(property))
            	if(assertion.getObject().getLiteral().equals("false"))
            		gpu.setHasVGAInterface(false);
            	else
            		gpu.setHasVGAInterface(true);
        }
		return gpu;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
