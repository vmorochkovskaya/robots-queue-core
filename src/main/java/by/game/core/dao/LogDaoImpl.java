package by.game.core.dao;


import by.game.core.entity.Log;
import by.game.core.util.DatabaseUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.sql.SQLException;

public class LogDaoImpl implements ILogDao {
    @Override
    public Serializable addLog(Log log) throws SQLException{
        Session session = null;
        Serializable id = null;
        Transaction tr = null;
        try{
            session = DatabaseUtil.getSessionFactory().openSession();
            tr = session.beginTransaction();
            id = session.save(log);
            tr.commit();
        }catch(HibernateException e){
            if(tr!=null)tr.rollback();
            throw new SQLException();
        }finally{
            if(session!=null&&session.isOpen())session.close();
        }
        return id;

    }
}
