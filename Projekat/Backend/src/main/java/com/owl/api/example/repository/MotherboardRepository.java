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
import com.owl.api.example.model.CaseType;
import com.owl.api.example.model.GPUMemoryType;
import com.owl.api.example.model.Manufacturer;
import com.owl.api.example.model.Motherboard;
import com.owl.api.example.model.MotherboardChipset;
import com.owl.api.example.model.PowerSupplyType;
import com.owl.api.example.model.Purpose;
import com.owl.api.example.model.RAMType;
import com.owl.api.example.model.SSDType;

@Repository
public class MotherboardRepository {
	private OWLOntology ontology;
    private IRI ontologyIRI;
    private OWLReasoner reasoner; 
    private OWLDataFactory dataFactory;
    
    private OWLClass motherboardClass;
	private OWLObjectProperty manufacturer;
	private OWLObjectProperty compatibleWithCPUsFrom;
	private OWLDataProperty compatibleWithGPUMemoryType;
	private OWLDataProperty compatibleWithPSUType;
	private OWLDataProperty compatibleWithCaseType;
	private OWLDataProperty compatibleWithRAMType;
	private OWLDataProperty compatibleWithSSDType;
	private OWLDataProperty price;
	private OWLDataProperty purpose;
	private OWLDataProperty chipset;
	private OWLDataProperty pciExpressSlots;
	private OWLDataProperty sataSlots;
	private OWLDataProperty m2Slots;
	private OWLDataProperty ramSlots;
		
	public MotherboardRepository(OntologySetup ontologySetup) {
		instantiateOntology(ontologySetup);
		
		this.motherboardClass = this.dataFactory.getOWLClass(IRI.create(this.ontologyIRI + "/MaticnaPloca"));
		this.manufacturer = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/imaProizvodjaca");
		this.compatibleWithCPUsFrom = this.dataFactory.getOWLObjectProperty(this.ontologyIRI + "/maticnaPodrzavaProcesoreProizvodjaca");
		this.compatibleWithGPUMemoryType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaPodrzavaGrafickeSaMemorijomTipa");
		this.compatibleWithPSUType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaPodrzavaNapajanjaTipa");
		this.compatibleWithCaseType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaPodrzavaKucistaVelicine");
		this.compatibleWithRAMType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaPodrzavaRAMMemorijuTipa");
		this.compatibleWithSSDType = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaPodrzavaSSDTipa");
		this.price = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaCenuURSD");
		this.purpose = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/imaNamenu");
		this.chipset = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaImaChipset");
		this.pciExpressSlots = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaImaPCIExpressSlotova");
		this.sataSlots = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaImaSataSlotova");
		this.m2Slots = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaImaM2Slotova");
		this.ramSlots = this.dataFactory.getOWLDataProperty(this.ontologyIRI + "/maticnaImaRAMSlotova");
	}
	
	public List<Motherboard> getAll() {
		Set<OWLNamedIndividual> motherboardIndividuals = this.reasoner.getInstances(this.motherboardClass, false).getFlattened();
		List<Motherboard> motherboards = new ArrayList<>();
        for (OWLNamedIndividual individual : motherboardIndividuals) {
        	motherboards.add(createMotherboardFromIndividual(individual));
        }
        return motherboards;
	}
	
	public List<Motherboard> findMotherboards(int pciExpressSlots, int sataSlots, int ramSlots, int m2Slots) {
		List<Motherboard> allMotherboards = getAll();
		List<Motherboard> filteredMotherboards = new ArrayList<>();
		for(Motherboard m : allMotherboards) {
			if(m.getPciExpressSlots() == pciExpressSlots && m.getSataSlots() == sataSlots && m.getRamSlots() == ramSlots && m.getM2Slots() == m2Slots) {
				filteredMotherboards.add(m);
			}
		}
		return filteredMotherboards;
	}
	
