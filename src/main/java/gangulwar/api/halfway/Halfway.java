package gangulwar.api.halfway;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Halfway {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Lat");
        String lat = sc.next();

        System.out.println("Enter Lon=");
        String lon = sc.next();

        System.out.println("Enter Radius=");
        String radius = sc.next();

        System.out.println("Select Category=\n1.Cafes\n2.LandMark\n3.Entertainment");
        int choice = sc.nextInt();
        int category;
        switch (choice) {
            case 1:
                category = 13000;
                break;
            case 2:
                category = 16000;
                break;
            case 3:
                category = 10000;
                break;
            default:
                category = 0;
                break;
        }

        ArrayList<PlacesModal> modalArrayList = APIRequest(lat, lon, radius, category);
        System.out.println(modalArrayList);

    }

    public static ArrayList<PlacesModal> APIRequest(String lat, String lon, String radius, int category) {

        OkHttpClient client = new OkHttpClient();
        String API_BASE_URL = "https://api.foursquare.com/v3/places/search?";
        String API_KEY = "API_KEY";
        String endpoint = API_BASE_URL + "ll=" + lat + "," + lon + "&radius=" + radius + "&categories=" + category;

        Request request = new Request.Builder()
                .url(endpoint)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", API_KEY)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            return getObjects(responseBody);
        } catch (IOException e) {
            return new ArrayList<PlacesModal>(null);
        }
    }

    private static ArrayList<PlacesModal> getObjects(String responseBody) {

        ArrayList<PlacesModal> arrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(responseBody);
        var results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {

            JSONObject places = results.getJSONObject(i);

            var nameOfPlace = places.getString("name");

            var categories = places.getJSONArray("categories");
            var typeJson = categories.getJSONObject(0);
            var type = typeJson.getString("name");

            var icon = typeJson.getJSONObject("icon");
            var prefix = icon.getString("prefix");
            var suffix = icon.getString("suffix");
            var iconUrl = prefix + "120" + suffix;

            var distance = places.getInt("distance");

            var geocodes = places.getJSONObject("geocodes");
            var main = geocodes.getJSONObject("main");
            var lat = main.getFloat("latitude");
            var lon = main.getFloat("longitude");

            arrayList.add(new PlacesModal(nameOfPlace, type, iconUrl, distance, lat, lon));
        }
        return arrayList;
    }
}
