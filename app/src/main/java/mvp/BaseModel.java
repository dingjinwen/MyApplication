package mvp;


/**
 * @author dingjinwen
 * @description
 * @date 2017/6/6 0006
 */
public class BaseModel {
    public int status;//0为错误，1为正确
    public int errorCode;
    public String errorMessage;

    public boolean getStatus() {
        return status == 1;
    }
}
