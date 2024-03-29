package world.jumo.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AfricasTalkingGateway
{
    private String _username;
    private String _apiKey;
    private String _environment;
    private int responseCode;
    
    private static final int HTTP_CODE_OK      = 200;
    private static final int HTTP_CODE_CREATED = 201;
    
    
    
    public AfricasTalkingGateway() {
		
	}

	//Change debug flag to true to view raw server response
    private static final boolean DEBUG = false;
    
    public AfricasTalkingGateway(String username_, String apiKey_)
    {
	_username    = username_;
	_apiKey      = apiKey_;
	_environment = "production";
    }
    
    public AfricasTalkingGateway(String username_, String apiKey_, String environment_)
    {
	_username    = username_;
	_apiKey      = apiKey_;
	_environment = environment_;
    }
    
	
    //Bulk messages methods
    public JSONArray sendMessage(String to_, String message_) throws Exception
    {
	
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	return sendMessageImpl(to_, message_, data);
    }
    
   
    public JSONArray sendMessage(String to_, String message_, String from_) throws Exception
    {	
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	if ( from_.length() > 0 ) data.put("from", from_);
	
    	return sendMessageImpl(to_, message_, data);
    }
    
   
    public JSONArray sendMessage(String to_, String message_, String from_, int bulkSMSMode_) throws Exception
    {	
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	if ( from_.length() > 0 ) data.put("from", from_);
	
    	data.put("bulkSMSMode", Integer.toString(bulkSMSMode_));
	
    	return sendMessageImpl(to_, message_, data);
    }
    

    public JSONArray sendMessage(String to_, String message_, String from_, int bulkSMSMode_, HashMap<String, String> options_) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	if ( from_.length() > 0 ) data.put("from", from_);
	
    	data.put("bulkSMSMode", Integer.toString(bulkSMSMode_));
	

    	if (options_.containsKey("enqueue")) data.put("enqueue", options_.get("enqueue"));
    	if (options_.containsKey("keyword")) data.put("keyword", options_.get("keyword"));
    	if (options_.containsKey("linkId"))  data.put("linkId", options_.get("linkId"));
    	if (options_.containsKey("retryDurationInHours"))  data.put("retryDurationInHours", options_.get("retryDurationInHours"));
	
    	return sendMessageImpl(to_, message_, data);
    }
    

    public JSONArray fetchMessages(int lastReceivedId_) throws Exception
    {
    	String requestUrl = getSmsUrl() + "?" +
	    URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(_username, "UTF-8") +
	    "&" + URLEncoder.encode("lastReceivedId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(lastReceivedId_), "UTF-8");
    	
    	String response = sendGETRequest(requestUrl);
    	if(responseCode == HTTP_CODE_OK) {
	    JSONObject jsObject = new JSONObject(response);
	    return jsObject.getJSONObject("SMSMessageData").getJSONArray("Messages");
    	}
    	
    	throw new Exception(response.toString());
    }

    
    //Subcscription methods
    public JSONObject createSubscription(String phoneNumber_, String shortCode_, String keyword_) throws Exception
    {
    	if(phoneNumber_.length() == 0 || shortCode_.length() == 0 || keyword_.length() == 0)
	    throw new Exception("Please supply phoneNumber, shortCode and keyword");
    	
	HashMap <String, String> data_ = new HashMap<String, String>();
	data_.put("username", _username);
	data_.put("phoneNumber", phoneNumber_);
	data_.put("shortCode", shortCode_);
	data_.put("keyword", keyword_);
	String requestUrl = getSubscriptionUrl() + "/create";
		
	String response = sendPOSTRequest(data_, requestUrl);
		
	if(responseCode != HTTP_CODE_CREATED)
	    throw new Exception(response.toString());
		
	JSONObject jsObject = new JSONObject(response);
	return jsObject;
    }

  
    public JSONObject deleteSubscription(String phoneNumber_,String shortCode_, String keyword_) throws Exception
    {
    	if(phoneNumber_.length() == 0 || shortCode_.length() == 0 || keyword_.length() == 0)
	    throw new Exception("Please supply phone number, short code and keyword");
    	
	HashMap <String, String> data_ = new HashMap<String, String>();
	data_.put("username", _username);
	data_.put("phoneNumber", phoneNumber_);
	data_.put("shortCode", shortCode_);
	data_.put("keyword", keyword_);
	String requestUrl = getSubscriptionUrl() + "/delete";
		
	String response = sendPOSTRequest(data_, requestUrl);
		
	if(responseCode != HTTP_CODE_CREATED)
	    throw new Exception(response.toString());
		
	JSONObject jsObject = new JSONObject(response);
	return jsObject;
    }

    
    public JSONArray fetchPremiumSubscriptions (String shortCode_, String keyword_, int lastReceivedId_) throws Exception
    {
    	if(shortCode_.length() == 0 || keyword_.length() == 0)
	    throw new Exception("Please supply short code and keyword");
    	
    	lastReceivedId_ = lastReceivedId_ > 0? lastReceivedId_ : 0;
    	String requestUrl = getSubscriptionUrl() + "?username="+_username+"&shortCode="+shortCode_+"&keyword="+keyword_+"&lastReceivedId="+lastReceivedId_;
    	
    	String response = sendGETRequest(requestUrl);
    	if(responseCode == HTTP_CODE_OK) {
	    JSONObject jsObject = new JSONObject(response);
	    return jsObject.getJSONArray("responses");
    	}
    	
    	throw new Exception(response.toString());
    }
    
    
    public JSONArray call(String from_, String to_) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("from", from_);
    	data.put("to", to_);
    	String urlString = getVoiceUrl() + "/call";
    	String response  = sendPOSTRequest(data, urlString);
    	
    	JSONObject jsObject = new JSONObject(response);
    	
    	if(jsObject.getString("errorMessage").equals("None"))
	    return jsObject.getJSONArray("entries");
	throw new Exception(jsObject.getString("errorMessage"));
    }
 
    //Call methods
    public JSONArray getNumQueuedCalls(String phoneNumber, String queueName) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("phoneNumber", phoneNumber);
    	data.put("queueName", queueName);
    	
    	return queuedCalls(data);
    }
 
    
    public JSONArray getNumQueuedCalls(String phoneNumber) throws Exception 
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("phoneNumbers", phoneNumber);
    	
    	return queuedCalls(data);
    }
 
    
    public void uploadMediaFile(String url_, String phoneNumber_) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("url", url_);
	data.put("phoneNumber", phoneNumber_);
    	String requestUrl = getVoiceUrl() + "/mediaUpload";
    	
    	sendPOSTRequest(data, requestUrl);
    }
 
    
    //Airtime methods
    public JSONArray sendAirtime(String recipients_) throws Exception 
    {
    	HashMap<String, String> data_ = new HashMap<String, String>();
    	data_.put("username", _username);
    	data_.put("recipients", recipients_);
    	String urlString = getAirtimeUrl() + "/send";
    	
    	String response = sendPOSTRequest(data_, urlString);
    	
    	if(responseCode == HTTP_CODE_CREATED) {
	    JSONObject jsObject = new JSONObject(response);
	    JSONArray results = jsObject.getJSONArray("responses");
	    if(results.length() > 0)
		return results;
	    throw new Exception(jsObject.getString("errorMessage"));
    	}
    	
    	throw new Exception(response);
    }
    
    
    //User data method
    public JSONObject getUserData() throws Exception
    {
    	String requestUrl = getUserDataUrl() + "?username="+_username;
    	
    	String response   = sendGETRequest(requestUrl);
    	if(responseCode == HTTP_CODE_OK) {
	    JSONObject jsObject = new JSONObject(response);
	    return jsObject.getJSONObject("UserData");
    	}
    	
    	throw new Exception(response);
    }    
   
    private JSONArray sendMessageImpl(String to_, String message_, HashMap<String, String> data_) throws Exception {
    	String response = sendPOSTRequest(data_, getSmsUrl());
    	if (responseCode == HTTP_CODE_CREATED) {
	    JSONObject jsObject   = new JSONObject(response);
	    JSONArray  recipients = jsObject.getJSONObject("SMSMessageData").getJSONArray("Recipients");
	    if(recipients.length() > 0) return recipients;
	    
	    throw new Exception(jsObject.getJSONObject("SMSMessageData").getString("Message"));
    	}
    	
    	throw new Exception(response);
    }
    
    //Private accessor methods
    private JSONArray queuedCalls(HashMap<String, String> data_) throws Exception {
    	String requestUrl = getVoiceUrl() + "/queueStatus";
    	String response = sendPOSTRequest(data_, requestUrl);
    	JSONObject jsObject = new JSONObject(response);
    	if(jsObject.getString("errorMessage").equals("None"))
	    return jsObject.getJSONArray("entries");
    	throw new Exception(jsObject.getString("errorMessage"));
    }
    
    private String sendPOSTRequest(HashMap<String, String> dataMap_, String urlString_) throws Exception {
	String data = new String();
	Iterator<Entry<String, String>> it = dataMap_.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
	    data += URLEncoder.encode(pairs.getKey().toString(), "UTF-8");
	    data += "=" + URLEncoder.encode(pairs.getValue().toString(), "UTF-8");
	    if ( it.hasNext() ) data += "&";
	}
	URL url = new URL(urlString_);
	URLConnection conn = url.openConnection();
	conn.setRequestProperty("Accept", "application/json");
	conn.setRequestProperty("apikey", _apiKey);
	conn.setDoOutput(true);
	return sendPOSTRequestImpl(data, conn);
    }

    private String sendJsonPOSTRequest(String data_, String urlString_) throws Exception
    {
	URL url = new URL(urlString_);
	URLConnection conn = url.openConnection();
	conn.setRequestProperty("Content-Type", "application/json");
	conn.setRequestProperty("Accept", "application/json");
	conn.setRequestProperty("apikey", _apiKey);
	conn.setDoOutput(true);
	return sendPOSTRequestImpl(data_, conn);
    }

    private String sendPOSTRequestImpl(String data_, URLConnection conn_) throws Exception {
    	try {
	    OutputStreamWriter writer = new OutputStreamWriter(conn_.getOutputStream());
	    writer.write(data_);
	    writer.flush();
	    
	    HttpURLConnection http_conn = (HttpURLConnection)conn_;
	    responseCode = http_conn.getResponseCode();
			
	    BufferedReader reader;
	    boolean passed = true;
	    
	    if(responseCode == HTTP_CODE_OK || responseCode == HTTP_CODE_CREATED) {
		reader = new BufferedReader(new InputStreamReader(http_conn.getInputStream()));
	    }
	    else {
		reader = new BufferedReader(new InputStreamReader(http_conn.getErrorStream()));
		passed = false;
	    }
	    String response = readResponse(reader);
	    
	    if(DEBUG) System.out.println("ResponseCode: " + responseCode + " RAW Response: " + response);
	    
	    reader.close();
	    
	    if(passed) return response;
	    
	    throw new Exception(response);
	    
    	} catch (Exception e){
	    throw e;
	}
    }

    private String readResponse(BufferedReader reader) throws Exception
    {
	StringBuilder response = new StringBuilder();
	String line;
	while((line = reader.readLine()) != null) {
	    response.append(line);
	}
	return response.toString();
    }
    
    private String sendGETRequest(String urlString) throws Exception
    {
    	try {
	    URL url= new URL(urlString);
	    URLConnection connection = (URLConnection)url.openConnection();
	    connection.setRequestProperty("Accept","application/json");
	    connection.setRequestProperty("apikey", _apiKey);
    		
	    HttpURLConnection http_conn = (HttpURLConnection)connection;
	    responseCode = http_conn.getResponseCode();
    		
	    BufferedReader reader;
	    boolean passed = true;
	    if(responseCode == HTTP_CODE_OK || responseCode == HTTP_CODE_CREATED) {
		reader = new BufferedReader(new InputStreamReader(http_conn.getInputStream()));
	    }
	    else {
		reader = new BufferedReader(new InputStreamReader(http_conn.getErrorStream()));
		passed = false;
	    }
	    String response = reader.readLine();
    		
	    if(DEBUG) System.out.println(response);
	    
	    reader.close();
	    
	    if(passed) return response;
    			
	    throw new Exception(response);
    	}
    	catch (Exception e) {throw e;}
    }

    private String getApiHost() {
	return (_environment == "sandbox") ? "https://api.sandbox.africastalking.com" : "https://api.africastalking.com";
    }



    private String getVoiceHost() {
	return (_environment == "sandbox") ? "https://voice.sandbox.africastalking.com" : "https://voice.africastalking.com";
    }
    
    private String getSmsUrl() {
	return getApiHost() + "/version1/messaging";
    }
    
    private String getVoiceUrl() {
	return getVoiceHost();
    }
    
    private String getSubscriptionUrl() {
	return getApiHost() + "/version1/subscription";
    }
    
    private String getUserDataUrl() {
	return getApiHost() + "/version1/user";
    }

    private String getAirtimeUrl() {
	return getApiHost() + "/version1/airtime";
    }



    
}


