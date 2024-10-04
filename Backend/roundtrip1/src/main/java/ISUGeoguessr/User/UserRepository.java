package ISUGeoguessr.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
    {
        User findById(int UserID);

        void deleteById(int UserID);
    }
