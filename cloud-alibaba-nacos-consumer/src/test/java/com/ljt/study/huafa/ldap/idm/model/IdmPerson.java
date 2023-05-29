package com.ljt.study.huafa.ldap.idm.model;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;

/**
 * @author LiJingTang
 * @date 2023-05-25 09:27
 */
@Data
public final class IdmPerson {

    private String uid;

    private String employeeNumber;
    private String cn;
    private String mobile;
    private String ou;
    private String title;
    private String mail;
    private String departmentNumber;
    private String departmentName;

    @Attribute(name = "smart-type")
    private String smartType;
    @Attribute(name = "smart-status")
    private String smartStatus;
    @Attribute(name = "smart-order")
    private String smartOrder;
    @Attribute(name = "smart-gender")
    private String smartGender;
    @Attribute(name = "smart-alias")
    private String smartAlias;
    @Attribute(name = "smart-orgno-fullpath")
    private String smartOrgnoFullpath;
    @Attribute(name = "smart-orgname-fullpath")
    private String smartOrgnameFullpath;

    @Attribute(name = "customized-companyid")
    private String customizedCompanyid;
    @Attribute(name = "customized-dutyid")
    private String customizedDutyid;
    @Attribute(name = "customized-dutyname")
    private String customizedDutyname;
    @Attribute(name = "customized-positionid")
    private String customizedPositionid;
    @Attribute(name = "customized-idmparttime")
    private String customizedIdmparttime;

}
