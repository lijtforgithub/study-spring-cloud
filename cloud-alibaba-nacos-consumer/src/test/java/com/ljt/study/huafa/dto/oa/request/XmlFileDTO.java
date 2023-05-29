package com.ljt.study.huafa.dto.oa.request;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author LiJingTang
 * @date 2023-05-25 18:41
 */
@Data
@XmlRootElement(name = "files")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlFileDTO {

    private List<XmlUrl> urls;



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
