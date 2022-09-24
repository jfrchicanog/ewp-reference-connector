package eu.erasmuswithoutpaper.iia.entity;

public class IiaResponse {
	
	private String iiaId;
	private String hashCode;
	
	public String getIiaId() {
		return iiaId;
	}
	
	public void setIiaId(String iiaId) {
		this.iiaId = iiaId;
	}
	
	public String getHashCode() {
		return hashCode;
	}
	
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public IiaResponse(String iiaId, String hashCode) {
		super();
		this.iiaId = iiaId;
		this.hashCode = hashCode;
	}
	
		
}
