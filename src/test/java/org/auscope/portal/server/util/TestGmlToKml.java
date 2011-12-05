package org.auscope.portal.server.util;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.auscope.portal.PropertiesMatcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGmlToKml {

    private Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private ServletContext mockServletContext = context.mock(ServletContext.class);
    private PortalXSLTTransformer mockTransformer = context.mock(PortalXSLTTransformer.class);
    private InputStream mockInputStream = context.mock(InputStream.class);
    private GmlToKml gmlToKml;

    @Before
    public void setup() {
        gmlToKml = new GmlToKml(mockTransformer, mockServletContext);
    }

    private PropertiesMatcher aProperty(String property, String value) {
        Properties prop = new Properties();
        prop.setProperty(property, value);
        return new PropertiesMatcher(prop);
    }

    /**
     * Tests that the transform is setup correctly
     */
    @Test
    public void testTransform() {
        final String inputXml = "<input/>";
        final String outputXml = "<output/>";
        final String serviceUrl = "http://service/wfs";


        context.checking(new Expectations() {{
            oneOf(mockServletContext).getResourceAsStream(GmlToKml.GML_TO_KML_XSLT);will(returnValue(mockInputStream));
            oneOf(mockTransformer).convert(with(equal(inputXml)), with(same(mockInputStream)), with(aProperty("serviceURL", serviceUrl)));will(returnValue(outputXml));
        }});

        final String result = gmlToKml.convert(inputXml, serviceUrl);
        Assert.assertEquals(outputXml, result);
    }

    /**
     * Tests that the transform returns "" on failure
     */
    @Test
    public void testFileDNE() {
        final String inputXml = "<input/>";
        final String outputXml = "";
        final String serviceUrl = "http://service/wfs";


        context.checking(new Expectations() {{
            oneOf(mockServletContext).getResourceAsStream(GmlToKml.GML_TO_KML_XSLT);will(returnValue(null));
        }});

        final String result = gmlToKml.convert(inputXml, serviceUrl);
        Assert.assertEquals(outputXml, result);
    }

}
