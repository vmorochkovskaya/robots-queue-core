package by.game.core.dao;


import by.game.core.entity.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogDao extends CrudRepository<Log, Integer> {

   void addLog(Log log);
}
