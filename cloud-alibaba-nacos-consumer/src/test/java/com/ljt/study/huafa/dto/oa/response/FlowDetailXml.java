package com.ljt.study.huafa.dto.oa.response;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiJingTang
 * @date 2023-06-11 14:39
 */
@Data
@XmlRootElement(name = "formExport")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlowDetailXml {

    @XmlElement
    private Summary summary;

    @XmlElementWrapper(name = "definitions")
    @XmlElement(name = "column")
    private List<DefinitionsColumn> definitions;

    @XmlElementWrapper(name = "values")
    @XmlElement(name = "column")
    private List<ValuesColumn> values;

    @XmlElementWrapper(name = "subForms")
    @XmlElement(name = "subForm")
    private List<SubForm> subForms;



    @Data
    @XmlRootElement(name = "summary")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Summary {

        @XmlAttribute
        private String id;
        @XmlAttribute
        private String name;

    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SubForm {

        @XmlElementWrapper(name = "definitions")
        @XmlElement(name = "column")
        private List<DefinitionsColumn> definitions;

        @XmlElementWrapper(name = "values")
        @XmlElement(name = "row")
        private List<ValuesRow> values;

    }


    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ValuesRow {

        @XmlElement(name = "column")
        private List<ValuesColumn> rows;

    }



    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DefinitionsColumn {

        @XmlAttribute
        private String id;
        @XmlAttribute
        private String name;

    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ValuesColumn {

        @XmlAttribute
        private String name;

        @XmlElement
        @XmlJavaTypeAdapter(CDATAAdapter.class)
        private String value;
    }



    public static class CDATAAdapter extends XmlAdapter<String, String> {

        private static final Pattern P1 = Pattern.compile("<!\\[CDATA\\[(.*?)\\]\\]>");

        @Override
        public String marshal(String value) throws Exception {
            return "<![CDATA[" + value + "]]>";
        }

        @Override
        public String unmarshal(String value) throws Exception {
            if (StrUtil.isBlank(value)) {
                return value;
            }

            Matcher m = P1.matcher(value);
            if (m.find()) {
                return m.replaceAll("$1");
            }

            return value;
        }

    }

}
