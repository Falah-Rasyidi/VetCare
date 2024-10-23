package au.edu.rmit.sept.webapp.util;

public class dataTypeConverter  {
    static public String htmlTimeToSQL(String dateString){
        String res = dateString;
        res = dateString.replace('T', ' ');
        return(res+":00");


    }
}
