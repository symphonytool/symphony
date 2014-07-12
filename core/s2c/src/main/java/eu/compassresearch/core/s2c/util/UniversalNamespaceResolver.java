package eu.compassresearch.core.s2c.util;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import org.w3c.dom.Document;

/**
 * Got this from {@link "http://www.ibm.com/developerworks/library/x-nmspccontext/"} they also have an optimized one
 * 
 * @author kel
 */
public class UniversalNamespaceResolver implements NamespaceContext
{
	// the delegate
	private Document sourceDocument;

	/**
	 * This constructor stores the source document to search the namespaces in it.
	 * 
	 * @param document
	 *            source document
	 */
	public UniversalNamespaceResolver(Document document)
	{
		sourceDocument = document;
	}

	/**
	 * The lookup for the namespace uris is delegated to the stored document.
	 * 
	 * @param prefix
	 *            to search for
	 * @return uri
	 */
	public String getNamespaceURI(String prefix)
	{
		if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
		{
			return sourceDocument.lookupNamespaceURI(null);
		} else
		{
			return sourceDocument.lookupNamespaceURI(prefix);
		}
	}

	/**
	 * This method is not needed in this context, but can be implemented in a similar way.
	 * 
	 * @param namespaceURI
	 * @return
	 */
	public String getPrefix(String namespaceURI)
	{
		return sourceDocument.lookupPrefix(namespaceURI);
	}

	@SuppressWarnings("rawtypes")
	public Iterator getPrefixes(String namespaceURI)
	{
		// not implemented yet
		return null;
	}

}
