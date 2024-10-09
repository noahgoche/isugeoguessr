package ISUGeoguessr.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface LocationRepository extends JpaRepository<Location, Long>
{
    Location findById(int LocationID);

    @Transactional
    void deleteById(int LocationID);
}
