package ISUGeoguessr.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>
    {
        Location findById(int LocationID);

        @Transactional
        void deleteById(int LocationID);
    }
