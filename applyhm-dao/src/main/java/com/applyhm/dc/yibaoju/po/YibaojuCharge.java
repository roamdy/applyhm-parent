package com.applyhm.dc.yibaoju.po;

import java.io.Serializable;

public class YibaojuCharge implements Serializable{

    private static final long serialVersionUID = -310620690170578803L;

    private Integer id;				           // 主键
    private String tel;                         // 电话号码
    private String origin;                      // 来自哪个平台
	private String idCard;                     // 身份证
	private String userName;                   // 用户姓名
	private Integer sex;                       // 用户性别
	private Integer nation;                     // 民族
	private Integer personClass;                // 人员类别
	private String sickNo;                     // 病历号
	private String securityNo;                 // 社保号
	private String chargeNo;                   // 发票号
	private String hospital;                   // 住院医院ID
	private String hospitalNo;                 // 住院号
	private String inHospitalDay;                // 住院时间
	private String outHospitalDay;               // 出院时间
	private Double chargeNum;                  // 费用总额
	private String remark;                     // 备注
	private String createTime;                   // 创建时间
	private String input_user_img_1;           // 用户头像1
	private String input_user_img_2;           // 用户头像2
	private String input_fapiao_img_1;         // 发票1
	private String input_fapiao_img_2;         // 发票2
	private String input_fapiao_img_3;         // 发票3
	private String input_fapiao_img_4;         // 发票4
	private Integer status;                    // 审核状态
	private String baogao_url;                 // 报告
	private String overTime;                     // 修改时间
	private Integer ownerDanwei ;              // 医保局单位ID
	private Integer print_status;              // 打印状态
	private String pdfPath;                    // 文件本地路径

	//兼容字段
	private String hospitalName;
	private String yibaojuName;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public Integer getNation() {
        return nation;
    }
    public void setNation(Integer nation) {
        this.nation = nation;
    }
    public Integer getPersonClass() {
        return personClass;
    }
    public void setPersonClass(Integer personClass) {
        this.personClass = personClass;
    }
    public String getSickNo() {
        return sickNo;
    }
    public void setSickNo(String sickNo) {
        this.sickNo = sickNo;
    }
    public String getSecurityNo() {
        return securityNo;
    }
    public void setSecurityNo(String securityNo) {
        this.securityNo = securityNo;
    }
    public String getChargeNo() {
        return chargeNo;
    }
    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }
    public String getHospital() {
        return hospital;
    }
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
    public String getHospitalNo() {
        return hospitalNo;
    }
    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }
    public String getInHospitalDay() {
        return inHospitalDay;
    }
    public void setInHospitalDay(String inHospitalDay) {
        this.inHospitalDay = inHospitalDay;
    }
    public String getOutHospitalDay() {
        return outHospitalDay;
    }
    public void setOutHospitalDay(String outHospitalDay) {
        this.outHospitalDay = outHospitalDay;
    }
    public Double getChargeNum() {
        return chargeNum;
    }
    public void setChargeNum(Double chargeNum) {
        this.chargeNum = chargeNum;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getInput_user_img_1() {
        return input_user_img_1;
    }
    public void setInput_user_img_1(String input_user_img_1) {
        this.input_user_img_1 = input_user_img_1;
    }
    public String getInput_user_img_2() {
        return input_user_img_2;
    }
    public void setInput_user_img_2(String input_user_img_2) {
        this.input_user_img_2 = input_user_img_2;
    }
    public String getInput_fapiao_img_1() {
        return input_fapiao_img_1;
    }
    public void setInput_fapiao_img_1(String input_fapiao_img_1) {
        this.input_fapiao_img_1 = input_fapiao_img_1;
    }
    public String getInput_fapiao_img_2() {
        return input_fapiao_img_2;
    }
    public void setInput_fapiao_img_2(String input_fapiao_img_2) {
        this.input_fapiao_img_2 = input_fapiao_img_2;
    }
    public String getInput_fapiao_img_3() {
        return input_fapiao_img_3;
    }
    public void setInput_fapiao_img_3(String input_fapiao_img_3) {
        this.input_fapiao_img_3 = input_fapiao_img_3;
    }
    public String getInput_fapiao_img_4() {
        return input_fapiao_img_4;
    }
    public void setInput_fapiao_img_4(String input_fapiao_img_4) {
        this.input_fapiao_img_4 = input_fapiao_img_4;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getBaogao_url() {
        return baogao_url;
    }
    public void setBaogao_url(String baogao_url) {
        this.baogao_url = baogao_url;
    }
    public String getOverTime() {
        return overTime;
    }
    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }
    public Integer getOwnerDanwei() {
        return ownerDanwei;
    }
    public void setOwnerDanwei(Integer ownerDanwei) {
        this.ownerDanwei = ownerDanwei;
    }
    public Integer getPrint_status() {
        return print_status;
    }
    public void setPrint_status(Integer print_status) {
        this.print_status = print_status;
    }
    public String getHospitalName() {
        return hospitalName;
    }
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    public String getYibaojuName() {
        return yibaojuName;
    }
    public void setYibaojuName(String yibaojuName) {
        this.yibaojuName = yibaojuName;
    }
    public String getPdfPath() {
        return pdfPath;
    }
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
