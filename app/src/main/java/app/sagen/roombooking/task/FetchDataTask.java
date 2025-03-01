package app.sagen.roombooking.task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import app.sagen.roombooking.data.Building;
import app.sagen.roombooking.util.Utils;

public class FetchDataTask extends AsyncTask<Void, Void, List<Building>> {

    public interface FetchRoomTaskCallback {
        void fetchedDataList(List<Building> building);
    }

    private static final String API_URI = "http://student.cs.hioa.no/~s326194/showBuildings.php";

    private static final String TAG = "FetchDataTask";

    private URL apiUri;
    private FetchRoomTaskCallback fetchRoomTaskCallback;

    public FetchDataTask(FetchRoomTaskCallback fetchRoomTaskCallback) {
        try {
            this.apiUri = new URL(API_URI);
            this.fetchRoomTaskCallback = fetchRoomTaskCallback;
        } catch (MalformedURLException e) {
            Log.e(TAG, "FetchDataTask: Could not parse api url!", e);
        }
    }

    @Override
    protected List<Building> doInBackground(Void... voids) {

        List<Building> buildings = new ArrayList<>();
        try {
            JSONArray jsonArray = Utils.readJsonArrayFrom(apiUri);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                buildings.add(new Building(jsonObject));
            }

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: Error while loading buildings!", e);
        }
        return buildings;
    }

    @Override
    protected void onPostExecute(List<Building> buildings) {
        fetchRoomTaskCallback.fetchedDataList(buildings);
    }
}
