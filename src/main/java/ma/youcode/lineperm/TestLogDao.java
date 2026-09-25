package ma.youcode.lineperm;

import ma.youcode.lineperm.dao.LogDao;

public class TestLogDao {

    public static void main(String[] args) {

        LogDao logDao = new LogDao();

        System.out.println("Total logs : " + logDao.compterTotal());

        System.out.println("Refused logs : " + logDao.compterRefuse());

        System.out.println("Users : " + logDao.userDistincts());

        System.out.println("Top files : " + logDao.topFichiers());

        System.out.println("Refused users : " + logDao.refuseByUser());

        System.out.println("Most active user : " + logDao.mostUser());

        System.out.println("Actions : " + logDao.repartitionByAction());

        System.out.println("Actions of badr : " + logDao.actionUser("badr"));
    }
}