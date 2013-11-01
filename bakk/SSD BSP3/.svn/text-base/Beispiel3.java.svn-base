import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/*
 * Semistrukturierte Daten - SS 2009
 * Uebungsbeispiel 3
 */

public class Beispiel3 
{	
	private ArrayList<Node> processingInstructions;
	private int anzAttribute;
	private int anzElemente;

	public static void main(String[] args) 
	{
		// Argumentueberpruefung
		if (args.length != 1) 
		{
			System.err.println("Usage: java Beispiel3 <XML-File>");
			System.exit(1);
		}
		
		String xmlInput = args[0];
		
		Beispiel3 beispiel = new Beispiel3();
		
		beispiel.dom(xmlInput);
		beispiel.sax(xmlInput);
		beispiel.domsax(xmlInput);
	}
	
	/**
	 * Vervollstaendigen Sie die Methode. Der Name des XML-Files, welches verarbeitet werden soll,
	 * wird mittels Parameter "xmlInput" uebergeben.
	 *
	 * Verwenden Sie fuer die Loesung dieser Teilaufgabe einen DOM-Baum.
	 */
	private void dom(String xmlInput) 
	{	
		String nodeName = "";
		NodeList nl;
		Node subnode;
		
		anzElemente = 0;
		anzAttribute = 0;
		
		try {
			//Erzeugen des DOM Baums
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(xmlInput));
			
			//Erzeugen der ArrayList zum speichern der Processing Instructions
			processingInstructions = new ArrayList<Node>();
			
			//Operationen an den Knoten durchfuehren
			operateTreeElements(document, addNodesToArrayList(document.getChildNodes()));
			
			//Processing Instructions an das Ende anfuegen
			addProcessingInstructionsToEnd(document);
			
			//Anzahl Elemente und Attribute ausgeben
			System.out.println("Anzahl Elemente: " + anzElemente);
			System.out.println("Anzahl Attribute: " + anzAttribute);
			System.out.println();
			
			//Ausgabe 
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("beispiel3-dom.xml"));
			transformer.transform(source, result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Vervollstaendigen Sie die Methode. Der Name des XML-Files, welches verarbeitet werden soll,
	 * wird mittels Parameter "xmlInput" uebergeben.
	 * 
	 * Verwenden Sie fuer die Loesung dieser Teilaufgabe einen SAX Prozessor.
	 */
	private void sax(String xmlInput) 
	{	
		// @TODO Hier Ihr Code	
	}
	
	
	
	/**
	 * Vervollstaendigen Sie die Methode. Der Name des XML-Files, welches verarbeitet werden soll,
	 * wird mittels Parameter "xmlInput" uebergeben.
	 */
	private void domsax (String xmlInput) 
	{		
		// @TODO Hier Ihr Code	
	}
	
	
	/**
	* Mehode um einen gegebenen String um zu drehen und verkehr zurück 
	* zu geben
	* @param input String
	* @return verkehrter String
	*/
	private String reverseString(String input){
		char[] inputArray;
		char[] outputArray;
		
		inputArray = input.toCharArray();
		outputArray = new char[inputArray.length];
		
		for(int i = 0; i < inputArray.length; i++)
		{
			outputArray[i] = inputArray[inputArray.length - 1 - i];
		}
		
		return new String(outputArray);
	}
	
	/**
	* Methode welche die Operationen an allen Knoten durchfuehrt
	* @param document Document
	* @param children ArrayList mit den Kindknoten/Attributen
	*/
	private void operateTreeElements(Document document, ArrayList children)
	{
		Node node;
		String prefix;
		String namespaceURI;
		
		//Alle Kinder pruefen
		for(int i = 0; i < children.size(); i++)
		{
			node = (Node)children.get(i);
			
			//Pruefen ob Element
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				//Anzahl erhoehen
				anzElemente ++;
				//Namen umdrehen
				prefix = node.getPrefix();
				namespaceURI = node.getNamespaceURI();
				document.renameNode(node, namespaceURI, reverseString(node.getLocalName()));
				node.setPrefix(prefix);
				//Kindknoten weiter machen
				operateTreeElements(document, addNodesToArrayList(node.getChildNodes()));
				//Attribute weiter machen
				operateTreeElements(document, addAttributesToArrayList(node.getAttributes()));
			}
			
			//Pruefen ob Attribut
			if(node.getNodeType() == Node.ATTRIBUTE_NODE)
			{
				if(!node.getNodeName().startsWith("xmlns"))
				{
					//Anzahl erhoehen
					anzAttribute++;
					//Namen auf Kleinbuchstaben aendern
					prefix = node.getPrefix();
					namespaceURI = node.getNamespaceURI();
					document.renameNode(node, namespaceURI, node.getLocalName().toLowerCase() + "77");
					node.setPrefix(prefix);
				}
			}
			
			//Pruefen ob Kommentar
			if(node.getNodeType() == Node.COMMENT_NODE)
			{
				node.getParentNode().removeChild(node);
			}
			
			//Pruefen ob Processing Instruction
			if(node.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE)
			{
				processingInstructions.add(node);
				node.getParentNode().removeChild(node);
			}
		}
	}
	
	/**
	* Methode welche die Kindknoten zur ArraList hinzufuegt
	* @param nodes Kindknoten
	* @return ArrayList mit Kindknoten
	*/
	private ArrayList<Node> addNodesToArrayList(NodeList nodes)
	{
		ArrayList<Node> children = new ArrayList<Node>();
		
		//Knoten in ArrayListe einfuegen
		for(int i = 0; i < nodes.getLength(); i++)
		{
			children.add(nodes.item(i));
		}
		
		return children;
	}
	
	/**
	* Methode welche die Attribute zur ArrayList hinzufuegt
	* @param attributes Attribute
	* @return ArrayList mit Attributen
	*/
	private ArrayList<Node> addAttributesToArrayList(NamedNodeMap attributes)
	{
		ArrayList<Node> attributeNodes = new ArrayList<Node>();
		
		//Attribute in ArrayList einfuegen
		for(int i = 0; i < attributes.getLength(); i++)
		{
			attributeNodes.add(attributes.item(i));
		}
		
		return attributeNodes;
	}
	
	/**
	* Methode welche die Processing Instruction anfuegt
	* @param document Document
	*/
	private void addProcessingInstructionsToEnd(Document document)
	{
		for(int i = 0; i < processingInstructions.size(); i++)
		{
			document.appendChild(processingInstructions.get(i));
		}
	}
	
	private String reversePrefixedString(String qName, String localName)
	{
		String prefix = null;
		String[] result;
		
		if(qName.contains(":"))
		{
			result = qName.split(":");
			prefix = result[0];
		}
		
		if(prefix == null)
		{
			return reverseString(localName);
		}
		else
		{
			return prefix + ":" + reverseString(localName);
		}
	}
	
	private String lowerPrefixedAttr(String qName, String localName)
	{
		String prefix = null;
		String[] result;
		
		if(qName.contains("xmlns"))
		{
			return qName;
		}
		if(qName.contains(":"))
		{
			result = qName.split(":");
			prefix = result[0];
		}
		
		if(prefix == null)
		{
			return localName.toLowerCase();
		}
		else
		{
			return prefix + ":" + localName.toLowerCase();
		}	
	}

}


