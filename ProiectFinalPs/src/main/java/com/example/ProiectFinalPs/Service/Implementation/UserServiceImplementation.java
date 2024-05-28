package com.example.ProiectFinalPs.Service.Implementation;

import com.example.ProiectFinalPs.Model.ParfumMagazin;
import com.example.ProiectFinalPs.Model.Utilizator;
import com.example.ProiectFinalPs.Repository.ParfumMagazinRepository;
import com.example.ProiectFinalPs.Repository.UserRepository;
import com.example.ProiectFinalPs.Service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParfumMagazinRepository parfumMagazinRepository;

    @Override
    public Utilizator findFirstById(Integer id) {
        return userRepository.findFirstById(id);
    }

    @Override
    public List<Utilizator> findAll() {
        return (List<Utilizator>) userRepository.findAll();
    }

    @Override
    public String insert(Utilizator utilizator) {
        userRepository.save(utilizator);
        return ("inserted");
    }

    @Override
    public void delete(Utilizator utilizator) {
        userRepository.delete(utilizator);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Utilizator login(Utilizator credentials) {
        Utilizator utilizator = findByEmail(credentials.getEmail());

        if (utilizator == null || !(utilizator.getParola().equals(credentials.getParola()))) {
            return null;
        }
        {
            return utilizator;
        }
    }

    @Override
    public Utilizator findByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public String saveAsCsv(Utilizator utilizator) {

        if(utilizator.getUserType().equals("angajat"))
        {

            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();
            List<ParfumMagazin> filteredParfumMagazinList = new ArrayList<>();

            for(ParfumMagazin parfumMagazin:parfumMagazinList)
            {
                if(parfumMagazin.getMagazin().getMagazinId().equals(utilizator.getMagazin().getMagazinId()))
                {
                    filteredParfumMagazinList.add(parfumMagazin);
                }
            }

            System.out.println(filteredParfumMagazinList.size());

            try (FileWriter writer = new FileWriter("output.csv")) {
                writer.append("ParfumMagazinId,Disponibilitate,SticlaParfumId,MagazinId, Volum, Pret, Nume, Producator\n");

                for (ParfumMagazin parfumMagazin : filteredParfumMagazinList) {
                    writer.append(String.valueOf(parfumMagazin.getParfumMagazinId())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getDisponibilitate())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getSticlaParfumId())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getMagazin().getMagazinId())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getVolum())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getPret())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getParfum().getNume())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getParfum().getProducator())).append("\n");
                }

                System.out.println("CSV file saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error saving CSV file: " + ex.getMessage());
            }
        }
        else if(utilizator.getUserType().equals("manager"))
        {
            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();


            try (FileWriter writer = new FileWriter("output.csv")) {
                writer.append("ParfumMagazinId,Disponibilitate,SticlaParfumId,MagazinId, Volum, Pret, Nume, Producator\n");

                for (ParfumMagazin parfumMagazin : parfumMagazinList) {
                    writer.append(String.valueOf(parfumMagazin.getParfumMagazinId())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getDisponibilitate())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getSticlaParfumId())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getMagazin().getMagazinId())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getVolum())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getPret())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getParfum().getNume())).append(",");
                    writer.append(String.valueOf(parfumMagazin.getSticlaParfum().getParfum().getProducator())).append("\n");
                }

                System.out.println("CSV file saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error saving CSV file: " + ex.getMessage());
            }
        }
        return ("Saved");
    }

    @Override
    public String saveAsDoc(Utilizator utilizator) {
        if(utilizator.getUserType().equals("angajat"))
        {
            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();
            List<ParfumMagazin> filteredParfumMagazinList = new ArrayList<>();

            for (ParfumMagazin parfumMagazin : parfumMagazinList) {
                if (parfumMagazin.getMagazin().getMagazinId().equals(utilizator.getMagazin().getMagazinId())) {
                    filteredParfumMagazinList.add(parfumMagazin);
                }
            }

            try {
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("ParfumMagazinId, Disponibilitate, SticlaParfumId, MagazinId, Volum, Pret, Nume, Producator");

                for (ParfumMagazin parfumMagazin : filteredParfumMagazinList) {
                    XWPFParagraph recordParagraph = document.createParagraph();
                    XWPFRun recordRun = recordParagraph.createRun();
                    recordRun.setText(String.format("%d, %d, %d, %d, %d, %d, %s, %s",
                            parfumMagazin.getParfumMagazinId(),
                            parfumMagazin.getDisponibilitate(),
                            parfumMagazin.getSticlaParfum().getSticlaParfumId(),
                            parfumMagazin.getMagazin().getMagazinId(),
                            parfumMagazin.getSticlaParfum().getVolum(),
                            parfumMagazin.getSticlaParfum().getPret(),
                            parfumMagazin.getSticlaParfum().getParfum().getNume(),
                            parfumMagazin.getSticlaParfum().getParfum().getProducator()));
                }

                try (FileOutputStream out = new FileOutputStream("outputDoc.docx")) {
                    document.write(out);
                    System.out.println("Word document saved successfully!");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error saving Word document: " + ex.getMessage());
            }
        }
        else if(utilizator.getUserType().equals("manager"))
        {
            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();

            try {
                XWPFDocument document = new XWPFDocument();
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("ParfumMagazinId, Disponibilitate, SticlaParfumId, MagazinId, Volum, Pret, Nume, Producator");

                for (ParfumMagazin parfumMagazin : parfumMagazinList) {
                    XWPFParagraph recordParagraph = document.createParagraph();
                    XWPFRun recordRun = recordParagraph.createRun();
                    recordRun.setText(String.format("%d, %d, %d, %d, %d, %d, %s, %s",
                            parfumMagazin.getParfumMagazinId(),
                            parfumMagazin.getDisponibilitate(),
                            parfumMagazin.getSticlaParfum().getSticlaParfumId(),
                            parfumMagazin.getMagazin().getMagazinId(),
                            parfumMagazin.getSticlaParfum().getVolum(),
                            parfumMagazin.getSticlaParfum().getPret(),
                            parfumMagazin.getSticlaParfum().getParfum().getNume(),
                            parfumMagazin.getSticlaParfum().getParfum().getProducator()));
                }

                try (FileOutputStream out = new FileOutputStream("outputDoc.docx")) {
                    document.write(out);
                    System.out.println("Word document saved successfully!");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error saving Word document: " + ex.getMessage());
            }
        }
        return ("Saved");
    }

    @Override
    public String saveAsJson(Utilizator utilizator) {
        if(utilizator.getUserType().equals("angajat"))
        {

            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();
            List<ParfumMagazin> filteredParfumMagazinList = new ArrayList<>();

            for (ParfumMagazin parfumMagazin : parfumMagazinList) {
                if (parfumMagazin.getMagazin().getMagazinId().equals(utilizator.getMagazin().getMagazinId())) {
                    // Initialize lazy-loaded properties within an active Hibernate session
                    Hibernate.initialize(parfumMagazin.getSticlaParfum().getParfum());
                    filteredParfumMagazinList.add(parfumMagazin);
                }
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter("output.json")) {
                gson.toJson(extractData(filteredParfumMagazinList), writer);
                System.out.println("JSON file saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error saving JSON file: " + ex.getMessage());
            }
        }
        else if(utilizator.getUserType().equals("manager"))
        {

            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter("output.json")) {
                gson.toJson(extractData(parfumMagazinList), writer);
                System.out.println("JSON file saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error saving JSON file: " + ex.getMessage());
            }
        }
        return ("Save");
    }

    @Override
    public String saveAsXml(Utilizator utilizator) {
        if(utilizator.getUserType().equals("angajat"))
        {

            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();
            List<ParfumMagazin> filteredParfumMagazinList = new ArrayList<>();

            for (ParfumMagazin parfumMagazin : parfumMagazinList) {
                if (parfumMagazin.getMagazin().getMagazinId().equals(utilizator.getMagazin().getMagazinId())) {
                    filteredParfumMagazinList.add(parfumMagazin);
                }
            }

            try (FileWriter writer = new FileWriter("output.xml")) {
                JAXBContext context = JAXBContext.newInstance(ParfumMagazinListWrapper.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                ParfumMagazinListWrapper wrapper = new ParfumMagazinListWrapper(filteredParfumMagazinList);
                marshaller.marshal(wrapper, writer);
                System.out.println("XML file saved successfully!");
            } catch (IOException | JAXBException ex) {
                ex.printStackTrace();
                System.out.println("Error saving XML file: " + ex.getMessage());
            }
        }
        else if(utilizator.getUserType().equals("manager"))
        {

            List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();


            try (FileWriter writer = new FileWriter("output.xml")) {
                JAXBContext context = JAXBContext.newInstance(ParfumMagazinListWrapper.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                ParfumMagazinListWrapper wrapper = new ParfumMagazinListWrapper(parfumMagazinList);
                marshaller.marshal(wrapper, writer);
                System.out.println("XML file saved successfully!");
            } catch (IOException | JAXBException ex) {
                ex.printStackTrace();
                System.out.println("Error saving XML file: " + ex.getMessage());
            }
        }
        return ("Saved");
    }

    @Override
    public List<Utilizator> search(String userType) {

        List<Utilizator> utilizatorList = (List<Utilizator>) userRepository.findAll();
        List<Utilizator> filteredUtilizatorList = new ArrayList<>();

        for(Utilizator utilizator:utilizatorList)
        {
            if(utilizator.getUserType().equals(userType))
            {
                filteredUtilizatorList.add(utilizator);
            }
        }

        return filteredUtilizatorList;
    }

    private List<Object> extractData(List<ParfumMagazin> parfumMagazinList) {
        List<Object> extractedData = new ArrayList<>();
        for (ParfumMagazin parfumMagazin : parfumMagazinList) {
            Map<String, Object> data = new HashMap<>();
            data.put("ParfumMagazinId", parfumMagazin.getParfumMagazinId());
            data.put("Disponibilitate", parfumMagazin.getDisponibilitate());
            data.put("SticlaParfumId", parfumMagazin.getSticlaParfum().getSticlaParfumId());
            data.put("MagazinId", parfumMagazin.getMagazin().getMagazinId());
            data.put("Volum", parfumMagazin.getSticlaParfum().getVolum());
            data.put("Pret", parfumMagazin.getSticlaParfum().getPret());
            data.put("Nume", parfumMagazin.getSticlaParfum().getParfum().getNume());
            data.put("Producator", parfumMagazin.getSticlaParfum().getParfum().getProducator());
            extractedData.add(data);
        }
        return extractedData;
    }
}
