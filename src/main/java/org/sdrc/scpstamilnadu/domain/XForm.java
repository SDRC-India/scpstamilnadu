package org.sdrc.scpstamilnadu.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



/**
 * This domain class or entity class will keep all the XForms.
 * @author Ratikanta Pradhan (ratikanta@sdrc.co.in)
 * @author Sarita
 * @since version 1.0.0.0
 *
 */
@Entity
public class XForm implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "FormId")
	private Integer formId;
	
    @Column(name = "XForm_Id", nullable = false)
	private String xFormId;
    
    @Column(name = "XForm_Title", nullable = false)
	private String xFormIdTitle;
    
	@Column(name = "RootElement")
	private String rootElement;
	
    @Column(name = "ODK_Server_URL", nullable = false)
	private String odkServerURL;
	
	@Column(name = "ODK_Username", nullable = false)
	private String username;
	
	@Column(name = "ODK_Password", nullable = false)
	private String password;
	
	@Column(name = "AreaXPath", nullable = false)
	private String areaXPath;
	
	@Column(name = "SecondaryAreaXPath")
	private String secondaryAreaXPath;
	
	@Column(name = "DateOfVisitXPath", nullable = false)
	private String dateOfVisitXPath;
	
	@Column(name = "LocationXPath")
	private String locationXPath;
	
	@Column(name = "ToEmailIdsXPath")
	private String toEmailIdsXPath;
		
	@Column(name = "CcEmailIds")
	private String ccEmailIds;
	
	@Column(name = "SendRawData", nullable = false)
	private boolean sendRawData;
	
	@Column(name = "IsLive", nullable = false)
	private boolean isLive;
	
	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	
	@Column(name = "UpdatedBy")
	private String updatedBy;

	@Column(name = "UpdatedDate")
	private Timestamp updatedDate;
	
	@Column(name = "BlockType")
	private String blockType;
	
	@Column(name = "AggregationLevel")
	private Integer aggregationLevel;
	/**
	 * This column will keep the media path of the xForms.
	 * @author Subhadarshani Patra (subhadarshani@sdrc.co.in)	 
	 * @since version 1.0.0.0
	 *
	 */
	@Column(name = "MediaPath")
	private String mediaPath;

	@OneToMany(mappedBy="xForm")
	private List<Program_XForm_Mapping> program_XForm_Mappings;
	
	
	
	
	@ManyToOne
	@JoinColumn(name="AreaLevelId", nullable = false)
	private AreaLevel areaLevel;

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getxFormId() {
		return xFormId;
	}

	public void setxFormId(String xFormId) {
		this.xFormId = xFormId;
	}

	public String getxFormIdTitle() {
		return xFormIdTitle;
	}

	public void setxFormIdTitle(String xFormIdTitle) {
		this.xFormIdTitle = xFormIdTitle;
	}

	public String getRootElement() {
		return rootElement;
	}

	public void setRootElement(String rootElement) {
		this.rootElement = rootElement;
	}

	public String getOdkServerURL() {
		return odkServerURL;
	}

	public void setOdkServerURL(String odkServerURL) {
		this.odkServerURL = odkServerURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAreaXPath() {
		return areaXPath;
	}

	public void setAreaXPath(String areaXPath) {
		this.areaXPath = areaXPath;
	}

	public String getSecondaryAreaXPath() {
		return secondaryAreaXPath;
	}

	public void setSecondaryAreaXPath(String secondaryAreaXPath) {
		this.secondaryAreaXPath = secondaryAreaXPath;
	}

	public String getDateOfVisitXPath() {
		return dateOfVisitXPath;
	}

	public void setDateOfVisitXPath(String dateOfVisitXPath) {
		this.dateOfVisitXPath = dateOfVisitXPath;
	}

	public String getLocationXPath() {
		return locationXPath;
	}

	public void setLocationXPath(String locationXPath) {
		this.locationXPath = locationXPath;
	}

	public String getToEmailIdsXPath() {
		return toEmailIdsXPath;
	}

	public void setToEmailIdsXPath(String toEmailIdsXPath) {
		this.toEmailIdsXPath = toEmailIdsXPath;
	}

	public String getCcEmailIds() {
		return ccEmailIds;
	}

	public void setCcEmailIds(String ccEmailIds) {
		this.ccEmailIds = ccEmailIds;
	}

	public boolean isSendRawData() {
		return sendRawData;
	}

	public void setSendRawData(boolean sendRawData) {
		this.sendRawData = sendRawData;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	public String getBlockType() {
		return blockType;
	}

	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}

	public Integer getAggregationLevel() {
		return aggregationLevel;
	}

	public void setAggregationLevel(Integer aggregationLevel) {
		this.aggregationLevel = aggregationLevel;
	}

	public List<Program_XForm_Mapping> getProgram_XForm_Mappings() {
		return program_XForm_Mappings;
	}

	public void setProgram_XForm_Mappings(
			List<Program_XForm_Mapping> program_XForm_Mappings) {
		this.program_XForm_Mappings = program_XForm_Mappings;
	}


	
	public String getMediaPath() {
		return mediaPath;
	}

	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	public AreaLevel getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(AreaLevel areaLevel) {
		this.areaLevel = areaLevel;
	}


	
}
