package com.ljt.study.huafa.dto.oa.request;

import lombok.Data;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.StringWriter;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-25 18:41
 */
@Data
@XmlRootElement(name = "files")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlowFileXml {

    private List<XmlUrl> urls;


    public String toXml() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(this, writer);
        return writer.toString();
    }


    @Data
    @XmlRootElement(name = "url")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class XmlUrl {

        @XmlAttribute
        private String name;

        @XmlValue
        private String value;

    }

}
