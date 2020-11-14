package webserviceapi.repository;

public class StatisticLatitudeLongitude {
    private Integer count;

    private Double latitude;

    private Double longitude;

    public StatisticLatitudeLongitude(Integer count, Double latitude, Double longitude) {
        this.count = count;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
