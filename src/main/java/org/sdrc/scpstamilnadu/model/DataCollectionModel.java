package org.sdrc.scpstamilnadu.model;

import java.util.ArrayList;
import java.util.List;

import org.sdrc.scpstamilnadu.model.DataModel;
import org.sdrc.scpstamilnadu.model.ValueObject;

public class DataCollectionModel {

	public List<DataModel> dataCollection;
	//private static final long serialVersionUID = 1L;
	private List<ValueObject> legends;
	private List<String> topPerformers ;	
	private List<String> bottomPerformers ;
	
	public List<DataModel> getDataCollection() {
		return dataCollection;
	}
	public void setDataCollection(List<DataModel> dataCollection) {
		this.dataCollection = dataCollection;
	}
	public List<ValueObject> getLegends() {
		return legends;
	}
	public void setLegends(List<ValueObject> legends) {
		this.legends = legends;
	}
	public List<String> getTopPerformers() {
		return topPerformers;
	}
	public void setTopPerformers(List<String> topPerformers) {
		this.topPerformers = topPerformers;
	}
	public List<String> getBottomPerformers() {
		return bottomPerformers;
	}
	public void setBottomPerformers(List<String> bottomPerformers) {
		this.bottomPerformers = bottomPerformers;
	}
	public DataCollectionModel(){
		dataCollection = new ArrayList<DataModel>();
	}
	@Override
	public String toString() {
		return "UtDataCollection [dataCollection=" + dataCollection
				+ ", legends=" + legends + ", topPerformers=" + topPerformers
				+ ", bottomPerformers=" + bottomPerformers + "]";
	}
	


}
