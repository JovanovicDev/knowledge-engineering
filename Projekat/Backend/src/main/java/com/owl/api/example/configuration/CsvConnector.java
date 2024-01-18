package com.owl.api.example.configuration;

import com.owl.api.example.dto.ComputerSimilarityDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

public class CsvConnector implements Connector {

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        LinkedList<CBRCase> cases = new LinkedList<CBRCase>();

        try {
            CSVReader reader = new CSVReader(new FileReader(new File("data/similarity.csv")));
            reader.readNext();

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                ComputerSimilarityDto computer = new ComputerSimilarityDto();
                computer.setName(nextLine[0]);
                computer.setCpuNumOfCores(Integer.parseInt(nextLine[1]));
                computer.setCpuFrequency(Float.parseFloat(nextLine[2]));
                computer.setGpuMemory(Integer.parseInt(nextLine[3]));
                computer.setGpuMemoryBus(Integer.parseInt(nextLine[4]));
                computer.setRamCapacity(Integer.parseInt(nextLine[5]));
                computer.setRamType(nextLine[6]);
                computer.setDiskCapacity(Integer.parseInt(nextLine[7]));

                CBRCase cbrCase = new CBRCase();
                cbrCase.setDescription(computer);
                cases.add(cbrCase);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cases;
    }

    @Override
    public void initFromXMLfile(URL url) throws InitializingException {

    }

    @Override
    public void close() {

    }

    @Override
    public void storeCases(Collection<CBRCase> collection) {

    }

    @Override
    public void deleteCases(Collection<CBRCase> collection) {

    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter caseBaseFilter) {
        return null;
    }
}
