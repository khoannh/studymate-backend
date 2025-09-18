package exe201.studymatebackend.repository;

import exe201.studymatebackend.pojo.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
    Action findByActionName(String actionName);

    boolean existsByActionID(Integer actionID);

    Action findByActionID(Integer actionID);

}
