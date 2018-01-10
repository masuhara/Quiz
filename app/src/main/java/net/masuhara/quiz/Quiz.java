package net.masuhara.quiz;

/**
 * Created by Masuhara on 2017/12/13.
 */

public class Quiz {
    // 問題
    String content;

    // 選択肢
    String option1;
    String option2;
    String option3;
    String option4;
    String option5;

    // 答えの文字
    String answer;

    // 画像名
    String imageName;

    // コンストラクタ
    // Ctrl + Enterキーで、GeneratorからConstructorを生成します。
    public Quiz(String content, String option1, String option2, String option3,  String option4,  String option5, String answer, String imageName) {
        this.content = content;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.answer = answer;
        this.imageName = imageName;
    }
}
