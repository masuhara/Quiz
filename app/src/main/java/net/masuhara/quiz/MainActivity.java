package net.masuhara.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView countTextView;
    TextView contentTextView;

    ImageView imageView;

    Button[] optionButtons;

    // 現在の問題番号
    int questionNumber;

    int point;

    List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countTextView = findViewById(R.id.countTextView);
        contentTextView = findViewById(R.id.contentTextView);
        imageView = findViewById(R.id.imageView);

        optionButtons = new Button[5];
        optionButtons[0] = findViewById(R.id.option1Button);
        optionButtons[1] = findViewById(R.id.option2Button);
        optionButtons[2] = findViewById(R.id.option3Button);
        optionButtons[3] = findViewById(R.id.option4Button);
        optionButtons[4] = findViewById(R.id.option5Button);
        for(Button button : optionButtons) {
            // レイアウトではなくコードからonClickを設定します
            // setOnClickListenerに、thisを入れて呼び出します
            button.setOnClickListener(this);
        }

        // Quizを1から始めるのでリセットする
        resetQuiz();

        contentTextView = findViewById(R.id.contentTextView);
        // hoge
        Int hoge = optionButtons.length;
    }

    void createQuizList() {
        Quiz quiz1 = new Quiz("気になるあの子から突然電話...!どうする？", "元気に応答", "普通に応答", "ダルそうに応答", "応答せずそのままシカト", "応答せずチャットで要件を聞く", "普通に応答", "tell.jpeg");
        Quiz quiz2 = new Quiz("", "", "", "", "", "", "", "");
        Quiz quiz3 = new Quiz("", "", "", "", "", "", "", "");
        Quiz quiz4 = new Quiz("", "", "", "", "", "", "", "");
        Quiz quiz5 = new Quiz("", "", "", "", "", "", "", "");

        quizList = new ArrayList<>();
        quizList.add(quiz1);
        quizList.add(quiz2);

        // Listの中身をシャッフルします
        Collections.shuffle(quizList);
    }

    void showQuiz() {
        countTextView.setText((questionNumber + 1) + "問目");
        // 表示する問題をリストから取得します。
        // 配列では[番号]でしたが、リストでは get(番号)で取得します。配列と同じく0からスタートです。
        Quiz quiz = quizList.get(questionNumber);
        contentTextView.setText(quiz.content);
        optionButtons[0].setText(quiz.option1);
        optionButtons[1].setText(quiz.option2);
        optionButtons[2].setText(quiz.option3);
        optionButtons[3].setText(quiz.option4);
        optionButtons[4].setText(quiz.option5);

        try {
            InputStream istream = getResources().getAssets().open(quiz.imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(istream);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.d("Assets","Error");
        }
    }

    // ⑤ クイズのリセット
    void resetQuiz() {
        questionNumber = 0;
        point = 0;
        createQuizList();
        showQuiz();
    }

    void updateQuiz() {
        questionNumber++;
        if (questionNumber < quizList.size()) {
            showQuiz();
        } else {
            // 全ての問題を解いてしまったので、結果を表示します。
            // 結果表示にはDialogを使います。
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("結果");
            builder.setMessage(quizList.size() + "問中、" + point + "問正解でした。");
            builder.setPositiveButton("リトライ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // もう一度クイズをやり直す
                    resetQuiz();
                }
            });
            builder.show();
        }
    }

    @Override
    public void onClick(View view) {
        // 引数のViewには、押されたViewが入っています。
        // Buttonが押された時しかよばれないので、キャストといって型を示してあげます。
        Button clickedButton = (Button) view;
        Quiz quiz = quizList.get(questionNumber);
        // ボタンの文字と、答えが同じかチェックします。
        if (TextUtils.equals(clickedButton.getText(), quiz.answer)) {
            // 正解の場合だけ1ポイント加算します。
            point++;
            Toast.makeText(this, "正解", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "はずれ", Toast.LENGTH_SHORT).show();
        }
        // 次の問題にアップデートします。
        updateQuiz();
    }
}
