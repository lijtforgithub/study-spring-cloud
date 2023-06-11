package com.ljt.study.huafa.dto.oa.response;

import com.ljt.study.huafa.dto.NonJSON;
import com.ljt.study.huafa.dto.oa.OABaseResponse;
import com.ljt.study.huafa.exception.ClientException;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LiJingTang
 * @date 2023-06-11 13:16
 */
@Data
@NonJSON
@NoArgsConstructor
public class FlowDetailResponse extends OABaseResponse {

    private String flowId;
    private String flowName;
    private List<Element> formList;
    private List<Element> subFormList;


    public FlowDetailResponse(String body) {
        FlowDetailXml obj = parse(body);
        this.flowId = obj.getSummary().getId();
        this.flowName = obj.getSummary().getName();
        this.formList = getFromList(obj);
        this.subFormList = getSubFromList(obj);


    }

    private static List<Element> getFromList(FlowDetailXml obj) {
        return getElementList(obj.getDefinitions(), obj.getValues());
    }

    private static List<Element> getElementList(List<FlowDetailXml.DefinitionsColumn> definitions, List<FlowDetailXml.ValuesColumn> valuesColumns) {
        List<Element> list = new ArrayList<>(definitions.size());
        Map<String, String> map = valuesColumns.stream().collect(Collectors.toMap(FlowDetailXml.ValuesColumn::getName, FlowDetailXml.ValuesColumn::getValue));

        for (FlowDetailXml.DefinitionsColumn definition : definitions) {
            Element element = new Element();
            element.setId(definition.getId());
            element.setName(definition.getName());
            element.setValue(map.get(definition.getName()));
            list.add(element);
        }

        return list;
    }

    private static List<Element> getSubFromList(FlowDetailXml obj) {
        List<Element> list = new ArrayList<>(obj.getDefinitions().size());

        for (FlowDetailXml.SubForm subForm : obj.getSubForms()) {
            List<FlowDetailXml.DefinitionsColumn> definitions = subForm.getDefinitions();
            List<FlowDetailXml.ValuesColumn> valuesColumns = subForm.getValues().stream().flatMap(row -> row.getRows().stream()).collect(Collectors.toList());

            list.addAll(getElementList(definitions, valuesColumns));
        }

        return list;
    }

    private static FlowDetailXml parse(String xml) {
        try {

            JAXBContext context = JAXBContext.newInstance(FlowDetailXml.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object object = unmarshaller.unmarshal(new StringReader(xml));
            return (FlowDetailXml) object;
        } catch (JAXBException e) {
            throw new ClientException(e.getMessage());
        }
    }


    @Data
    public static class Element {
        private String id;
        private String name;
        private String value;
    }

}