	public Motherboard findUpgrade(Motherboard motherboard) {
        Set<OWLLiteral> chipsetLiterals = reasoner.getDataPropertyValues(dataFactory.getOWLNamedIndividual(IRI.create(this.ontologyIRI + "/" + motherboard.getName())), chipset);
        OWLLiteral chipset = chipsetLiterals.stream().findFirst().orElse(null);
		
        OWLDataRange numOfPCIExpressSlots = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(motherboard.getPciExpressSlots()));
        
        OWLDataRange numOfSataSlots = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(motherboard.getSataSlots()));

        OWLDataRange numOfRamSlots = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(motherboard.getRamSlots()));
        
        OWLDataRange numOfM2Slots = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(motherboard.getM2Slots()));
        
        OWLClassExpression motherboardQuery = dataFactory.getOWLObjectIntersectionOf(
                this.motherboardClass,
                dataFactory.getOWLDataHasValue(this.chipset, chipset),
                dataFactory.getOWLDataSomeValuesFrom(this.pciExpressSlots, numOfPCIExpressSlots),
                dataFactory.getOWLDataSomeValuesFrom(this.sataSlots, numOfSataSlots),
                dataFactory.getOWLDataSomeValuesFrom(this.ramSlots, numOfRamSlots),
                dataFactory.getOWLDataSomeValuesFrom(this.m2Slots, numOfM2Slots)
        );

        for (OWLNamedIndividual motherboardIndividual : reasoner.getInstances(motherboardQuery, true).getFlattened())
            return createMotherboardFromIndividual(motherboardIndividual);
        
        return motherboard;
    }
	
	public Motherboard createMotherboardFromIndividual(OWLNamedIndividual motherboardIndividual) {
		Motherboard motherboard = new Motherboard();
		motherboard.setName(motherboardIndividual.getIRI().getShortForm());
		
		OWLNamedIndividual manufacturerIndividual = this.reasoner.getObjectPropertyValues(motherboardIndividual, manufacturer).getFlattened().stream().findFirst().get();
		motherboard.setManufacturer(new Manufacturer(manufacturerIndividual.getIRI().getShortForm()));
		
		OWLNamedIndividual cpuManufacturerIndividual = this.reasoner.getObjectPropertyValues(motherboardIndividual, compatibleWithCPUsFrom).getFlattened().stream().findFirst().get();
		motherboard.setCompatibleWithCPUsFrom(new Manufacturer(cpuManufacturerIndividual.getIRI().getShortForm()));
				
        for (OWLDataPropertyAssertionAxiom assertion : this.ontology.getDataPropertyAssertionAxioms(motherboardIndividual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (this.purpose.equals(property))
                switch(assertion.getObject().getLiteral()) {
                	case "Kucna upotreba": {
                		motherboard.setPurpose(Purpose.HOME_USE);
                		break;
                	}
                	case "Profesionalna": {
                		motherboard.setPurpose(Purpose.PROFESSIONAL_USE);
                		break;
                	}
                	case "Razvoj aplikacija": {
                		motherboard.setPurpose(Purpose.APP_DEVELOPMENT);
                		break;
                	}
                	case "Hostovanje": {
                		motherboard.setPurpose(Purpose.HOSTING);
                		break;
                	}
                	case "Gaming": {
                		motherboard.setPurpose(Purpose.GAMING);
                		break;
                	}
                	case "Rudarenje kriptovaluta": {
                		motherboard.setPurpose(Purpose.CRYPTO_MINING);
                		break;
                	}
                }
            else if (this.price.equals(property))
            	motherboard.setPrice(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.chipset.equals(property))
            	switch(assertion.getObject().getLiteral()) {
	            	case "AMD_B550": {
	            		motherboard.setChipset(MotherboardChipset.AMD_B550);
	            		break;
	            	}
	            	case "AMD_B650": {
	            		motherboard.setChipset(MotherboardChipset.AMD_B650);
	            		break;
	            	}
	            	case "Intel_B660": {
	            		motherboard.setChipset(MotherboardChipset.INTEL_B660);
	            		break;
	            	}
	            	case "Intel_B760": {
	            		motherboard.setChipset(MotherboardChipset.INTEL_B760);
	            		break;
	            	}
	            	case "Intel_Z690": {
	            		motherboard.setChipset(MotherboardChipset.INTEL_Z690);
	            		break;
	            	}
	            	case "Intel_Z790": {
	            		motherboard.setChipset(MotherboardChipset.INTEL_Z790);
	            		break;
	            	}
	            }
            else if (this.compatibleWithGPUMemoryType.equals(property)) 
            	switch(assertion.getObject().getLiteral()) {
	            	case "GDDR3": {
	            		motherboard.setCompatibleWithGPUMemoryType(GPUMemoryType.GDDR3);
	            		break;
	            	}
	            	case "GDDR4": {
	            		motherboard.setCompatibleWithGPUMemoryType(GPUMemoryType.GDDR4);
	            		break;
	            	}
	            	case "GDDR5": {
	            		motherboard.setCompatibleWithGPUMemoryType(GPUMemoryType.GDDR5);
	            		break;
	            	}
	            	case "GDDR6": {
	            		motherboard.setCompatibleWithGPUMemoryType(GPUMemoryType.GDDR6);
	            		break;
	            	}
            	}
            else if (this.compatibleWithPSUType.equals(property)) 
            	switch(assertion.getObject().getLiteral()) {
	            	case "Modularno": {
	            		motherboard.setCompatibleWithPSUType(PowerSupplyType.MODULAR);
	            		break;
	            	}
	            	case "Standardno": {
	            		motherboard.setCompatibleWithPSUType(PowerSupplyType.STANDARD);
	            		break;
	            	}
            	}
            else if (this.compatibleWithCaseType.equals(property)) 
            	switch(assertion.getObject().getLiteral()) {
	            	case "FullTower": {
	            		motherboard.setCompatibleWithCaseType(CaseType.FULL_TOWER);
	            		break;
	            	}
	            	case "MidTower": {
	            		motherboard.setCompatibleWithCaseType(CaseType.MID_TOWER);
	            		break;
	            	}
	            	case "MiniTower": {
	            		motherboard.setCompatibleWithCaseType(CaseType.MINI_TOWER);
	            		break;
	            	}
            	}
            else if (this.compatibleWithRAMType.equals(property)) 
            	switch(assertion.getObject().getLiteral()) {
	            	case "DDR3": {
	            		motherboard.setCompatibleWithRAMType(RAMType.DDR3);
	            		break;
	            	}
	            	case "DDR4": {
	            		motherboard.setCompatibleWithRAMType(RAMType.DDR4);
	            		break;
	            	}
	            	case "DDR5": {
	            		motherboard.setCompatibleWithRAMType(RAMType.DDR5);
	            		break;
	            	}
            	}
            else if (this.compatibleWithSSDType.equals(property)) 
            	switch(assertion.getObject().getLiteral()) {
	            	case "M.2": {
	            		motherboard.setCompatibleWithSSDType(SSDType.M2);
	            		break;
	            	}
	            	case "mSATA": {
	            		motherboard.setCompatibleWithSSDType(SSDType.mSATA);
	            		break;
	            	}
            	}
            else if (this.pciExpressSlots.equals(property))
            	motherboard.setPciExpressSlots(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.sataSlots.equals(property))
            	motherboard.setSataSlots(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.m2Slots.equals(property))
            	motherboard.setM2Slots(Integer.parseInt(assertion.getObject().getLiteral()));
            else if (this.ramSlots.equals(property))
            	motherboard.setRamSlots(Integer.parseInt(assertion.getObject().getLiteral()));
        }
		return motherboard;
	}
	
	private void instantiateOntology(OntologySetup ontologySetup) {
		this.ontology = ontologySetup.getOntology();
		this.ontologyIRI = ontologySetup.getOntologyIRI();
		this.reasoner = ontologySetup.getReasoner();
		this.dataFactory = ontologySetup.getDataFactory();
	}
}
