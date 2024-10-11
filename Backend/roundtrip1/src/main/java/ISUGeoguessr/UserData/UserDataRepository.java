package ISUGeoguessr.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long>
    {
        UserData findById(int UserID);

        @Transactional
        void deleteById(int UserID);
    }
