/**
 * Receives the Airtime request  
 * and sends to Africa's Talking API 
 * this is through their API Class
 */
package world.jumo.Service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import world.jumo.Model.AirtimeModel;

/**
 * @author Bob Mwenda
 *
 */
@Service
public class ProcessRequest {

	private final Logger logger = LogManager.getLogger(ProcessRequest.class);
	private final String initiator_username = "";
	private final String initiator_password = "";

	public void SendRequest(Map<?, ?> resultMap) {
		JSONArray recipients = new JSONArray();

		try {
			recipients.put(new JSONObject()
					.put("phoneNumber", "+254" + StringUtils.right((String) resultMap.get("phone_number"), 9))
					.put("amount", "KES " + resultMap.get("amount")));
		} catch (JSONException e1) {

			e1.printStackTrace();
		}

		AfricasTalkingGateway gateway = new AfricasTalkingGateway(initiator_username, initiator_password);

		try {
			JSONArray results = gateway.sendAirtime(recipients.toString());

			logger.info(results);

			int length = results.length();
			for (int i = 0; i < length; i++) {
				JSONObject result = results.getJSONObject(i);
				
				AirtimeModel model = new AirtimeModel();
				model.setResult_desc(result.getString("status"));
				model.setDiscount(result.getString("discount"));
				model.setThird_party_trans_id(result.getString("requestId"));
				model.setDiscount(result.getString("errorMessage"));

			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
