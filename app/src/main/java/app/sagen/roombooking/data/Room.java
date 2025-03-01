package app.sagen.roombooking.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Room implements Parcelable {

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    private int id;
    private String name;
    private String description;
    private Building building;
    private List<Reservation> reservations;

    public Room(Building building) {
        this.building = building;
        id = -1;
    }

    public Room(Building building, JSONObject jsonObject) throws JSONException {

        this.building = building;

        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        description = jsonObject.getString("description");

        reservations = new ArrayList<>();

        JSONArray reservations = jsonObject.getJSONArray("reservations");
        for (int i = 0; i < reservations.length(); i++) {
            this.reservations.add(new Reservation(this, reservations.getJSONObject(i)));
        }
    }

    public Room() {
    }

    protected Room(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        reservations = in.createTypedArrayList(Reservation.CREATOR);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeTypedList(reservations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
