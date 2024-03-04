package eu.erasmuswithoutpaper.omobility.las.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name = ListOfComponents.findAll, query = "SELECT lc FROM ListOfComponents lc"),
})
public class ListOfComponents {
	
	private static final String PREFIX = "eu.erasmuswithoutpaper.omobility.las.entity.ListOfComponents.";
    public static final String findAll = PREFIX + "all";
	
	@Id
    @Column(updatable = false)
	@GeneratedValue(generator="system-uuid")
    String id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENTS_STUDIED")
    private List<Component> componentsStudied;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENTS_RECOGNIZED")
    private List<Component> componentsRecognized;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENTS_VIRTUAL")
    private List<Component> virtualComponents;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENTS_BLENDED_MOBILITY")
    private List<Component> blendedMobilityComponents;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinTable(name = "COMPONENTS_SHORT_TERM_DOCTORAL")
    private List<Component> shortTermDoctoralComponents;
	
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_STUDENT_SIGNATURE",referencedColumnName = "ID")
    private eu.erasmuswithoutpaper.omobility.las.entity.Signature studentSignature;
	
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_SENDING_HEI_SIGNATURE",referencedColumnName = "ID")
    private eu.erasmuswithoutpaper.omobility.las.entity.Signature sendingHeiSignature;
	
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_RECEIVING_HEI_SIGNATURE",referencedColumnName = "ID")
    private eu.erasmuswithoutpaper.omobility.las.entity.Signature receivingHeiSignature;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Component> getComponentsStudied() {
		return componentsStudied;
	}

	public void setComponentsStudied(List<Component> componentsStudied) {
		this.componentsStudied = componentsStudied;
	}

	public List<Component> getComponentsRecognized() {
		return componentsRecognized;
	}

	public void setComponentsRecognized(List<Component> componentsRecognized) {
		this.componentsRecognized = componentsRecognized;
	}

	public List<Component> getVirtualComponents() {
		return virtualComponents;
	}

	public void setVirtualComponents(List<Component> virtualComponents) {
		this.virtualComponents = virtualComponents;
	}

	public List<Component> getBlendedMobilityComponents() {
		return blendedMobilityComponents;
	}

	public void setBlendedMobilityComponents(List<Component> blendedMobilityComponents) {
		this.blendedMobilityComponents = blendedMobilityComponents;
	}

	public List<Component> getShortTermDoctoralComponents() {
		return shortTermDoctoralComponents;
	}

	public void setShortTermDoctoralComponents(List<Component> shortTermDoctoralComponents) {
		this.shortTermDoctoralComponents = shortTermDoctoralComponents;
	}

	public eu.erasmuswithoutpaper.omobility.las.entity.Signature getStudentSignature() {
		return studentSignature;
	}

	public void setStudentSignature(eu.erasmuswithoutpaper.omobility.las.entity.Signature studentSignature) {
		this.studentSignature = studentSignature;
	}

	public eu.erasmuswithoutpaper.omobility.las.entity.Signature getSendingHeiSignature() {
		return sendingHeiSignature;
	}

	public void setSendingHeiSignature(eu.erasmuswithoutpaper.omobility.las.entity.Signature sendingHeiSignature) {
		this.sendingHeiSignature = sendingHeiSignature;
	}

	public eu.erasmuswithoutpaper.omobility.las.entity.Signature getReceivingHeiSignature() {
		return receivingHeiSignature;
	}

	public void setReceivingHeiSignature(eu.erasmuswithoutpaper.omobility.las.entity.Signature receivingHeiSignature) {
		this.receivingHeiSignature = receivingHeiSignature;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListOfComponents other = (ListOfComponents) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
