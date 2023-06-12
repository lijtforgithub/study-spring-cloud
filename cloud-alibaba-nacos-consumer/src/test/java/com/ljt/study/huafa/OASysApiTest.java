package com.ljt.study.huafa;

import com.alibaba.fastjson.JSON;
import com.ljt.study.huafa.api.OASysApi;
import com.ljt.study.huafa.client.OAFtpClient;
import com.ljt.study.huafa.dto.oa.request.FlowFileXml;
import com.ljt.study.huafa.dto.oa.request.FlowFormXml;
import com.ljt.study.huafa.dto.oa.request.StartFlowRequest;
import com.ljt.study.huafa.dto.oa.request.StartFlowSimpleRequest;
import com.ljt.study.huafa.dto.oa.response.FlowDetailResponse;
import com.ljt.study.huafa.dto.oa.response.FlowStatusResponse;
import com.ljt.study.huafa.dto.oa.response.StartFlowResponse;
import com.ljt.study.huafa.ldap.idm.service.IdmPersonService;
import com.ljt.study.huafa.prop.OAProperties;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2023-05-24 17:30
 */
@Slf4j
@SpringBootTest
class OASysApiTest {

    @Autowired
    private OASysApi oaSysApi;
    @Autowired
    private OAProperties oaProperties;
    @Autowired
    private OAFtpClient oaFtpClient;
    @Autowired
    private IdmPersonService idmPersonService;

    @Test
    void startFlow() {
        StartFlowRequest request = new StartFlowRequest();
        request.setSystemName(oaProperties.getSystemName());
        request.setLoginName("zhuxiaoren");
//        String ou = idmPersonService.getOneByUsername(request.getLoginName()).getOu();
//        System.out.println(ou);
//        request.setAccountName(ou);
        request.setAuthCode(DigestUtils.md5DigestAsHex((request.getSystemName() + request.getLoginName()).getBytes()));
        request.setFlowCode("KFS05");
        request.setSubject("关于工程整改责任单位确认书ZR220525000004");
        request.setSendState("0");
        request.setVersion("IDM");
        request.setDatas("<flow><fieldList><field0023></field0023><field0024>2022-05-25 15:11:05</field0024><field0025> </field0025><field0026>珠海大区</field0026><field0027>珠海西区</field0027><field0028>ZR220525000004</field0028><field0029>珠海华发依山郡</field0029><field0030>一期</field0030><field0031>101</field0031><field0032>住宅-一般住宅-高层（12层以上，100米以内）</field0032><field0033>杨晓秀</field0033><field0034>13417857969</field0034><field0041> </field0041><field0042>3000.00</field0042><field0043>5</field0043><field0047>珠海建�易笆喂こ逃邢薰�司</field0047><field0048> </field0048><field0049>1000014010</field0049><field0050>2023-03-20 00:00:00</field0050><field0051>否</field0051><field0052></field0052><field0053>41栋101业主报修厨房渗漏水，因中建四局第一建设有限公司现场无法安排人员维修，导致报修事项无法顺利开展处理，现我司将启用第三方专业单位代为整改，维修所需一切费用从中建四局第一建设有限公司保修款中扣除。</field0053></fieldList><formson><formson_36776/><fieldList><field0035>报修工单</field0035><field0036>2022-05-17 10:33:30</field0036><field0037>BX220517035546</field0037><field0038>厨房</field0038><field0039>土建工程|渗漏水|门窗渗漏|其他（请补充说明）</field0039><field0040></field0040></fieldList><formson_36777/><fieldList><field0044>中建四局第一建设有限公司</field0044><field0045>分担比例:100|费用:3000.00</field0045><field0046>1000001084</field0046></fieldList></formson></flow>");
//        request.setFiles("<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?><files><url name=\\\"工程整改责任确认单-20220525150957.pdf\\\">//gfcsp/1653462664835l96o1.pdf</url><url name=\\\"2022-5-15--FJ20220910 （关于华发依山郡一期二标室内渗漏水的问题） 扫描件-20220525151006.pdf\\\">//gfcsp/1653462664837z624b.pdf</url></files>");

        StartFlowResponse response = oaSysApi.startFlow(request);

        log.info(JSON.toJSONString(response));
    }

//    @Test
    void startFlowSimple() throws IOException {
        Map<String, InputStream> fileMap = new HashMap<>();
        fileMap.put("test.json", Files.newInputStream(Paths.get("/Users/lijingtang/Documents/test.json")));

        StartFlowSimpleRequest request = new StartFlowSimpleRequest();
        request.setLoginName("zhuxiaoren");
        String ou = idmPersonService.getOneByUsername(request.getLoginName()).getOu();
        request.setAccountName(ou);
        request.setFlowCode("KFS05");
        request.setSubject("关于工程整改责任单位确认书ZR220525000004");
        request.setFlowForm(getFlowFormXml());
        request.setFlowFile(fileMap);

        StartFlowResponse response = oaSysApi.startFlow(request);

        log.info(JSON.toJSONString(response));
    }

