package by.game.core.dao;

import by.game.core.entity.Log;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface ILogDAO extends CrudRepository<Log, Long> {

    Serializable addLog(Log item);
}

