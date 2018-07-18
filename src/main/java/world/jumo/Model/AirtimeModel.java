/**
 * This is the class object of the Airtime Request
 */
package world.jumo.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author Bob Mwenda
 *
 */
@Entity
@Table(name = "transactions")
public class AirtimeModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @params
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String amount;
	@Column
	private String discount;
	@Column
	private String phone_number;
	@Column
	private String request_id;
	@Column
	private String result_desc;
	@Column
	private String message;
	@Column
	private Integer status;
	@Column
	private String third_party_trans_id;
	@Column
	private Date transaction_time;
	@Column
	private Date deleted_at;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date created_at;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updated_at;
	
	
	public AirtimeModel() {
		
	}
	
	
	/**
	 * @param id
	 * @param amount
	 * @param phone_number
	 * @param request_id
	 * @param message
	 * @param result_desc
	 * @param status
	 * @param third_party_trans_id
	 * @param transaction_time
	 * @param deleted_at
	 * @param created_at
	 * @param updated_at
	 */
	public AirtimeModel(long id, String amount, String phone_number, String request_id, String message,
			String result_desc, Integer status, String third_party_trans_id, Date transaction_time, Date deleted_at,
			Date created_at, Date updated_at) {
		this.id = id;
		this.amount = amount;
		this.phone_number = phone_number;
		this.request_id = request_id;
		this.message = message;
		this.result_desc = result_desc;
		this.status = status;
		this.third_party_trans_id = third_party_trans_id;
		this.transaction_time = transaction_time;
		this.deleted_at = deleted_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}




	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}



	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	
	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @return the phone_number
	 */
	public String getPhone_number() {
		return phone_number;
	}




	/**
	 * @return the request_id
	 */
	public String getRequest_id() {
		return request_id;
	}


	/**
	 * @param request_id the request_id to set
	 */
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}


	/**
	 * @return the message
	 */
	public String getmessage() {
		return message;
	}


	/**
	 * @param message 
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * @return the result_desc
	 */
	public String getResult_desc() {
		return result_desc;
	}


	/**
	 * @param result_desc the result_desc to set
	 */
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}


	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


	/**
	 * @return the third_party_trans_id
	 */
	public String getThird_party_trans_id() {
		return third_party_trans_id;
	}


	/**
	 * @param third_party_trans_id the third_party_trans_id to set
	 */
	public void setThird_party_trans_id(String third_party_trans_id) {
		this.third_party_trans_id = third_party_trans_id;
	}


	/**
	 * @return the transaction_time
	 */
	public Date getTransaction_time() {
		return transaction_time;
	}


	/**
	 * @param transaction_time the transaction_time to set
	 */
	public void setTransaction_time(Date transaction_time) {
		this.transaction_time = transaction_time;
	}


	/**
	 * @return the deleted_at
	 */
	public Date getDeleted_at() {
		return deleted_at;
	}


	/**
	 * @param deleted_at the deleted_at to set
	 */
	public void setDeleted_at(Date deleted_at) {
		this.deleted_at = deleted_at;
	}


	/**
	 * @return the created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}


	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}


	/**
	 * @return the updated_at
	 */
	public Date getUpdated_at() {
		return updated_at;
	}


	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}


	/* 
	 * converts java.lang.Object to String
	 */
	@Override
	public String toString() {
		return "Airtime [id=" + id + ", " + (amount != null ? "amount=" + amount + ", " : "")
				+ (phone_number != null ? "phone_number=" + phone_number + ", " : "")
				+ (request_id != null ? "request_id=" + request_id + ", " : "")
				+ (message != null ? "result_code=" + message + ", " : "")
				+ (result_desc != null ? "result_desc=" + result_desc + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (third_party_trans_id != null ? "third_party_trans_id=" + third_party_trans_id + ", " : "")
				+ (transaction_time != null ? "transaction_time=" + transaction_time + ", " : "")
				+ (deleted_at != null ? "deleted_at=" + deleted_at + ", " : "")
				+ (created_at != null ? "created_at=" + created_at + ", " : "")
				+ (updated_at != null ? "updated_at=" + updated_at : "") + "]";
	}
	
	

	

}
