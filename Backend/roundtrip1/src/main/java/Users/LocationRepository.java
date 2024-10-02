package Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<User, Long>
{
    User findById(int LocationID);

    void deleteById(int LocationID);
}
