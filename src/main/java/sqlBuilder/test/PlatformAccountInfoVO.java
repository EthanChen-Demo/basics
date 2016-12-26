package sqlBuilder.test;

import sqlBuilder.ApiModelProperty;
import sqlBuilder.SqlField;
import sqlBuilder.SqlTable;

@SqlTable("platform_account_info")
public class PlatformAccountInfoVO extends BaseVo {

	private static final long serialVersionUID = 1L;

	private int id;
	/**
	 * 账户（商家）所在的省份
	 */
	private String provinceName;
	/**
	 * 账户（商家）所在的城市
	 */
	private String cityName;

	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 平台:账号所在的应用平台。
	 */
	private String platform;

	@SqlField("account_name")
	@ApiModelProperty(value = "账号名称")
	private String accountName;

	@SqlField("account_type")
	@ApiModelProperty(value = "账号类型")
	private String accountType;

	@SqlField("account_service")
	@ApiModelProperty(value = "账号套餐类型")
	private String accountService;

	@SqlField("account_balance")
	@ApiModelProperty(value = "账户余额")
	private Double accountBalance;

	@SqlField("account_source_clue")
	@ApiModelProperty(value = "车源推荐次数")
	private String accountSourceClue;

	@SqlField("account_car")
	@ApiModelProperty(value = "我的车源数")
	private int accountCar;

	@SqlField("account_add_clue")
	@ApiModelProperty(value = "新增商机")
	private int accountAddClue;
	
	/**
	 * 数据清洗的次数。
	 */
	@SqlField("clean_counter")
	@ApiModelProperty(value = "数据清洗的次数")
	private int cleanedCounter = 0;

	public int getCleanedCounter() {
		return cleanedCounter;
	}

	public void setCleanedCounter(int cleanedCounter) {
		this.cleanedCounter = cleanedCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountService() {
		return accountService;
	}

	public void setAccountService(String accountService) {
		this.accountService = accountService;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountSourceClue() {
		return accountSourceClue;
	}

	public void setAccountSourceClue(String accountSourceClue) {
		this.accountSourceClue = accountSourceClue;
	}

	public int getAccountCar() {
		return accountCar;
	}

	public void setAccountCar(int accountCar) {
		this.accountCar = accountCar;
	}

	public int getAccountAddClue() {
		return accountAddClue;
	}

	public void setAccountAddClue(int accountAddClue) {
		this.accountAddClue = accountAddClue;
	}

	@Override
	public String toString() {
		return "PlatformAccountInfoVO [id=" + id  + ", platform=" + platform + ", accountName="
				+ accountName + ", accountType=" + accountType + ", accountService=" + accountService
				+ ", accountBalance=" + accountBalance + ", accountSourceClue=" + accountSourceClue + ", accountCar="
				+ accountCar + ", accountAddClue=" + accountAddClue + "]";
	}

}

