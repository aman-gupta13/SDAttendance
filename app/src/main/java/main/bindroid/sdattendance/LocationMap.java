package main.bindroid.sdattendance;

import java.util.HashMap;

/**
 * Created by Dev.Dhar on 04/09/15.
 */
public class LocationMap {

	private static LocationMap ourInstance = new LocationMap();

	public static LocationMap getInstance() {
		return ourInstance;
	}

	private LocationMap() {
		init();
	}

	HashMap<Integer, Location> map = new HashMap<>();

	public Location getLocation(String seatNumber) {
		if (seatNumber != null && seatNumber.trim().length() > 0) {
			int seat = Integer.valueOf(seatNumber.substring(2)).intValue();
			Location loc = LocationMap.getInstance().map.get(seat);

			if (seat > 3181) {
				seat = seat - 3181;
			}
			if (loc == null) {
				for (int i = seat; i < 3181; i++) {
					loc = LocationMap.getInstance().map.get(i);
					if (loc != null) {
						break;
					}
				}

			}
			return loc;

		}
		return null;
	}

	private void init() {
		// 1
		map.put(3001, new Location(72, 430));
		map.put(3002, new Location(72, 430));
		map.put(3003, new Location(72, 430));
		map.put(3004, new Location(72, 430));

		// 2
		map.put(3005, new Location(43, 408));
		map.put(3006, new Location(43, 408));
		map.put(3007, new Location(43, 408));
		map.put(3008, new Location(43, 408));
		map.put(3009, new Location(43, 408));
		// 3
		map.put(3010, new Location(43, 369));
		map.put(3011, new Location(43, 369));
		map.put(3012, new Location(43, 369));
		map.put(3013, new Location(43, 369));
		map.put(3014, new Location(43, 369));
		map.put(3015, new Location(43, 369));
		map.put(3016, new Location(43, 369));
		map.put(3017, new Location(43, 369));
		map.put(3018, new Location(43, 369));
		map.put(3019, new Location(43, 369));

		// 3
		map.put(3020, new Location(43, 326));
		map.put(3021, new Location(43, 326));
		map.put(3022, new Location(43, 326));
		map.put(3023, new Location(43, 326));
		map.put(3024, new Location(43, 326));
		map.put(3025, new Location(43, 326));
		map.put(3026, new Location(43, 326));
		map.put(3027, new Location(43, 326));
		map.put(3028, new Location(43, 326));
		map.put(3029, new Location(43, 326));

		map.put(3030, new Location(43, 300));
		map.put(3031, new Location(43, 300));
		map.put(3032, new Location(43, 300));
		map.put(3033, new Location(43, 300));
		map.put(3034, new Location(43, 300));
		map.put(3035, new Location(43, 300));
		map.put(3036, new Location(43, 300));
		map.put(3037, new Location(43, 300));
		map.put(3038, new Location(43, 300));
		map.put(3039, new Location(43, 300));
		// 55

		map.put(3040, new Location(43, 260));
		map.put(3041, new Location(43, 260));
		map.put(3042, new Location(43, 260));
		map.put(3043, new Location(43, 260));
		map.put(3044, new Location(43, 260));
		map.put(3045, new Location(43, 260));
		map.put(3046, new Location(43, 260));
		map.put(3047, new Location(43, 260));
		map.put(3048, new Location(43, 260));
		map.put(3049, new Location(43, 260));

		map.put(3050, new Location(43, 220));
		map.put(3051, new Location(43, 220));
		map.put(3052, new Location(43, 220));
		map.put(3053, new Location(43, 220));
		map.put(3054, new Location(43, 220));
		map.put(3055, new Location(43, 220));
		map.put(3056, new Location(43, 220));
		map.put(3057, new Location(43, 220));
		map.put(3058, new Location(43, 220));
		map.put(3059, new Location(43, 220));

		map.put(3050, new Location(43, 180));
		map.put(3051, new Location(43, 180));
		map.put(3052, new Location(43, 180));
		map.put(3053, new Location(43, 180));
		map.put(3054, new Location(43, 180));
		map.put(3055, new Location(43, 180));
		map.put(3056, new Location(43, 180));
		map.put(3057, new Location(43, 180));
		map.put(3058, new Location(43, 180));
		map.put(3059, new Location(43, 180));

		map.put(3060, new Location(43, 140));
		map.put(3061, new Location(43, 140));
		map.put(3062, new Location(43, 140));
		map.put(3063, new Location(43, 140));
		map.put(3064, new Location(43, 140));
		map.put(3065, new Location(43, 140));
		map.put(3066, new Location(43, 140));
		map.put(3067, new Location(43, 140));
		map.put(3068, new Location(43, 140));
		map.put(3069, new Location(43, 140));

		map.put(3181, new Location(400, 430));
		map.put(3182, new Location(400, 430));
		map.put(3183, new Location(400, 430));
		map.put(3184, new Location(400, 430));

		map.put(3183, new Location(423, 400));
		map.put(3182, new Location(423, 400));
		map.put(3181, new Location(423, 400));
		map.put(3180, new Location(423, 400));
		map.put(3179, new Location(423, 400));

		map.put(3169, new Location(423, 370));

		map.put(3159, new Location(423, 340));

		map.put(3149, new Location(423, 310));

		map.put(3139, new Location(423, 280));

		map.put(3129, new Location(423, 250));

		map.put(3119, new Location(423, 230));

	}

}
