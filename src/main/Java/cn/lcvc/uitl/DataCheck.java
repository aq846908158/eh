package cn.lcvc.uitl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCheck {
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isEmailNO(String email) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isTrueName(String trueName) {
        Pattern p = Pattern.compile("[\\u4E00-\\u9FA5]{2,4}");
        Matcher m = p.matcher(trueName);
        return m.matches();
    }
}
