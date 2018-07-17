/**
 * This is the repository to access the class object via ORM
 */
package world.jumo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import world.jumo.Model.AirtimeModel;

/**
 * @author Bob Mwenda
 *
 */
@Repository
public interface AirtimeRepository extends JpaRepository<AirtimeModel, Long> {

	@Override
	AirtimeModel save(AirtimeModel model);

	@Query(value = "UPDATE transactions SET result_desc = :result_desc, discount = :discount, third_party_trans_id = :third_party_trans_id, message = :message WHERE id = :id", nativeQuery = true)
	String UpdateAirtimeModel(@Param("result_desc") String result_desc, @Param("discount") String discount,
			@Param("third_party_trans_id") String third_party_trans_id, @Param("message") String message,
			@Param("id") String id);

}
