package by.game.core.dao;


import by.game.core.entity.Log;

import java.io.Serializable;
import java.sql.SQLException;


public interface ILogDao {

   Serializable addLog(Log log) throws SQLException;
}
