package llc.metaversenetwork.app.util;

import com.alaer.lib.api.bean.CommonQuestion;
import com.alaer.lib.api.bean.CommonQuestionList;

import likly.dollar.$;

/**
 * Created by HuangJW on 2021/11/21 20:19.
 * Mail: huangjingwei@gwm.cn
 * Powered by GDC
 */
public class DataUtil {

    public static CommonQuestionList parseCommonQuestion(CommonQuestionList questionList) {
        boolean isDefaultLanguage = LocaleUtil.isDefaultLanguage();
        for (CommonQuestion question : questionList.list) {
            question.infos = $.json().toBean(question.i18n, CommonQuestion.Infos.class);
            CommonQuestion.Info info = question.infos.zh_TW;
            if (!isDefaultLanguage)
                info = question.infos.en_US;
            question.title = info.title;
            question.path = info.path;
            question.infos = null;
        }
        return questionList;
    }

}