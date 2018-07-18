/**
 * This is the repository to access the class object via ORM
 */
package world.jumo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import world.jumo.Model.AirtimeModel;

/**
 * @author Bob Mwenda
 *
 */
@Repository
@Transactional
public interface AirtimeRepository extends JpaRepository<AirtimeModel, Long> {

	@Modifying
	@Query("UPDATE AirtimeModel t set t.third_party_trans_id = :third_party_trans_id, t.result_desc = :result_desc, t.discount = :discount, t.message = :message WHERE t.id = :id")
	Integer updateAirtimeResponse(@Param("id") Long id, @Param("third_party_trans_id") String third_party_trans_id, @Param("discount") String discount, @Param("message") String message, @Param("result_desc") String result_desc);


}
