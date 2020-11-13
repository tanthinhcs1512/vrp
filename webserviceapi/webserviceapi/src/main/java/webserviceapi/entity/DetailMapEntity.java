package webserviceapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "detail_map")
public class DetailMapEntity implements Serializable {

    private static final long serialVersionUID = 345678L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable =  false)
    private Integer sign;

    @Column(nullable =  false)
    private String distance;

    @Column()
    private String heading;

    @Column(nullable =  false)
    private Integer head_interval;

    @Column(nullable =  false)
    private String tail_interval;

    @Column(nullable =  false, length = 20)
    private String text;
}
