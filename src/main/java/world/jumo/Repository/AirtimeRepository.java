/**
 * This is the repository to access the class object via ORM
 */
package world.jumo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

	

}
