package com.ljt.study.huafa.dto.oa.request;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author LiJingTang
 * @date 2023-06-12 08:33
 */
@Data
public class FlowFormXml {

    private List<Field> fieldList;
    private List<FormSon> sonList;


    public String toXml() {
        StringBuilder builder = new StringBuilder("<flow>");
        builder.append("<fieldList>");
        if (Objects.nonNull(fieldList)) {
            fieldList.forEach(field -> {
                builder.append(field.toXml());
            });
        }
        builder.append("</fieldList>");

        if (Objects.nonNull(sonList)) {
            sonList.forEach(formSon -> builder.append(formSon.toXml()));
        }

        return builder.append("</flow>").toString();
    }

    @Data
    public static class FormSon {

        private List<Son> sonList;

        public String toXml() {
            StringBuilder builder = new StringBuilder();
            builder.append("<formson>");
            if (Objects.nonNull(sonList)) {
                sonList.forEach(son -> builder.append(son.toXml()));
            }
            builder.append("</formson>");

            return builder.toString();
        }
    }

    @Data
    public static class Son {

        private String id;

        private List<Field> fieldList;

        public String toXml() {
            StringBuilder builder = new StringBuilder();
            builder.append("<formson_").append(id).append("/>")
                    .append("<fieldList>");
            if (Objects.nonNull(fieldList)) {
                fieldList.forEach(field -> {
                    builder.append(field.toXml());
                });
            }
            builder.append("</fieldList>");

            return builder.toString();
        }
    }


    @Data
    public static class Field {

        /**
         * 编号
         */
        private String id;
        private String value;

        public String toXml() {
            return "<field" + id + ">" + StrUtil.trimToEmpty(value) + "</field" + id + ">";
        }
    }

}
