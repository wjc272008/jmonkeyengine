/**
 * blend_equation_separateType.java
 *
 * This file was generated by XMLSpy 2007sp2 Enterprise Edition.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the XMLSpy Documentation for further details.
 * http://www.altova.com/xmlspy
 */


package com.jmex.model.collada.schema;


public class blend_equation_separateType extends com.jmex.xml.xml.Node {

	public blend_equation_separateType(blend_equation_separateType node) {
		super(node);
	}

	public blend_equation_separateType(org.w3c.dom.Node node) {
		super(node);
	}

	public blend_equation_separateType(org.w3c.dom.Document doc) {
		super(doc);
	}

	public blend_equation_separateType(com.jmex.xml.xml.Document doc, String namespaceURI, String prefix, String name) {
		super(doc, namespaceURI, prefix, name);
	}
	
	public void adjustPrefix() {
		for (	org.w3c.dom.Node tmpNode = getDomFirstChild( Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb" );
				tmpNode != null;
				tmpNode = getDomNextChild( Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb", tmpNode )
			) {
			internalAdjustPrefix(tmpNode, true);
			new rgbType(tmpNode).adjustPrefix();
		}
		for (	org.w3c.dom.Node tmpNode = getDomFirstChild( Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha" );
				tmpNode != null;
				tmpNode = getDomNextChild( Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha", tmpNode )
			) {
			internalAdjustPrefix(tmpNode, true);
			new alphaType(tmpNode).adjustPrefix();
		}
	}
	public void setXsiType() {
 		org.w3c.dom.Element el = (org.w3c.dom.Element) domNode;
		el.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "blend_equation_separate");
	}

	public static int getrgbMinCount() {
		return 1;
	}

	public static int getrgbMaxCount() {
		return 1;
	}

	public int getrgbCount() {
		return getDomChildCount(Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb");
	}

	public boolean hasrgb() {
		return hasDomChild(Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb");
	}

	public rgbType newrgb() {
		return new rgbType(domNode.getOwnerDocument().createElementNS("http://www.collada.org/2005/11/COLLADASchema", "rgb"));
	}

	public rgbType getrgbAt(int index) throws Exception {
		return new rgbType(getDomChildAt(Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb", index));
	}

	public org.w3c.dom.Node getStartingrgbCursor() throws Exception {
		return getDomFirstChild(Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb" );
	}

	public org.w3c.dom.Node getAdvancedrgbCursor( org.w3c.dom.Node curNode ) throws Exception {
		return getDomNextChild( Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb", curNode );
	}

	public rgbType getrgbValueAtCursor( org.w3c.dom.Node curNode ) throws Exception {
		if( curNode == null )
			throw new com.jmex.xml.xml.XmlException("Out of range");
		else
			return new rgbType(curNode);
	}

	public rgbType getrgb() throws Exception 
 {
		return getrgbAt(0);
	}

	public void removergbAt(int index) {
		removeDomChildAt(Element, "http://www.collada.org/2005/11/COLLADASchema", "rgb", index);
	}

	public void removergb() {
		removergbAt(0);
	}

	public org.w3c.dom.Node addrgb(rgbType value) {
		return appendDomElement("http://www.collada.org/2005/11/COLLADASchema", "rgb", value);
	}

	public void insertrgbAt(rgbType value, int index) {
		insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema", "rgb", index, value);
	}

	public void replacergbAt(rgbType value, int index) {
		replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema", "rgb", index, value);
	}

	public static int getalphaMinCount() {
		return 1;
	}

	public static int getalphaMaxCount() {
		return 1;
	}

	public int getalphaCount() {
		return getDomChildCount(Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha");
	}

	public boolean hasalpha() {
		return hasDomChild(Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha");
	}

	public alphaType newalpha() {
		return new alphaType(domNode.getOwnerDocument().createElementNS("http://www.collada.org/2005/11/COLLADASchema", "alpha"));
	}

	public alphaType getalphaAt(int index) throws Exception {
		return new alphaType(getDomChildAt(Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha", index));
	}

	public org.w3c.dom.Node getStartingalphaCursor() throws Exception {
		return getDomFirstChild(Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha" );
	}

	public org.w3c.dom.Node getAdvancedalphaCursor( org.w3c.dom.Node curNode ) throws Exception {
		return getDomNextChild( Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha", curNode );
	}

	public alphaType getalphaValueAtCursor( org.w3c.dom.Node curNode ) throws Exception {
		if( curNode == null )
			throw new com.jmex.xml.xml.XmlException("Out of range");
		else
			return new alphaType(curNode);
	}

	public alphaType getalpha() throws Exception 
 {
		return getalphaAt(0);
	}

	public void removealphaAt(int index) {
		removeDomChildAt(Element, "http://www.collada.org/2005/11/COLLADASchema", "alpha", index);
	}

	public void removealpha() {
		removealphaAt(0);
	}

	public org.w3c.dom.Node addalpha(alphaType value) {
		return appendDomElement("http://www.collada.org/2005/11/COLLADASchema", "alpha", value);
	}

	public void insertalphaAt(alphaType value, int index) {
		insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema", "alpha", index, value);
	}

	public void replacealphaAt(alphaType value, int index) {
		replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema", "alpha", index, value);
	}

}