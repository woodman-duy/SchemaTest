package modules.customers.service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.enterprise.inject.Default;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * Basic implementation of a Geocoding service using Photon.
 */
@Default
public class PhotonGeocodeServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhotonGeocodeServiceImpl.class);
	private static final String PHOTON_BASE_URL = "https://photon.komoot.io/api/";
	private static final String USER_AGENT_HEADER = "Skyve Simple CRM";

	@SuppressWarnings({ "static-method" })
	public Point geocode(String address) throws Exception {
		if (address == null) {
			throw new IllegalArgumentException("address is required");
		}
		
		URI photonQueryUri = URI.create(String.format(PHOTON_BASE_URL + "?q=%s&limit=%s",
				URLEncoder.encode(address, "UTF-8").replaceAll("\\+", "%20"), String.valueOf(1)));

		HttpRequest request = HttpRequest.newBuilder()
				.uri(photonQueryUri)
				.header("User-Agent", USER_AGENT_HEADER)
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();

		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

		if (response != null) {
			String json = response.body();
			// parse out the payload
			JSONParser parser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
			JSONObject jsonBody = (JSONObject) parser.parse(json);

			if (jsonBody.containsKey("features")) {
				JSONArray features = (JSONArray) jsonBody.get("features");
				if (features != null && features.size() > 0) {
					JSONObject feature = (JSONObject) features.get(0);
					if (feature != null && feature.containsKey("geometry")) {
						JSONObject geometry = (JSONObject) feature.get("geometry");
						if ("Point".equals(geometry.getAsString("type"))) {
							JSONArray coords = (JSONArray) geometry.get("coordinates");
							if (coords != null) {
								GeometryFactory gf = new GeometryFactory();
								LOGGER.debug("Found coordinates lng:{}, lat:{}", coords.get(0), coords.get(1));
								double lng = toDouble(coords.get(0));
								double lat = toDouble(coords.get(1));

								return gf.createPoint(new Coordinate(lng, lat));
							}
						}
					}
				}
			}
		}

		return null;
	}

	private static double toDouble(final Object number) {
		if (number instanceof Double) {
			return ((Double) number).doubleValue();
		}
		if (number instanceof BigDecimal) {
			return ((BigDecimal) number).doubleValue();
		}

		return Double.valueOf(number.toString()).doubleValue();
	}
}
