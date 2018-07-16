/**
 * This poller class receives the data from the database 
 * and routes it to the processing service
 */
package world.jumo.DbPoller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import world.jumo.Service.ProcessRequest;


/**
 * @author Bob Mwenda
 *
 */
public class DbPoller {
	
	private final Logger logger = LogManager.getLogger(DbPoller.class);
	
	public ProcessRequest processRequest;

	public void handleJdbcMessage(List<Map> message) {
		try 
			{
				for (Map<?, ?> resultMap : message) 
				{					
					logger.info(resultMap);
					
					processRequest.SendRequest(resultMap);
				}
			} 
		catch (Exception e) 
			{
				logger.error(e);
				// UPdate DB for the failed transaction
			}

	}
}
