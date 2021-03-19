package io.damelyngdoh.multidatasourcespringapp.domain.alpha;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Simple object code named Alpha.
 * 
 * @author Dame Lyngdoh
 */
@Entity
@Table(name = "alpha_object")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlphaObject {
    
    /**
     * Unique identifier (primary key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequence_generator")
    private Long id;

    /**
     * Some string data associated with the object.
     */
    @Column(name = "alpha_data")
    private String alphaData;

    @Override
    public String toString() {
        return "{" + 
            "id=" + id + 
            "alphaData=" + alphaData + 
            "}";
    }
}
