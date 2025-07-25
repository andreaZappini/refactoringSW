package model;
import java.io.File;

import java.io.FileOutputStream;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import model.stato.*;

public class XMLUtilities {
    
    public static <T> Elenco<T> leggiXML(File file, String contesto, Function<Element, T> parser) throws Exception {
        Elenco<T> elenco = new Elenco<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();
    
        NodeList lista = doc.getDocumentElement().getChildNodes();

    
        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                try {
                    elenco.aggiungi(parser.apply(elemento));
                } catch (Exception e) {
                    System.err.println("ERRORE nel parsing dell'elemento: " + e.getMessage() + elemento.toString() + nodo.getTextContent());
                }
            }
        }
        return elenco;
    }

    public static Elenco<ListaVisite> leggiListaVisiteXML(File file, Elenco<TipoVisita> elencoTV, Elenco<Fruitore> elencoF) throws Exception {
        Elenco<ListaVisite> elenco = new Elenco<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList listaVisiteNodes = doc.getElementsByTagName("ListaVisite");

        for (int i = 0; i < listaVisiteNodes.getLength(); i++) {
            Element listaVisiteElem = (Element) listaVisiteNodes.item(i);

            String chiave = listaVisiteElem.getElementsByTagName("chiave").item(0).getTextContent();
            ListaVisite listaVisite = new ListaVisite(chiave);

            NodeList visiteNodes = ((Element) listaVisiteElem.getElementsByTagName("visite").item(0)).getElementsByTagName("visita");
            for (int j = 0; j < visiteNodes.getLength(); j++) {
                Element visitaElem = (Element) visiteNodes.item(j);

                LocalDate data = LocalDate.parse(visitaElem.getElementsByTagName("dataVisita").item(0).getTextContent());
                StatiVisita stato = creaStato(visitaElem.getElementsByTagName("stato").item(0).getTextContent());
                String tipoElem = visitaElem.getElementsByTagName("tipo").item(0).getTextContent();
                TipoVisita tipo = elencoTV.getElementByKey(tipoElem);

                int iscritti = Integer.parseInt(visitaElem.getElementsByTagName("iscritti").item(0).getTextContent());
                HashMap<Fruitore, Prenotazione> prenotazioni = new HashMap<>();

                NodeList iscrizioniNodes = ((Element) visitaElem.getElementsByTagName("iscrizioni").item(0)).getElementsByTagName("iscrizione");
                for (int k = 0; k < iscrizioniNodes.getLength(); k++) {
                    Element iscrizioneElem = (Element) iscrizioniNodes.item(k);

                    String fruitoreStr = iscrizioneElem.getElementsByTagName("fruitore").item(0).getTextContent();
                    String numStr = iscrizioneElem.getElementsByTagName("numPersone").item(0).getTextContent();
                    NodeList codiceNode = iscrizioneElem.getElementsByTagName("codice");
                    String codice = codiceNode.getLength() > 0 ? codiceNode.item(0).getTextContent() : null;

                    Fruitore f = elencoF.getElementByKey(fruitoreStr);
                    if(codice == null)
                        prenotazioni.put(f, new Prenotazione(f, Integer.parseInt(numStr)));
                    else
                        prenotazioni.put(f, new Prenotazione(codice, f, Integer.parseInt(numStr)));
                }

                Visita visita = new Visita(data, tipo, stato, prenotazioni, iscritti);                
                listaVisite.aggiungiVisita(visita);
            }

            elenco.aggiungi(listaVisite);
        }

        return elenco;
    }

    private static StatiVisita creaStato(String stato) {
        switch (stato) {
            case "PROPONIBILE":
                return new VisitaProponibile();
            case "PROPOSTA":
                return new VisitaProposta();
            case "CONFERMATA":
                return new VisitaConfermata();
            case "CANCELLATA":
                return new VisitaCancellata();
            case "EFFETTUATA":
                return new VisitaEffettuata();
            case "COMPLETA":
                return new VisitaCompleta();
            default:
                throw new IllegalArgumentException("Stato di visita sconosciuto: " + stato);
        }
    }

    

    public static String[] leggiXML(File file, String contesto) throws Exception{

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();
        rimuoviNodiVuoti(doc);

        NodeList lista = doc.getDocumentElement().getChildNodes();
        String[] risultato = new String[lista.getLength()];

        for (int i = 0; i < lista.getLength(); i++) {
            Node nodo = lista.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                risultato[i] = nodo.getTextContent().trim();
            }
        }
        return risultato;
    }
    
    public static <T> void scriviXML(File file, Elenco<T> elencoOggetti, String rootElementName) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
    
        Element rootElement = doc.createElement(rootElementName);
        doc.appendChild(rootElement);
    
        for (String key : elencoOggetti.getElenco().keySet()) {
            T oggetto = elencoOggetti.getElementByKey(key);
    
            Element objectElement = doc.createElement(oggetto.getClass().getSimpleName());
    
            if (oggetto instanceof LocalDate) {
                objectElement.setTextContent(((LocalDate) oggetto).toString());

            } else if(oggetto instanceof ListaVisite){
                scriviListaVisite(oggetto, doc, objectElement);
            } else if (oggetto instanceof Fruitore) {
                Fruitore fruitore = (Fruitore) oggetto;

                Element usernameElem = doc.createElement("username");
                usernameElem.setTextContent(fruitore.getUsername());
                objectElement.appendChild(usernameElem);

                Element passwordElem = doc.createElement("password");
                passwordElem.setTextContent(fruitore.getPassword());
                objectElement.appendChild(passwordElem);

                Element primoAccessoElem = doc.createElement("primoAccesso");
                primoAccessoElem.setTextContent(String.valueOf(fruitore.getPrimoAccesso()));
                objectElement.appendChild(primoAccessoElem);

            }else {
                scriviOggettoXML(oggetto, doc, objectElement);
            }
    
            rootElement.appendChild(objectElement);
        }
    
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);
        }
    
        System.out.println("File XML scritto con successo: " + file.getName());
    } 

    private static void scriviOggettoXML(Object oggetto, Document doc, Element parentElement) throws IllegalAccessException {
        if (oggetto == null) return;
    
        Class<?> clazz = oggetto.getClass();
    
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) continue;
    
                field.setAccessible(true);
                Object valoreCampo = field.get(oggetto);
                if (valoreCampo == null) continue;
    
                Element campoElement = doc.createElement(field.getName());
    
                if (valoreCampo instanceof Collection<?>) {
                    for (Object item : (Collection<?>) valoreCampo) {
                        Element itemElement = doc.createElement("elemento");
                        itemElement.appendChild(doc.createTextNode(item.toString()));
                        campoElement.appendChild(itemElement);
                    }
    
                } else if (valoreCampo instanceof Elenco<?>) {
                    Elenco<?> elenco = (Elenco<?>) valoreCampo;
                    for (String key : elenco.getElenco().keySet()) {
                        Element itemElement = doc.createElement("elemento");
                        itemElement.appendChild(doc.createTextNode(key)); // Scrive solo la chiave
                        campoElement.appendChild(itemElement);
                    }
                }else if(valoreCampo instanceof TipoVisita){
                    campoElement.appendChild(doc.createTextNode(valoreCampo.toString()));
                }else if (valoreCampo instanceof LocalDate) {
                    campoElement.appendChild(doc.createTextNode(((LocalDate) valoreCampo).toString()));
                } else {
                    campoElement.appendChild(doc.createTextNode(valoreCampo.toString()));
                }
    
                parentElement.appendChild(campoElement);
            }
    
            clazz = clazz.getSuperclass();
        }
    }

    private static void scriviListaVisite(Object oggetto, Document doc, Element parentElement) throws IllegalAccessException {
    
        ListaVisite listaVisite = (ListaVisite) oggetto;
    
        Element chiaveElement = doc.createElement("chiave");
        chiaveElement.setTextContent(listaVisite.getChiave());
        parentElement.appendChild(chiaveElement);
    
        Element visiteElement = doc.createElement("visite");
    
        for (Visita visita : listaVisite.getVisite().getElenco().values()) {
            Element visitaElement = doc.createElement("visita");
    
            Element dataElement = doc.createElement("dataVisita");
            dataElement.setTextContent(visita.getDataVisita().toString());
            visitaElement.appendChild(dataElement);
    
            Element statoElement = doc.createElement("stato");
            statoElement.setTextContent(visita.getStato().toString());
            visitaElement.appendChild(statoElement);
    
            Element tipoElement = doc.createElement("tipo");
            tipoElement.setTextContent(visita.getTipo().toString());
            visitaElement.appendChild(tipoElement);

            Element iscrittiElement = doc.createElement("iscritti");
            iscrittiElement.setTextContent(String.valueOf(visita.getIscritti()));
            visitaElement.appendChild(iscrittiElement);

            Element iscrizioniElement = doc.createElement("iscrizioni");
            for (Prenotazione p : visita.getPrenotazioni().values()) {
                Element iscrizioneElement = doc.createElement("iscrizione");

                Element fruitorElement = doc.createElement("fruitore");
                fruitorElement.setTextContent(p.getFruitore().toString());
                iscrizioneElement.appendChild(fruitorElement);

                Element numPersoneElement = doc.createElement("numPersone");
                numPersoneElement.setTextContent(String.valueOf(p.getNumPartecipanti()));
                iscrizioneElement.appendChild(numPersoneElement);

                Element codiceElement = doc.createElement("codice");
                codiceElement.setTextContent(p.getCodice());
                iscrizioneElement.appendChild(codiceElement);


                iscrizioniElement.appendChild(iscrizioneElement);
            }
            visitaElement.appendChild(iscrizioniElement);
            
            visiteElement.appendChild(visitaElement);
        }
    
        parentElement.appendChild(visiteElement);
    }
    



    public static void scriviXML(File file, String[] dati, String[] campi, String contesto) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();


        Element rootElement = doc.createElement(contesto);
        doc.appendChild(rootElement);

        for(int i = 0; i < dati.length; i++){

            Element e = doc.createElement(campi[i]);
            e.appendChild(doc.createTextNode(dati[i]));
            rootElement.appendChild(e);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }

    private static void rimuoviNodiVuoti(Node doc) {
        NodeList lista = doc.getChildNodes();
        for (int i = lista.getLength() - 1; i >= 0; i--) {
            Node child = lista.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty()) {
                doc.removeChild(child);
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                rimuoviNodiVuoti(child); 
            }
        }

    }

    public static Elenco<Visita> leggiArchivioXML(File file, Elenco<TipoVisita> elencoTV, Elenco<Fruitore> elencoF) throws Exception {
        Elenco<Visita> archivio = new Elenco<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList listaVisiteNodes = doc.getElementsByTagName("visita");
        for (int i = 0; i < listaVisiteNodes.getLength(); i++) {
            Element listaVisiteElem = (Element) listaVisiteNodes.item(i);

            StatiVisita stato = creaStato(listaVisiteElem.getElementsByTagName("stato").item(0).getTextContent());
            LocalDate data = LocalDate.parse(listaVisiteElem.getElementsByTagName("dataVisita").item(0).getTextContent());
            String tipoElem = listaVisiteElem.getElementsByTagName("tipo").item(0).getTextContent();
            TipoVisita tipo = elencoTV.getElementByKey(tipoElem);
            HashMap<Fruitore, Prenotazione> prenotazioni = new HashMap<>();
            NodeList iscrizioniNodes = ((Element) listaVisiteElem.getElementsByTagName("iscrizioni").item(0)).getElementsByTagName("iscrizione");
            for (int j = 0; j < iscrizioniNodes.getLength(); j++) {
                Element iscrizioneElem = (Element) iscrizioniNodes.item(j);

                String fruitoreStr = iscrizioneElem.getElementsByTagName("fruitore").item(0).getTextContent();
                String numStr = iscrizioneElem.getElementsByTagName("numPersone").item(0).getTextContent();
                NodeList codiceNode = iscrizioneElem.getElementsByTagName("codice");
                String codice = codiceNode.getLength() > 0 ? codiceNode.item(0).getTextContent() : null;

                Fruitore f = elencoF.getElementByKey(fruitoreStr);
                if (f != null) {
                    if(codice == null)
                        prenotazioni.put(f, new Prenotazione(f, Integer.parseInt(numStr)));
                    else
                        prenotazioni.put(f, new Prenotazione(codice, f, Integer.parseInt(numStr)));
                }
            }
            int iscritti = Integer.parseInt(listaVisiteElem.getElementsByTagName("iscritti").item(0).getTextContent());
            Visita visita = new Visita(data, tipo, stato, prenotazioni, iscritti);
            archivio.aggiungi(visita);
            }
        
        return archivio;
    }

    public static void scriviArchivioXML(File file, Elenco<Visita> archivio) throws Exception {
                
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
    
        Element visiteElement = doc.createElement("visite");
    
        for (Visita visita : archivio.getElenco().values()) {
            Element visitaElement = doc.createElement("visita");
    
            Element dataElement = doc.createElement("dataVisita");
            dataElement.setTextContent(visita.getDataVisita().toString());
            visitaElement.appendChild(dataElement);
    
            Element statoElement = doc.createElement("stato");
            statoElement.setTextContent(visita.getStato().toString());
            visitaElement.appendChild(statoElement);
    
            Element tipoElement = doc.createElement("tipo");
            tipoElement.setTextContent(visita.getTipo().toString());
            visitaElement.appendChild(tipoElement);

            Element iscrittiElement = doc.createElement("iscritti");
            iscrittiElement.setTextContent(String.valueOf(visita.getIscritti()));
            visitaElement.appendChild(iscrittiElement);

            Element iscrizioniElement = doc.createElement("iscrizioni");
            for (Prenotazione p : visita.getPrenotazioni().values()) {
                Element iscrizioneElement = doc.createElement("iscrizione");

                Element fruitorElement = doc.createElement("fruitore");
                fruitorElement.setTextContent(p.getFruitore().toString());
                iscrizioneElement.appendChild(fruitorElement);

                Element numPersoneElement = doc.createElement("numPersone");
                numPersoneElement.setTextContent(String.valueOf(p.getNumPartecipanti()));
                iscrizioneElement.appendChild(numPersoneElement);

                Element codiceElement = doc.createElement("codice");
                codiceElement.setTextContent(p.getCodice());
                iscrizioneElement.appendChild(codiceElement);

                iscrizioniElement.appendChild(iscrizioneElement);
            }
            visitaElement.appendChild(iscrizioniElement);
            
    
            visiteElement.appendChild(visitaElement);
        }

        doc.appendChild(visiteElement);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);
        }
    
        System.out.println("File XML scritto con successo: " + file.getName());
    }
}