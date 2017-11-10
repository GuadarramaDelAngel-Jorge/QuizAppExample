package guadarrama_jorge.quizappexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class MainActivity extends Activity {

    // Member variables accessible in all the methods of the MainActivity:
    Button mTrueButton;
    Button mFalseButton;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    ProgressBar mProgressBar;
    int mIndex;
    int mScore;
    int mQuestion;

    // Create question bank using the TrueFalse class for each item in the array
    @NonNull
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true),
            new TrueFalse(R.string.question_14, false),
            new TrueFalse(R.string.question_15,true),
            new TrueFalse(R.string.question_16, false),
            new TrueFalse(R.string.question_17,true),
            new TrueFalse(R.string.question_18, false),
            new TrueFalse(R.string.question_19,true),
            new TrueFalse(R.string.question_20, false),
            new TrueFalse(R.string.question_21,true),
            new TrueFalse(R.string.question_22, false),
            new TrueFalse(R.string.question_23,true),
            new TrueFalse(R.string.question_24, false),
            new TrueFalse(R.string.question_25,true),
            new TrueFalse(R.string.question_26, false),
            new TrueFalse(R.string.question_27,true),
            new TrueFalse(R.string.question_28, false),
            new TrueFalse(R.string.question_29,true),
            new TrueFalse(R.string.question_30,true)
    };

    // Declaring constants here. Rather than a fixed number, choosing to make it a function
    // of the length of the question bank (the number of questions)
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);
    private Toast mToastMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mScoreTextView = findViewById(R.id.score);

        // Restores the 'state' of the app upon screen rotation:
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
        } else {
            mScore = 0;
            mIndex = 0;
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
            }
        });

    }

    // Increase the index of the array question
    private void updateQuestion(){

        // Index out of bounds exception
        // We'll add modulus 13 (length of the array) in order to restart the questionBank
        mIndex = (mIndex+1) % mQuestionBank.length;

        if(mIndex==0){

            // Using this to get the app context
            // This refers to the MainActivity
            AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
            myAlert.setTitle("The game has finished");
            myAlert.setCancelable(false);
            myAlert.setMessage("You've scored: " + mScore + "points :) ");
            myAlert.setPositiveButton("Close application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            myAlert.show();
        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection) {

        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        // Can cancel the Toast message if there is one on screen and a new answer
        // has been submitted.
        if (mToastMessage != null) {
            mToastMessage.cancel();
        }

        if(userSelection == correctAnswer) {
            mToastMessage = makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT);
            mScore = mScore + 1;

        } else {
            mToastMessage = makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_LONG);
        }

        mToastMessage.show();

    }


    // Save the state of our app when the activity is restarted due to the screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState){

        outState.putInt("ScoreKey",mScore);
        outState.putInt("IndexKey",mIndex); //Tracks the question the user was on
    }
}


