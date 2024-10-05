package ISUGeoguessr.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long>
    {
        UserData findById(int UserID);

        void deleteById(int UserID);
    }
