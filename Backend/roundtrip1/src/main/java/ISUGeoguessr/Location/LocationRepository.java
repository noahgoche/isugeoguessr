package ISUGeoguessr.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long>
{
    Location findById(int LocationID);

    void deleteById(int LocationID);
}
