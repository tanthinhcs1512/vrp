package webserviceapi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "map_routes")
public class RouteEntity implements Serializable {

    private static final long serialVersionUID = 345678L;

    public RouteEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =  false, length = 20)
    private String idPlaceOrigin;

    @Column(nullable =  false, length = 20)
    private String origin;

    @Column(nullable = false)
    private Double latitudeOrigin;

    @Column(nullable = false)
    private Double longitudeOrigin;

    @Column(nullable =  false, length = 20)
    private String idPlaceDestination;

    @Column(nullable =  false, length = 20)
    private String destination;

    @Column(nullable = false)
    private Double latitudeDestination;

    @Column(nullable = false)
    private Double longitudeDestination;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private Integer time;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Integer transfer;

    @Column(nullable = false)
    private Double bbox_1;

    @Column(nullable = false)
    private Double bbox_2;

    @Column(nullable = false)
    private Double bbox_3;

    @Column(nullable = false)
    private Double bbox_4;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdPlaceOrigin() {
        return idPlaceOrigin;
    }

    public void setIdPlaceOrigin(String idPlaceOrigin) {
        this.idPlaceOrigin = idPlaceOrigin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getLatitudeOrigin() {
        return latitudeOrigin;
    }

    public void setLatitudeOrigin(Double latitudeOrigin) {
        this.latitudeOrigin = latitudeOrigin;
    }

    public Double getLongitudeOrigin() {
        return longitudeOrigin;
    }

    public void setLongitudeOrigin(Double longitudeOrigin) {
        this.longitudeOrigin = longitudeOrigin;
    }

    public String getIdPlaceDestination() {
        return idPlaceDestination;
    }

    public void setIdPlaceDestination(String idPlaceDestination) {
        this.idPlaceDestination = idPlaceDestination;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getLatitudeDestination() {
        return latitudeDestination;
    }

    public void setLatitudeDestination(Double latitudeDestination) {
        this.latitudeDestination = latitudeDestination;
    }

    public Double getLongitudeDestination() {
        return longitudeDestination;
    }

    public void setLongitudeDestination(Double longitudeDestination) {
        this.longitudeDestination = longitudeDestination;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public Double getBbox_1() {
        return bbox_1;
    }

    public void setBbox_1(Double bbox_1) {
        this.bbox_1 = bbox_1;
    }

    public Double getBbox_2() {
        return bbox_2;
    }

    public void setBbox_2(Double bbox_2) {
        this.bbox_2 = bbox_2;
    }

    public Double getBbox_3() {
        return bbox_3;
    }

    public void setBbox_3(Double bbox_3) {
        this.bbox_3 = bbox_3;
    }

    public Double getBbox_4() {
        return bbox_4;
    }

    public void setBbox_4(Double bbox_4) {
        this.bbox_4 = bbox_4;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