    @Test
    void getFlowDetail() {
        FlowDetailResponse response = oaSysApi.getFlowDetail("-1684709141644885585");

        log.info(JSON.toJSONString(response));
    }

    @Test
    void getFlowStatus() {
        FlowStatusResponse response = oaSysApi.getFlowStatus("-1684709141644885585");

        log.info(JSON.toJSONString(response));
    }

    @Test
    void testFtp() throws IOException {
        Path path = Paths.get("/Users/lijingtang/Documents/test.json");
        boolean success = oaFtpClient.upload("/202306/test.json", Files.newInputStream(path));
        log.info("上传结果：{}", success);
    }

    public static void main(String[] args) throws JAXBException {
        FlowFormXml formXml = getFlowFormXml();
        log.info("{}", formXml.toXml());


        FlowFileXml fileXml = new FlowFileXml();
        FlowFileXml.XmlUrl url1 = new FlowFileXml.XmlUrl();
        url1.setName("file1");
        url1.setValue("http://f1");
        FlowFileXml.XmlUrl url2 = new FlowFileXml.XmlUrl();
        url2.setName("file2");
        url2.setValue("http://f2");
        fileXml.setUrls(Lists.newArrayList(url1, url2));

        JAXBContext context = JAXBContext.newInstance(fileXml.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(fileXml, writer);
        log.info("{}", writer.toString().replaceAll(" {4}", "").replaceAll("\n", ""));
    }

    private static FlowFormXml getFlowFormXml() {
        FlowFormXml formXml = new FlowFormXml();
        FlowFormXml.Field field1 = new FlowFormXml.Field();
        field1.setId("0023");
        FlowFormXml.Field field2 = new FlowFormXml.Field();
        field2.setId("0024");
        field2.setValue("2022-05-25 15:11:05");
        formXml.setFieldList(Lists.newArrayList(field1, field2));

        FlowFormXml.FormSon formSon = new FlowFormXml.FormSon();
        FlowFormXml.Son son1 = new FlowFormXml.Son();
        son1.setId("36776");
        FlowFormXml.Field field3 = new FlowFormXml.Field();
        field3.setId("0035");
        field3.setValue("报修工单");
        FlowFormXml.Field field4 = new FlowFormXml.Field();
        field4.setId("0037");
        field4.setValue("BX220517035546");
        son1.setFieldList(Lists.newArrayList(field3, field4));
        FlowFormXml.Son son2 = new FlowFormXml.Son();
        son2.setId("36777");
        FlowFormXml.Field field5 = new FlowFormXml.Field();
        field5.setId("0044");
        field5.setValue("中建四局第一建设有限公司");
        FlowFormXml.Field field6 = new FlowFormXml.Field();
        field6.setId("0046");
        field6.setValue("1000001084");
        son2.setFieldList(Lists.newArrayList(field5, field6));
        formSon.setSonList(Lists.newArrayList(son1, son2));
        formXml.setSonList(Lists.newArrayList(formSon));
        return formXml;
    }

}
