package gangulwar.api.halfway;

public class PlacesModal {
    String nameOfPlace;
    String typeOfPlace;
    String iconUrl;
    int distance;
    float lat;
    float lon;

    public PlacesModal(String nameOfPlace, String typeOfPlace, String iconUrl, int distance, float lat, float lon) {
        this.nameOfPlace = nameOfPlace;
        this.typeOfPlace = typeOfPlace;
        this.iconUrl = iconUrl;
        this.distance = distance;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "\nName=" + nameOfPlace + "\nType Of Place=" +
                typeOfPlace + "\nIcon Url="+iconUrl+
                "\nDistance=" +
                distance + "\n\tLat=" + "\n\t"
                + lat + "\n\tLong=" + lon;
    }
}
