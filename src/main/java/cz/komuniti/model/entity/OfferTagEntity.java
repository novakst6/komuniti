/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.model.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.search.annotations.*;

/**
 *
 * @author novakst6
 */

@Entity
@Table(name="OFFER_TAG")
@Indexed(index="Offer_Entity_Index")
public class OfferTagEntity implements Serializable {
    
    private Long id;
    private String name;
    private Long version;
    private Set<OfferEntity> offers;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="OFFERTAG_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="OFFERTAG_NAME")
    @Field(analyze= Analyze.YES,index= Index.YES,store= Store.NO)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @ManyToMany(fetch= FetchType.LAZY,mappedBy="tags")
    public Set<OfferEntity> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferEntity> offers) {
        this.offers = offers;
    }

    @Transient
    public boolean isOffesEmpty(){
        return offers.isEmpty();
    }
    
}
