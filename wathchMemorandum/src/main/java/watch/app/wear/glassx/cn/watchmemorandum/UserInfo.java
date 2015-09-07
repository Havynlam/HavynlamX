package watch.app.wear.glassx.cn.watchmemorandum;

/**
 * Created by Havynlam on 15/9/7.
 */
public class UserInfo
{
    private String datetime;
    private String content;
    private String alerttime;

    public UserInfo()
    {

    }

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getAlerttime()
    {
        return alerttime;
    }

    public void setAlerttime(String alerttime)
    {
        this.alerttime = alerttime;
    }
}
