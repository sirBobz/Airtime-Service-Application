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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import world.jumo.Model.AirtimeModel;
import world.jumo.Repository.AirtimeRepository;

/**
 * @author Bob Mwenda
 *
 */
@Service
public class ProcessRequest {

	private final Logger logger = LogManager.getLogger(ProcessRequest.class);
	
	@Autowired
	private AirtimeRepository airtimeRepository; 
	
	private final String initiator_username = "Dibon";
	private final String initiator_password = "7bb50ca58757df358ee606bbede2a88f94453da50df6508123d901dd5face9b6";

	public void SendRequest(Map<?, ?> resultMap) {
		
		JSONArray recipients = new JSONArray();

		try {
			recipients.put(new JSONObject()
					.put("phoneNumber", "+254" + StringUtils.right((String) resultMap.get("phone_number"), 9))
					.put("amount", "KES " + resultMap.get("amount")));
		} catch (JSONException e1) {

			logger.error(e1);
		}

		AfricasTalkingGateway gateway = new AfricasTalkingGateway(initiator_username, initiator_password);

		try {
			JSONArray results = gateway.sendAirtime(recipients.toString());

			logger.info(results);

			int length = results.length();
			for (int i = 0; i < length; i++) {
				JSONObject result = results.getJSONObject(i);

				airtimeRepository.UpdateAirtimeModel(result.getString("status"), result.getString("discount"), result.getString("requestId"), result.getString("errorMessage"), (String) resultMap.get("id"));
				
				
				

			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
